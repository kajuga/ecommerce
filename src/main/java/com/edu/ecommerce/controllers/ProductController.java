package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.product.ProductDto;
import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.mapper.ProductMapper;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.service.impl.ProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import liquibase.pro.packaged.L;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper;


    @GetMapping
    @ApiOperation(value = "Get all products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        var productDtos = productService.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    @ApiOperation(value = "Get all products by category")
    public ResponseEntity<List<ProductDto>> findAllProductsByCategoryId(@NotNull @PathVariable("categoryId") Long id) {
        var allProductsByCategoryId = productService.getAllProductByCategoryId(id);
        var collect = allProductsByCategoryId.stream().map(productMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    //TODO обработчик вывода ошибки при создании уже существующего product
    @PostMapping()
    @ApiOperation(value = "Create product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        var product = productService.create(productMapper.fromDto(productDto));
        return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId, @RequestBody @Valid ProductDto productDto) {
                var product = productService.update(productId, productMapper.fromDto(productDto));
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete product by id")
    public ResponseEntity<Void> delete(@NotNull @PathVariable ("id") Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}