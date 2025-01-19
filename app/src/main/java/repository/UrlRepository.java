package repository;

import hexlet.code.model.Url;
import hexlet.code.util.TimestampFormatter;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {
    //Сохраняет новый объект Url в базе данных
    public static void save(Url url) throws SQLException {
        // Проверка на существование URL
        if (exists(url.getName())) {
            throw new SQLException("URL already exists");
        }

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
    //Обновляет поле createdAt для существующего URL в базе данных
    public static void update(Url url) throws SQLException {
        String sql = "UPDATE urls SET createdAt = NOW() WHERE name = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.executeUpdate();
        }
    }
    //Находит и возвращает объект Url по его идентификатору (ID)
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
    //Находит и возвращает объект Url по его имени
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
    //Удаляет объект Url из базы данных по его имени
    public static void destroy(Url url) throws SQLException {
        String sql = "DELETE FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.execute();
        }
    }
    //Возвращает список всех объектов Url из базы данных
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

    //Проверяет, существует ли URL с заданным именем в базе данных
    private static boolean exists(String urlName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection(); // Используем геттер
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, urlName); // Устанавливаем значение параметра
            var resultSet = preparedStatement.executeQuery(); // Выполняем запрос
            if (resultSet.next()) { // Проверяем, есть ли результат
                return resultSet.getInt(1) > 0; // Возвращаем true, если найден хотя бы один URL
            }
        }
        return false; // Если ничего не найдено, возвращаем false
    }
}
