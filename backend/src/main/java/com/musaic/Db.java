package com.musaic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private static String url;
    private static String user;
    private static String password;

    static {
        // Read DB config from environment variables
        url = System.getenv("DATABASE_URL");
        user = System.getenv("DB_USER");
        password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new RuntimeException(
                "Missing database environment variables. " +
                "Please set DATABASE_URL, DB_USER, and DB_PASSWORD."
            );
        }
    }

    /**
     * Get a new JDBC connection to Postgres.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
