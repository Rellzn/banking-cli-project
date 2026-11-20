package com.group14.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.group14.exception.DataAccessException;


public class PGConnectionUntil {
    private final String url;
    private final String username;
    private final String password;

    public PGConnectionUntil(AppConfig config) {
        this.url = config.require("POSTGRES_URL");
        this.username = config.require("POSTGRES_USERNAME");
        this.password = config.require("POSTGRES_PASSWORD");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DataAccessException("Could not connect to PostgreSQL at " + url, e);
        }
    }
}
