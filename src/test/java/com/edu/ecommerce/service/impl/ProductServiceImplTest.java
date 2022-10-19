package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.repository.CategoryRepository;
import com.edu.ecommerce.repository.ProductRepository;
import com.edu.ecommerce.service.interfaces.DataService;
import com.edu.ecommerce.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class ProductServiceImplTest {

    private static final long ID = 777L;

    private ProductServiceImpl productService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        DataService dataService = Mockito.mock(DataService.class);
        UserService userService = Mockito.mock(UserService.class);
        productService = new ProductServiceImpl(productRepository, categoryRepository, dataService, userService);

    }

    @Test
    void getAllProducts() throws Exception {

        Category mockCategoryFirst = new Category(3L, "Borsch", "Russian food", "http://russian_food.jpg");
        Category mockCategorySecond = new Category(6L, "Salo", "Ukrainian food", "http://ucrainian_food.jpg");

        when(productRepository.findAll()).thenReturn(Arrays.asList(
                new Product(2L, "Red Borsch", "http://red_borsch.jpg", 33.00, "red borsch", mockCategoryFirst),
                new Product(9L, "Salo Borsch", "http://salo_borsch.jpg", 12.00, "salo borsch", mockCategorySecond)
        ));

        List<Product> products = productService.findAll();
        assertEquals(products.get(0).getName(), "Red Borsch");
        assertEquals(products.get(1).getName(), "Salo Borsch");
    }

    @Test
    void getProductById() {
        Category category = new Category(22L, "Borsch", "Russian food", "http://russian_food.jpg");

        when(productRepository.findById(ID)).thenReturn(
                of(new Product(ID, "Red Borsch", "http://red_borsch.jpg", 33.00, "red borsch", category)));
        Product product = productService.findById(ID);

        assertEquals(product.getName(), "Red Borsch");
        assertEquals(product.getCategory().getCategoryName(), "Borsch");
    }

    @Test
    void getAllProductByCategoryId() {

        Category categoryToFind = new Category(ID, "Borsch", "Russian food", "http://russian_food.jpg");

        when(categoryRepository.findById(ID)).thenReturn(java.util.Optional.of(categoryToFind));

        when(productRepository.findAllByCategory(categoryToFind)).thenReturn(Arrays.asList(
                new Product(2L, "Red Borsch", "http://red_borsch.jpg", 33.00, "red borsch", categoryToFind),
                new Product(9L, "Salo Borsch", "http://salo_borsch.jpg", 12.00, "salo borsch", categoryToFind)
        ));

        List<Product> productList = productService.getAllProductByCategoryId(ID);
        assertEquals(2, productList.size());
        assertEquals(productList.get(1).getDescription(), "salo borsch");

    }
}