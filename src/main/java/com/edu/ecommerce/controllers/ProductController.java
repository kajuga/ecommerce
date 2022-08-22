package com.edu.ecommerce.controllers;

import com.edu.ecommerce.common.ApiResponse;
import com.edu.ecommerce.dto.product.ProductDto;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.service.impl.CategoryServiceImpl;
import com.edu.ecommerce.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryServiceImpl categoryService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryService.findById(productDto.getCategoryId()));
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtos = productService.listProducts();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PutMapping("/{productID}")
    @ApiOperation(value = "Update product by Id")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Long productID,
                                                     @RequestBody @Valid ProductDto productDto) {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryService.findById(productDto.getCategoryId()));
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
        return new ResponseEntity<>(new ApiResponse(true, "Product was updated"), HttpStatus.OK);
    }
}