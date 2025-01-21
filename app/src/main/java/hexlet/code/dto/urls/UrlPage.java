package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Url url;
    private List<Url> urls;

    public UrlPage() {
        this.urls = new ArrayList<>();
    }

    public <T> UrlPage(List<T> ts) {
        super();
    }
}
