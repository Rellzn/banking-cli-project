package com.group14.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.group14.exception.DataAccessException;

public class AppConfig {
    private static final String FILE = "/application.properties";

    private final Properties props = new Properties();

    public AppConfig() {
        try (InputStream in = AppConfig.class.getResourceAsStream(FILE)) {
            if (in != null) {
                props.load(in);
            }
            
        } catch (IOException e) {
            throw new DataAccessException("Could not read " + FILE, e);
        }
    }

    public String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new DataAccessException("Missing configuration value: " + key
                    + " (set it in application.properties or as env var " + envName(key) + ")");
        }
        return value;
    }

    public String get(String key) {
        String env = System.getenv(envName(key));
        return (env != null && !env.isBlank()) ? env : props.getProperty(key);
    }

    private static String envName(String key) {
        return key.replace('.', '_').toUpperCase();
    }
}
