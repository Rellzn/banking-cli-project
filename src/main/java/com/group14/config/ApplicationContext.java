package com.group14.config;

public class ApplicationContext implements AutoCloseable {
    private final AppConfig config;
    private final PGConnectionUntil postgres;

    public ApplicationContext() {
        this.config = new AppConfig();
        this.postgres = new PGConnectionUntil(config);

        new SchemaInitializer(postgres).initialize();
    }

    public AppConfig config() {
        return config;
    }

    public PGConnectionUntil postgres() {
        return postgres;
    }

    @Override
    public void close() {
        //nothing to close yet, need for later
    }
}

