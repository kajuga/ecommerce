package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.repository.CategoryRepository;
import com.edu.ecommerce.repository.ProductRepository;
import com.edu.ecommerce.service.interfaces.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;




class ProductServiceImplTest {

    private ProductServiceImpl productServiceImpl;

    @Mock
    ProductRepository repository;







    @Test
    void findAll() {
    }

//        @Test
//    void create() {
//    }


//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void getAllProductByCategoryId() {
//    }
//
//    @Test
//    void findByName() {
//    }
//

//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}