package com.edu.ecommerce.mapper;

import com.edu.ecommerce.dto.category.CategoryDto;
import com.edu.ecommerce.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                      .id(category.getId())
                      .categoryName(category.getCategoryName())
                      .description(category.getDescription())
                      .imageUrl(category.getImageUrl())
                      .build();
    }

    public Category fromDto(CategoryDto categoryDto) {
        return Category.builder()
                       .id(categoryDto.getId())
                        .categoryName(categoryDto.getCategoryName())
                        .description(categoryDto.getDescription())
                        .imageUrl(categoryDto.getImageUrl())
                        .build();
    }
}