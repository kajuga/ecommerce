package com.edu.ecommerce.controllers;

import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.repository.CategoryRepository;
import com.edu.ecommerce.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@WithMockUser
@WebMvcTest(
        controllers = CategoryController.class,
        useDefaultFilters = false,
        excludeAutoConfiguration = {
                SecurityConfig.class
        }
)
@Import({CategoryController.class, MvcTestConfiguration.class})
@AutoConfigureMockMvc(/*addFilters = false*/)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void getCategories() throws Exception {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(
                new Category(1L, "Borsch", "Russian food", "http://borsch.jpg"),
                new Category(2L, "Salo", "Ukrainian food", "http://salo.jpg"),
                new Category(3L, "Hot dogs", "Korean food", "http://hot_dogs.jpg")
        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].description", is("Russian food")))
                .andExpect(jsonPath("$[1].categoryName", is("Salo")))
                .andExpect(jsonPath("$[*].id",containsInAnyOrder(1,2,3)))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder("Russian food", "Korean food", "Ukrainian food")));
    }

//    @Test
//    void createCategory() {
//    }
//
//    @Test
//    void updateCategory() {
//    }
//
//    @Test
//    void delete() {
//    }
}