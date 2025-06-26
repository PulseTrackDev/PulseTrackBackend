package com.spring.pulsetrackbackend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class DatabaseEnvParser {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @PostConstruct
    public void setJdbcUrlFromDatabaseUrl() throws Exception {
        if (databaseUrl == null || databaseUrl.isEmpty()) return;

        URI dbUri = new URI(databaseUrl);
        String userInfo = dbUri.getUserInfo();
        String[] parts = userInfo.split(":");

        String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s",
                dbUri.getHost(),
                dbUri.getPort(),
                dbUri.getPath());

        System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
        System.setProperty("spring.datasource.username", parts[0]);
        System.setProperty("spring.datasource.password", parts[1]);
    }
}