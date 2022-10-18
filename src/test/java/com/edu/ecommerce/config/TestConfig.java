package com.edu.ecommerce.config;

import com.edu.ecommerce.repository.UserRepository;
import com.edu.ecommerce.service.impl.UserServiceImpl;
import com.edu.ecommerce.service.interfaces.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {


    @Bean
    public UserServiceImpl userService() {
        return mock(UserServiceImpl.class);
    }

    @Bean
    public UserRepository userRepository() {
        return mock(UserRepository.class);
    }



}
