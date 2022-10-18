package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.config.TestConfig;
import com.edu.ecommerce.exceptions.CrmException;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    private static final String userEmail = "admin@email.com";
    private static final User user = mock(User.class);

    @BeforeAll
    public static void setUp() {
        when(user.getEmail())
                .thenReturn(userEmail);
    }






}