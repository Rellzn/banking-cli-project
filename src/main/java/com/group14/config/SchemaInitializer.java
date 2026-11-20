package com.group14.config;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.group14.exception.DataAccessException;

//executes schema.sql as a single transaction to initialize the db

public class SchemaInitializer {
    private static final String SCHEMA_RESOURCE = "/db/postgres/schema.sql";

    private final PGConnectionUntil postgres;

    public SchemaInitializer(PGConnectionUntil postgres) {
        this.postgres = postgres;
    }

    public void initialize() {
        String script = loadScript();

        try (Connection conn = postgres.getConnection()) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(script);
                conn.commit();
            } catch (SQLException e) {
                rollback(conn, e);
                throw new DataAccessException("Schema initialization failed; rolled back", e);
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DataAccessException("Failed to reset AutoCommit", e);
                }
            }

        } catch (SQLException e) {
            // From setAutoCommit() or close(); connection-level problems.
            throw new DataAccessException("Schema initialization failed", e);
        }
    }

    private String loadScript() {
        try (InputStream in = SchemaInitializer.class.getResourceAsStream(SCHEMA_RESOURCE)) {
            if (in == null) { //getResourceAsStream returns null if not found, not stricly necessary
                throw new DataAccessException("Schema file not found on classpath: " + SCHEMA_RESOURCE);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataAccessException("Could not read " + SCHEMA_RESOURCE, e);
        }
    }

    private void rollback(Connection conn, SQLException original) {
        try {
            conn.rollback();
        } catch (SQLException rollbackFailure) {
            original.addSuppressed(rollbackFailure);
        }
    }
}