package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import repository.UrlRepository;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {
    public static void index(Context ctx) throws SQLException {
        //обрабатывает GET-запросы на отображение списка URL
        var urls = UrlRepository.getEntities(); //извлекаются все URL-адреса из базы данных
        var page = new UrlsPage(urls); //создается объект UrlsPage, который содержит список URL
        String flash = ctx.consumeSessionAttribute("flash");
        //извлекается временное сообщение из сессии (если есть) и устанавливается в объект страницы.
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
        //отображения шаблона urls/index.jte, передавая модель с данными страницы
    }

    public static void show(Context ctx) throws SQLException {
        //обрабатывает GET-запросы для отображения конкретного URL по его идентификатору
        var id = ctx.pathParamAsClass("id", Long.class).get();
        //извлекается параметр id из URL-запроса и преобразуется в тип Long
        var url = UrlRepository.findById(id)//ищется URL по его идентификатору
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
        var page = new UrlPage(url); //создается объект UrlPage, который содержит информацию о найденном URL
        ctx.render("urls/show.jte", model("page", page));
        //отображается шаблон urls/show.jte с данными страницы
    }

    public static void add(Context ctx) throws SQLException,
            //обрабатывает POST-запросы для добавления нового URL
            MalformedURLException,
            URISyntaxException,
            IllegalArgumentException {
        var name = ctx.formParam("url"); //извлекается параметр url из формы
        try {
            URL absoluteUrl = new URI(name).toURL();
            //Создание объекта URI: Параметр name преобразуется в объект URI.
            // Это позволяет проверить, является ли строка корректным URI.
            // Затем URI преобразуется в объект URL. Если строка не является корректным URL,
            // будет выброшено исключение MalformedURLException
            String schema = absoluteUrl.toURI().getScheme();
            //Получение схемы из объекта URL извлекается схема (например, http, https)
            String authority = absoluteUrl.toURI().getAuthority();
            //Получение авторитета извлекается авторитет (например, www.example.com),
            // который включает в себя доменное имя и, возможно, порт.
            Url url = new Url(schema + "://" + authority);
            //Создается новый объект Url, который содержит полное имя URL, сформированное из схемы и авторитета.
            Optional<Url> foundedUrl = UrlRepository.findByName(url.getName());
            //проверяется, существует ли уже URL с таким же именем в базе данных
            if (foundedUrl.isEmpty()) {
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
            }
            ctx.redirect(NamedRoutes.urlsPath());
            //После добавления (или попытки добавления) URL, пользователь перенаправляется на страницу со списком URL,
            // используя маршрут, определенный в NamedRoutes.urlsPath()
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }
}
