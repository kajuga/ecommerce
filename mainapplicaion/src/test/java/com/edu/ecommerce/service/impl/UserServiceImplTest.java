package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.config.TestConfig;
import com.edu.ecommerce.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
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