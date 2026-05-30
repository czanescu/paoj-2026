package com.pao.proiectMagazin.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    private DatabaseConnection() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("db.properties not found in resources");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load db.properties", e);
        }
        this.url = requireProperty(properties, "db.url");
        this.user = requireProperty(properties, "db.user");
        this.password = requireProperty(properties, "db.password");
    }

    private String requireProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing property: " + key);
        }
        return value;
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
