package com.github.jefersonalmeida.ifood.register;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class RegisterTestLifecycle implements QuarkusTestResourceLifecycleManager {

    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres");

    @Override
    public Map<String, String> start() {
        postgreSQLContainer.start();
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.jdbc.url", postgreSQLContainer.getJdbcUrl());
        properties.put("quarkus.datasource.username", postgreSQLContainer.getUsername());
        properties.put("quarkus.datasource.password", postgreSQLContainer.getPassword());
        return properties;
    }

    @Override
    public void stop() {
        if (postgreSQLContainer != null) postgreSQLContainer.stop();
    }
}
