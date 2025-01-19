package repository;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {
    // Объявляем dataSource как private
    private static HikariDataSource dataSource;

    // Геттер для доступа к dataSource
    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    // Сеттер для установки значения dataSource (если это необходимо)
    public static void setDataSource(HikariDataSource ds) {
        dataSource = ds;
    }
}

