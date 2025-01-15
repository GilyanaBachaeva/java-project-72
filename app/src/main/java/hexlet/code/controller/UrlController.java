package hexlet.code.controller;

import io.javalin.http.Context;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import hexlet.code.repository.BaseRepository;

public class UrlController {

    public static void addUrl(Context ctx) {
        String urlInput = ctx.formParam("url");
        try {
            URI uri = new URI(urlInput);
            String domain = uri.getScheme() + "://" + uri.getHost();
            if (uri.getPort() != -1) {
                domain += ":" + uri.getPort();
            }

            // Проверка на уникальность
            if (BaseRepository.urlExists(domain)) {
                ctx.attribute("flashMessage", "Страница уже существует");
            } else {
                BaseRepository.addUrl(domain);
                ctx.attribute("flashMessage", "Страница успешно добавлена");
            }
        } catch (URISyntaxException e) {
            ctx.attribute("flashMessage", "Некорректный URL");
        }

        ctx.redirect("/");
    }
    public static void getAllUrls(Context ctx) {
        List<String> urls = BaseRepository.getAllUrls(); // Метод для получения всех URL
        ctx.render("urls.jte", Map.of("urls", urls));
    }

    public static void getUrlById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String url = BaseRepository.getUrlById(id); // Метод для получения URL по ID
        ctx.render("url.jte", Map.of("url", url));
    }
}
