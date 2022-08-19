package com.edu.ecommerce.service;


import com.edu.ecommerce.dto.product.ProductDto;
import com.edu.ecommerce.exceptions.ProductNotExistException;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.repository.ProductRepository;
import com.edu.ecommerce.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private final AuthenticatedUser authenticatedUser;

    public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            productDtos.add(new ProductDto(product));
        }
        return productDtos;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return product;
    }


    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    public void updateProduct(Long productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        productRepository.save(product);
    }


    public Product getProductById(Long productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }


}