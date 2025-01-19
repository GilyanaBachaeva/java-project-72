package repository;

import com.zaxxer.hikari.HikariDataSource;
//предоставляет статические методы для получения и установки этого источника данных, что позволяет другим классам в приложении легко получать доступ к базе данных
public class BaseRepository {
    private static HikariDataSource dataSource;//Объявляется статическое поле dataSource, которое будет хранить экземпляр HikariDataSource. Поскольку поле статическое, оно будет общим для всех экземпляров класса BaseRepository

    // Геттер для доступа к dataSource
    public static HikariDataSource getDataSource() {
        return dataSource;//позволяет другим частям приложения использовать текущее значение dataSource для получения соединений с базой данных
    }

    // Сеттер для установки значения dataSource (если это необходимо)
    public static void setDataSource(HikariDataSource ds) {//позволяет установить значение для dataSource
        dataSource = ds;
    }
}

