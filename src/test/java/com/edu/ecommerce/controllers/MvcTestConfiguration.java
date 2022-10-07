package com.edu.ecommerce.controllers;

import com.edu.ecommerce.mapper.CategoryMapper;
import com.edu.ecommerce.repository.*;
import com.edu.ecommerce.service.impl.CategoryServiceImpl;
import com.edu.ecommerce.service.interfaces.CategoryService;
import liquibase.pro.packaged.C;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MvcTestConfiguration {

    @MockBean
    CategoryRepository categoryRepository;
    @MockBean
    TokenRepository tokenRepository;
    @MockBean
    CartRepository cartRepository;
    @MockBean
    FileRepository fileRepository;
    @MockBean
    OrderItemsRepository orderItemsRepository;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    RoleRepository roleRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    WishListRepository wishListRepository;


    @Bean
    public MapperFacade mapperFacade() {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

}
