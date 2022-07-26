//package com.edu.ecommerce.configuration;
//
//import liquibase.integration.spring.SpringLiquibase;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableConfigurationProperties
//public class DataBaseConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .build();
//    }
//
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.liquibase")
//    public LiquibaseProperties liquibaseProperties() {
//        return new LiquibaseProperties();
//    }
//
//    @Bean
//    public SpringLiquibase liquibase(LiquibaseProperties liquibaseProperties) {
//        var liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource());
//        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
//        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
//        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
//        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
//
//        return liquibase;
//    }
//}