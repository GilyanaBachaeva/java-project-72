package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseRepository {
    @Getter
    private static HikariDataSource hikariDataSource;

    public static void setDataSource(HikariDataSource dataSource) {
        BaseRepository.hikariDataSource = dataSource;
    }

    // Метод для проверки существования URL в базе данных
    public static boolean urlExists(String url) {
        String query = "SELECT COUNT(*) FROM urls WHERE url = ?";
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, url);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Если количество больше 0, значит URL существует
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Метод для добавления URL в базу данных
    public static void addUrl(String url) {
        String query = "INSERT INTO urls (url) VALUES (?)";
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, url);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения всех URL из базы данных
    public static List<String> getAllUrls() {
        List<String> urls = new ArrayList<>();
        String query = "SELECT url FROM urls";
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                urls.add(resultSet.getString("url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urls;
    }

    // Метод для получения URL по ID
    public static String getUrlById(int id) {
        String query = "SELECT url FROM urls WHERE id = ?";
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("url");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если URL не найден
    }
}
