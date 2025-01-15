package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {13,13,13,13,13,13,22,28};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Главная страница</title>\n    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">\n</head>\n<body>\n    <div class=\"container\">\n        <h1>Добро пожаловать на главную страницу!</h1>\n        <p>Это пример использования Jte с Javalin и Bootstrap.</p>\n\n        ");
		jteOutput.writeContent("\n        <form action=\"/urls\" method=\"post\">\n            <div class=\"form-group\">\n                <label for=\"url\">Введите URL:</label>\n                <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" required>\n            </div>\n            <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\n        </form>\n\n        ");
		jteOutput.writeContent("\n        <div>\n            {{ flashMessage }}\n        </div>\n    </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
