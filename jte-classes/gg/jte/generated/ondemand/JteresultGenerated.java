package gg.jte.generated.ondemand;
public final class JteresultGenerated {
	public static final String JTE_NAME = "result.jte";
	public static final int[] JTE_LINE_INFO = {20,20,20,20,20,20};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Анализатор страниц</title>\n    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">\n</head>\n<body>\n    <div class=\"container\">\n        <h1>Анализатор страниц</h1>\n        <p>{{ flashMessage }}</p>\n\n        <h2>Сайты</h2>\n        <ul>\n            <li><a href=\"/urls\">Список сайтов</a></li>\n            <li><a href=\"/\">Главная</a></li>\n        </ul>\n    </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
