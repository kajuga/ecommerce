package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.exceptions.ResourceNotFoundException;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.repository.CategoryRepository;
import com.edu.ecommerce.service.interfaces.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * The service implementation to the maintain operations with Category.
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id: %s not found!", id)));
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Transactional
    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category update(Long id, Category category) {
        return categoryRepository.save(updateCategoryFields(findById(id), category));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    private Category updateCategoryFields(Category findCategory, Category request) {
        return Category.builder()
                .id(findCategory.getId())
                .categoryName(Optional.ofNullable(request.getCategoryName()).orElse(findCategory.getCategoryName()))
                .description(Optional.ofNullable(request.getDescription()).orElse(findCategory.getDescription()))
                .imageUrl(Optional.ofNullable(request.getImageUrl()).orElse(findCategory.getImageUrl()))
                .build();
    }
}