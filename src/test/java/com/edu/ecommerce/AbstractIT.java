package com.edu.ecommerce;

import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.security.TokenAuthenticationEntryPoint;
import com.edu.ecommerce.security.TokenAuthenticationFilter;
import com.edu.ecommerce.service.interfaces.TokenService;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation. *;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@SpringBootTest(
        properties = {"spring.main.allow-bean-definition-overriding=true"}
)
@ContextConfiguration(classes = {AbstractIT.TestSecurityConfig.class, AbstractIT.TestConfig.class})
@Testcontainers
@ActiveProfiles("it-test")
public class AbstractIT {

    @MockBean
    SecurityConfig securityConfig;

    @MockBean
    TokenAuthenticationFilter tokenAuthenticationFilter;

    @MockBean
    TokenService tokenService;

    @MockBean
    TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("ecommerce_v2")
            .withUsername("test")
            .withPassword("test");

    static {
        mySQLContainer.start();
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName(mySQLContainer.getDriverClassName())
                    .url(mySQLContainer.getJdbcUrl())
                    .password(mySQLContainer.getPassword())
                    .username(mySQLContainer.getUsername())
                    .build();


        }
    }

    @TestConfiguration
    @Order(1)
    public static class TestSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            // Disable CSRF
            httpSecurity.csrf().disable()
                    // Permit all requests without authentication
                    .authorizeRequests().anyRequest().permitAll();
        }
    }
}