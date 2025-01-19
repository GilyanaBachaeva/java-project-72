package hexlet.code;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public final class AppTest {
    private static final int HTTP_OK = 200;
    private static final int HTTP_NOT_FOUND = 404;

    private final Context ctx = mock(Context.class);
    private Javalin app;

    /**
     * Initializes and runs the application for testing.
     *
     * @throws SQLException if a database access error occurs
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    public void runApp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void getRootPageReturnSuccess() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(HTTP_OK);
            assertThat(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void getUrlsListPageReturnSuccess() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(HTTP_OK);
        });
    }

    @Test
    public void postCorrectUrlNameReturnSuccess() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(HTTP_OK);
            assertThat(response.body().string()).contains("https://www.example.com");
        });
    }

    @Test
    public void getIncorrectUrlIdReturnNotFound() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/9999");
            assertThat(response.code()).isEqualTo(HTTP_NOT_FOUND);
        });
    }
}
