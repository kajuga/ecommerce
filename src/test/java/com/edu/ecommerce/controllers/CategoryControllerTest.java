package com.edu.ecommerce.controllers;

import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
                .andExpect(jsonPath("$[*].description", containsInAnyOrder("Russian food", "Korean food", "Ukrainian food")))
                .andReturn();
    }

    @Test
    void createCategory() throws Exception {
        when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(new Category(1L, "Borsch2", "Russian food2", "http://borsch.jpg"));

        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                        .with(csrf())
                            .content(asJsonString(new Category(null, "Borsch", "Russian food", "http://borsch.jpg")))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isCreated())
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                            .andReturn();
    }

    @Test
    void updateCategory() throws Exception {

        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(new Category(1L, "Borsch2", "Russian food2", "http://borsch.jpg"));
        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(java.util.Optional.of(new Category(1L, "Borsch2", "Russian food2", "http://borsch.jpg")));

        mockMvc.perform(MockMvcRequestBuilders.put("/category/{id}", 1)
                        .with(csrf())
                            .content(asJsonString(new Category(1L, "Borsch2", "Russian food2", "http://borsch.jpg")))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andDo(MockMvcResultHandlers.print())

                            .andExpect(status().is2xxSuccessful())
                            .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Borsch2"))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Russian food2"))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value("http://borsch.jpg"));
    }

    @Test
    void delete() throws Exception {
        Category deletedCategory = new Category(1L, "Borsch", "Russian food", "http://borsch.jpg");
        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(java.util.Optional.of(deletedCategory));
        Mockito.doNothing().when(categoryRepository).delete(deletedCategory);

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/{id}", 1)
                .with(csrf()))
                .andExpect(status().isOk());
    }

    static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}