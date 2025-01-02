package hexlet.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.javalin.Javalin;

public class App {
    private static Javalin app;
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static Javalin getApp() {
        // Получаем порт из переменной окружения или используем 7000 по умолчанию
        final int port = Integer.parseInt(System.getenv("PORT") != null ? System.getenv("PORT") : "7000");
        if (app == null) {
            app = Javalin.create(config -> {
                // Настройка приложения Javalin
                config.bundledPlugins.enableDevLogging();
            }).start(port);
            app.get("/", ctx -> ctx.result("Hello 29%World"));
            LOGGER.info("Приложение Javalin запущено на порту {}", port);
        }
        return app;
    }
    public static void main(String[] args) {
        getApp(); // Запуск приложения
    }
}
