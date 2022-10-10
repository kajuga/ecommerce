package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    private static final long ID = 1L;

    @InjectMocks
    private CategoryServiceImpl service;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    void findCategory_shouldCallRepository() {
        final Category category = mock(Category.class);
        when(categoryRepository.findById(ID)).thenReturn(Optional.ofNullable(category));

        final Category actualCategory = service.findById(ID);

        assertNotNull(actualCategory);
        assertEquals(category, actualCategory);
        verify(categoryRepository).findById(ID);

    }

    @Test
    void findCategoryById() {
        Category findedByIdCategory = new Category(667L, "Russian Food", "Nice russian borsch", "http://1820:80/image_borsch.jpg");
        given(categoryRepository.findById(667L)).willReturn(java.util.Optional.of(findedByIdCategory));

        Category category = service.findById(667L);

        assertEquals(category.getCategoryName(), "Russian Food");
        assertEquals(category.getDescription(), "Nice russian borsch");
        assertEquals(category.getImageUrl(), "http://1820:80/image_borsch.jpg");
    }



    @Test
    public void addCategory() {
        Category inputCategory = new Category(666L, "Korean Food", "Korean Food description", "https://korean_food.png");
        given(categoryRepository.save(inputCategory)).willReturn(inputCategory);
        Category category = service.create(inputCategory);
        verify(categoryRepository).save(category);
        assertEquals(category.getCategoryName(), "Korean Food");
    }

    @Test
    void update() {


    }




    @Test
    public void getCategory() {
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder()
                .categoryName("Korean Food")
                .description("Salt korean food")
                .imageUrl("http://1820:80/image.jpg")
                .build());
        mockCategories.add(Category.builder()
                .categoryName("Russian Food")
                .description("Nice russian borsch")
                .imageUrl("http://1820:80/image_borsch.jpg")
                .build());
        given(categoryRepository.findAll()).willReturn(mockCategories);
        List<Category> category = service.findAll();
        assertEquals(category.get(0).getCategoryName(), "Korean Food");
        assertEquals(category.get(1).getCategoryName(), "Russian Food");
        assertEquals(2, category.size());
        assertNotEquals(1, category.size());
        assertNotEquals(3, category.size());
    }



    @Test
    void findCategoryByName() {
        Category findedByNameCategory = new Category(667L, "Russian Food", "Nice russian borsch", "http://1820:80/image_borsch.jpg");
        given(categoryRepository.findByCategoryName("Russian Food")).willReturn(findedByNameCategory);
        Category category = service.findByName("Russian Food");
        assertEquals(category.getCategoryName(), "Russian Food");
        assertEquals(category.getDescription(), "Nice russian borsch");
        assertEquals(category.getImageUrl(), "http://1820:80/image_borsch.jpg");
    }




    @Test
    void delete() {
    }
}