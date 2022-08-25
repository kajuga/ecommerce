package com.edu.ecommerce.mapper;

import com.edu.ecommerce.dto.product.ProductDto;
import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.service.interfaces.CategoryService;
import com.edu.ecommerce.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryService categoryService;

    public Product fromDto(ProductDto productDto) {
        var category = categoryService.findById(productDto.getCategoryId());
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(category)
                .imageURL(productDto.getImageURL())
                .build();
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .imageURL(product.getImageURL())
                .build();
    }
}
