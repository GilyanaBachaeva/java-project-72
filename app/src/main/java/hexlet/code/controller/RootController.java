package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

//обрабатывает HTTP-запрос, извлекает временное сообщение из сессии, создает объект страницы и отображает его с помощью шаблона
public class RootController {
    public static void index(Context ctx) {
        var page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("index.jte", model("page", page));
    }
}
