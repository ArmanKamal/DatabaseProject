package com.smu.databasesystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class DataConfig {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.database}")
    private String databaseName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    public DataSource dataSource() {
        createDatabaseIfNotExists();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(jdbcUrl + databaseName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        executeSqlScript(dataSource);
        return dataSource;
    }

    private void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            // Create the database if it doesn't exist
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);

        } catch (Exception e) {
            throw new RuntimeException("Error creating database", e);
        }
    }

    private void executeSqlScript(DataSource dataSource) {
        // Create a ResourceDatabasePopulator
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        // Add the SQL script to the populator
        populator.addScript(new ClassPathResource("initialize.sql"));
        populator.addScript(new ClassPathResource("data.sql"));

        // Execute the populator on the dataSource
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
