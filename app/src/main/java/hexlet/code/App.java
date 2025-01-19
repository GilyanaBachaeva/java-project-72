package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import repository.BaseRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class App { //константа определяет строку подключения к базе данных H2,
    // которая будет использоваться в памяти. При запуске приложения будет выполнен SQL-скрипт из файла schema.sql
    private static final String LOCAL_H2_BASE = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;"
            + "INIT=runscript from 'classpath:/schema.sql'";

    public static void main(String[] args) throws IOException, SQLException { // точка входа в приложение
        var app = getApp(); //получить экземпляр Javalin
        app.start(getPort()); //запускает его на порту, полученном из метода getPort
    }
    public static Javalin getApp() throws IOException, SQLException { //настраивает соединение с базой данных,
        // выполняет инициализацию базы данных
        var hikariConfig = new HikariConfig();
        //создается объект HikariConfig, который настраивает соединение с базой данных
        hikariConfig.setJdbcUrl(getDbConfig());
        //метод getDbConfig возвращает строку подключения к базе данных, она передается в конфигурацию HikariCP.
        var dataSource = new HikariDataSource(hikariConfig);
        //создается объект HikariDataSource, который будет управлять соединениями с базой данных.
        // Этот объект будет использоваться для получения соединений в дальнейшем
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        //Загружается SQL-скрипт из файла schema.sql и выполняется для инициализации базы данных
        var sql = new BufferedReader(new InputStreamReader(url))
                //С помощью BufferedReader и InputStreamReader читается содержимое файла schema.sql
                // и собирается в одну строку. Это позволяет выполнить все SQL-команды из файла
                .lines().collect(Collectors.joining("\n"));
        try (var connection = dataSource.getConnection(); //соединение с базой данных из dataSource.
             var statement = connection.createStatement()) {
            //создается объект Statement, который используется для выполнения SQL-команд
            statement.execute(sql); //Метод execute(sql) выполняет все команды, загруженные из файла schema.sql
        }
        BaseRepository.setDataSource(dataSource);
        //устанавливается созданный dataSource в статический класс BaseRepository.
        // Это позволяет другим частям приложения использовать этот источник данных для работы с базой данных.
        var app = Javalin.create(config -> {
            //создается экземпляр Javalin с помощью метода create.
            // Внутри передается лямбда-функция для настройки конфигурации
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            //устанавливается рендерер файлов с использованием JavalinJte и созданного шаблонного движка
            config.bundledPlugins.enableDevLogging(); //включается логирование для разработки
        });
        app.get(NamedRoutes.rootPath(), RootController::index);
        //ссылка на статический метод index класса RootController, который будет вызван,
        // когда будет получен HTTP-запрос по маршруту, определенному в NamedRoutes.rootPath()
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::add);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        return app; //возвращает созданный экземпляр Javalin, который теперь настроен и готов к запуску
    }

    public static int getPort() {
        //получает порт из переменной окружения PORT. Если переменная не установлена,
        // используется порт 7070 по умолчанию
        var port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static String getDbConfig() {
        //возвращает строку подключения к базе данных, получая ее из переменной окружения JDBC_DATABASE_URL.
        // Если переменная не установлена, используется значение LOCAL_H2_BASE
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", LOCAL_H2_BASE);
    }

    public static TemplateEngine createTemplateEngine() {
        //возвращает объект типа TemplateEngine, который будет использоваться для рендеринга HTML-шаблонов
        ClassLoader classLoader = App.class.getClassLoader();
        //получаем ClassLoader, связанный с классом App. ClassLoader используется для загрузки ресурсов
        // (например, файлов шаблонов) из classpath приложения.
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        //этот объект отвечает за разрешение (поиск) шаблонов в указанной директории.
        // В данном случае он ищет шаблоны в папке templates, используя classLoader для доступа к ресурсам.
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        //создается экземпляр TemplateEngine.В качестве первого аргумента передается codeResolver,
        // который будет использоваться для поиска шаблонов.Вторым аргументом передается ContentType.Html,
        // что указывает, что шаблоны будут рендериться как HTML
        return templateEngine;
    }
}
