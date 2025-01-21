package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UrlCheckRepository;
import repository.UrlRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class AppTest {
    private final Context ctx = mock(Context.class);
    Javalin app;
    private static Path pathToHtmlFile = Paths.get(
            "src/test/resources/htmlexample.html").toAbsolutePath().normalize();
    private static String htmlFileContent;
    private static MockWebServer mockWebServer;

    public static String readFile(Path path) throws Exception {
        return Files.readString(pathToHtmlFile);
    }

    @BeforeAll
    public static void startMockServer() throws Exception {
        mockWebServer = new MockWebServer();
        htmlFileContent = readFile(pathToHtmlFile);
        MockResponse mockedResponse = new MockResponse()
                //.addHeader("content-type: text/html")
                .setBody(htmlFileContent)
                .setResponseCode(200);
        mockWebServer.enqueue(mockedResponse);
        mockWebServer.start();
    }

    @AfterAll
    public static void shutDownServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public void runApp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void shouldReturnSuccessForRootPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void shouldReturnSuccessForUrlsListPage() {
        JavalinTest.test(app, (server, client) -> {
            assertThat(client.get("/urls").code()).isEqualTo(200);
        });
    }

    @Test
    public void shouldReturnSuccessWhenPostingValidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.example.com");
        });
    }

    @Test
    public void shouldReturnNotFoundForInvalidUrlId() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/9999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

}
