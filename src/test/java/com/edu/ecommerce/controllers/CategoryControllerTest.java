package com.edu.ecommerce.controllers;

import com.edu.ecommerce.config.MvcTestConfiguration;
import com.edu.ecommerce.configuration.SecurityConfig;
import com.edu.ecommerce.dto.category.CategoryDto;
import com.edu.ecommerce.mapper.CategoryMapper;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.service.interfaces.CategoryService;
import com.edu.ecommerce.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
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
@ActiveProfiles("mock-test")
class CategoryControllerTest {

    private static final String BASE_REQUEST = "json/request/";
    private static final String BASE_RESPONSE = "json/response/";

    private static final String REQUEST_CREATE_1 = BASE_REQUEST + "category_create_1.json";
    private static final String RESPONSE_CREATE_1 = BASE_RESPONSE + "category_create_1.json";

    private static final String RESPONSE_GET_1 = BASE_RESPONSE + "categories_get_1.json";
    private static final String RESPONSE_GET_3 = BASE_RESPONSE + "categories_get_3.json";

    private final CategoryMapper mapper = new CategoryMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;


    @Test
    void getCategoriesTest() throws Exception {
        final List<CategoryDto> response = TestUtil.readJsonResourceToList(RESPONSE_GET_3, CategoryDto.class);
        final String expected = TestUtil.write(response);

        doReturn(response).when(categoryService).findAll();

        this.mockMvc
                .perform(get("/category"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }

    @Test
    void getCategoryByIdSuccessTest() throws Exception {
        final Category response = TestUtil.readJsonResource(RESPONSE_GET_1, Category.class);
        final String expected = TestUtil.write(response);

        doReturn(response)
                .when(categoryService)
                .findById(1L);

        this.mockMvc
                .perform(get("/category/{id}", 1L))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }


    @Test
    void createCategoryTest() throws Exception {
        when(categoryService.create(Mockito.any(Category.class)))
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


//    @Test
//    void createCategorySuccessTest() throws Exception {
//        final CategoryDto request = TestUtil.readJsonResource(REQUEST_CREATE_1, CategoryDto.class);
//        final CategoryDto response = TestUtil.readJsonResource(RESPONSE_CREATE_1, CategoryDto.class);
//        final String expected = TestUtil.write(response);
//
//        final String expectedResult = TestUtil.write(response);
//        doReturn(response)
//                .when(categoryService)
//                .create(request);
//
//        this.mockMvc
//                .perform(post("/departmens")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBytes)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().encoding(StandardCharsets.UTF_8))
//                .andExpect(content().string(expected));
//    }






    @Disabled
    @Test
    void updateCategory() throws Exception {
        Mockito.when(categoryService.create(Mockito.any(Category.class)))
                .thenReturn(new Category(1L, "Borsch2", "Russian food2", "http://borsch.jpg"));
        Mockito.when(categoryService.findById(1L))
                .thenReturn(new Category(1L, "Borsch2", "Russian food2", "http://borsch.jpg"));

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



//    @Test
//    void delete() throws Exception {
//        Category deletedCategory = new Category(1L, "Borsch", "Russian food", "http://borsch.jpg");
//        Mockito.when(categoryRepository.findById(1L))
//                .thenReturn(java.util.Optional.of(deletedCategory));
//        Mockito.doNothing().when(categoryRepository).delete(deletedCategory);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/category/{id}", 1)
//                .with(csrf()))
//                .andExpect(status().isOk());
//    }

    static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}