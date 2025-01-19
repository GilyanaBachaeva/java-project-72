package hexlet.code.util;
//предназначен для управления маршрутами (URL-путями) в веб-приложении.
// Он предоставляет статические методы, которые возвращают строки, представляющие различные маршруты
public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }
}
