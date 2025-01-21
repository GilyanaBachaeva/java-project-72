package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,5,5,8,8,8,13,13,13,17,17,17,21,21,21,44,44,44,47,49,49,51,51,53,53,53,56,56,69,69,72,72,72,75,75,75,75,75,75,75,79,83,86,86,91,91,91,91,91,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <section>\n        <div class=\"container-lg mt-5\">\n            <h1>Сайт:");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n            <table class=\"table table-bordered table-hover mt-3\">\n                <tbody>\n                <tr>\n                    <td>ID</td>\n                    <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n                </tr>\n                <tr>\n                    <td>Имя</td>\n                    <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n                </tr>\n                <tr>\n                    <td>Дата создания</td>\n                    <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getFormattedTimestamp());
				jteOutput.writeContent("</td>\n                </tr>\n                </tbody>\n            </table>\n            <h2 class=\"mt-5\">Проверки</h2>\n            <form method=\"post\" action=\"/urls/{id}/checks\">\n                <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\n            </form>\n            <table class=\"table table-bordered table-hover mt-3\">\n                <thead>\n                    <tr><th class=\"col-1\">ID</th>\n                        <th class=\"col-1\">Код ответа</th>\n                        <th>title</th>\n                        <th>h1</th>\n                        <th>description</th>\n                        <th class=\"col-2\">Дата проверки</th>\n                    </tr>\n                </thead>\n                <tbody>\n                </tbody>\n            </table>\n        </div>\n    </section>\n");
			}
		});
		jteOutput.writeContent("@import hexlet.code.dto.urls.UrlsPage\n @param UrlsPage page\n\n ");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n     <section>\n         ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\n             <div class=\"rounded-0 m-0 alert alert-dismissible fade show alert-success\" role=\"alert\">\n                 <p class=\"m-0\">");
					jteOutput.setContext("p", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("</p>\n                 <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n             </div>\n         ");
				}
				jteOutput.writeContent("\n         <div class=\"container-lg mt-5\">\n             <h1>Сайты</h1>\n                 <table class=\"table table-bordered table-hover mt-3\">\n                     <thead>\n                         <tr>\n                             <th class=\"col-1\">ID</th>\n                             <th>Имя</th>\n                             <th class=\"col-2\">Последняя проверка</th>\n                             <th class=\"col-1\">Код ответа</th>\n                         </tr>\n                     </thead>\n                     <tbody>\n                         ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n                             <tr>\n                                 <td>\n                                     ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("\n                                 </td>\n                                 <td>\n                                     <a href=\"/urls/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(url.getId());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\n                                 </td>\n                                 <td>\n                                     пока что тут ничего\n                                     ");
					jteOutput.writeContent("\n                                 </td>\n                                 <td>\n                                     пока что тут ничего\n                                     ");
					jteOutput.writeContent("\n                                 </td>\n                             </tr>\n                         ");
				}
				jteOutput.writeContent("\n                     </tbody>\n                 </table>\n         </div>\n     </section>\n ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
