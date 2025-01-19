package repository;

import com.zaxxer.hikari.HikariDataSource;
//предоставляет статические методы для получения и установки этого источника данных,
// что позволяет другим классам в приложении легко получать доступ к базе данных
public class BaseRepository {
    public static HikariDataSource dataSource;
}

