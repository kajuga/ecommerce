package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.AbstractIT;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.service.interfaces.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;


import static org.mockito.Mockito.*;

class CategoryServiceImpIT extends AbstractIT {

    @Autowired
    private CategoryService service;


    @Test
    void test_create() {
        final Category category = new Category();
        category.setCategoryName("CategoryName");
        category.setDescription("Description");
        category.setImageUrl("url");

        Category savedCategory = service.create(category);
        Assertions.assertNotNull(savedCategory.getId());

    }

}