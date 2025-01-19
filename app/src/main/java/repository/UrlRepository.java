package repository;

import hexlet.code.model.Url;
import hexlet.code.util.TimestampFormatter;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name) VALUES (?)";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
                url.setCreatedAt(generatedKeys.getTimestamp(2));
                url.setFormattedTimestamp(TimestampFormatter.dateFormatter(url.getCreatedAt()));
            } else {
                throw new SQLException("DB has not returned an id or createdAt after saving an entity");
            }
        }
    }

    public static void update(Url url) throws SQLException {
        String sql = "UPDATE urls SET createdAt = NOW() WHERE name = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.executeUpdate();
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var resultName = resultSet.getString("name");
                var resultCreatedAt = resultSet.getTimestamp("createdAt");
                var resultId = resultSet.getLong("id");
                Url resUrl = new Url(resultName);
                resUrl.setId(resultId);
                resUrl.setCreatedAt(resultCreatedAt);
                resUrl.setFormattedTimestamp(TimestampFormatter.dateFormatter(resUrl.getCreatedAt()));
                return Optional.of(resUrl);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var resultName = resultSet.getString("name");
                var resultId = resultSet.getLong("id");
                var resultCreatedAt = resultSet.getTimestamp("createdAt");
                Url resUrl = new Url(resultName);
                resUrl.setId(resultId);
                resUrl.setCreatedAt(resultCreatedAt);
                resUrl.setFormattedTimestamp(TimestampFormatter.dateFormatter(resUrl.getCreatedAt()));
                return Optional.of(resUrl);
            }
            return Optional.empty();
        }
    }

    public static void destroy(Url url) throws SQLException {
        String sql = "DELETE FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.execute();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var name = resultSet.getString("name");
                var url = new Url(name);
                url.setId(resultSet.getLong("id"));
                url.setCreatedAt(resultSet.getTimestamp("createdAt"));
                result.add(url);
            }
            return result;
        }
    }
}
