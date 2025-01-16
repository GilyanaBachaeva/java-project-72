package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

@Slf4j
public class App {

    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        HikariDataSource dataSource = setupDataSource();
        initializeDatabase(dataSource);
        BaseRepository.setDataSource(dataSource);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        setupRoutes(app);
        return app;
    }

    private static HikariDataSource setupDataSource() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDataBaseUrl());
        return new HikariDataSource(hikariConfig);
    }

    private static void initializeDatabase(HikariDataSource dataSource) throws IOException, SQLException {
        var sql = loadSqlSchema();
        log.info(sql);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static String loadSqlSchema() throws IOException {
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        return new BufferedReader(new InputStreamReader(url))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    private static void setupRoutes(Javalin app) {
        app.get("/", ctx -> ctx.render("index.jte"));

        app.post("/urls", ctx -> {
            String urlInput = ctx.formParam("url");
            String flashMessage = addUrl(urlInput);
            ctx.sessionAttribute("flashMessage", flashMessage);
            ctx.redirect("/result");
        });

        app.get("/result", ctx -> {
            String flashMessage = ctx.sessionAttribute("flashMessage");
            ctx.sessionAttribute("flashMessage", null);
            ctx.render("result.jte", Map.of("flashMessage", flashMessage));
        });

        app.get("/urls", UrlController::getAllUrls);
        app.get("/urls/{id}", UrlController::getUrlById);
    }

    private static String addUrl(String urlInput) {
        boolean isAdded = BaseRepository.addUrl(urlInput);
        return isAdded ? "Страница успешно добавлена" : "Ошибка при добавлении страницы";
    }

    private static String getDataBaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project");
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}
