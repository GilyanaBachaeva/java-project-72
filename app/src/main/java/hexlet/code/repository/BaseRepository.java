package hexlet.code.repository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

public class BaseRepository {
    @Getter
    private static HikariDataSource hikariDataSource;

    public static void setDataSource(HikariDataSource dataSource) {
        BaseRepository.hikariDataSource = dataSource;
    }
}
