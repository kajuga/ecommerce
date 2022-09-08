//package com.edu.ecommerce.controllers;
//
//import com.edu.ecommerce.model.Category;
//import com.edu.ecommerce.repository.CategoryRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CategoryControllerTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    public void resetDB() {
//        categoryRepository.deleteAll();
//    }
//
//    @Test
//    public void givenCategory_whenAdd_thenStatus201andCategoryReturned() throws Exception {
//        Category category = new Category(666L, "TestCategoryName", "TestDescription", "TestImageUrl");
//        mockMvc.perform(
//                        post("/category")
//                                .content(objectMapper.writeValueAsString(category))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated())
//                .andExpect((ResultMatcher) jsonPath("$.id").isNumber())
//                .andExpect((ResultMatcher) jsonPath("$.name").value("TestCategoryName"))
//                .andExpect((ResultMatcher) jsonPath("$.description").value("TestDescription"))
//                .andExpect((ResultMatcher) jsonPath("$.imageUrl").value("TestImageUrl"));
//    }
//    //другие тесты
//
//    private Category createTestPerson(Long id, String name, String description, String imageUrl) {
//        Category category = new Category(id, name, description, imageUrl);
//        return categoryRepository.save(category);
//    }
//
//
//
//
//
//}
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////
////    @Column(name = "category_name", length = 55)
////    private String categoryName;
////
////    @Column(name = "description")
////    private String description;
////
////    @Column(name = "image_url")
////    private String imageUrl;
//
