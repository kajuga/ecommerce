package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.AbstractIT;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.service.interfaces.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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