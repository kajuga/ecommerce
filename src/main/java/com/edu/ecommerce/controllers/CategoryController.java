package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.category.CategoryDto;
import com.edu.ecommerce.mapper.CategoryMapper;
import com.edu.ecommerce.model.Role;
import com.edu.ecommerce.security.AccessRole;
import com.edu.ecommerce.service.interfaces.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AccessRole(value = Role.ADMINISTRATOR)
public class CategoryController {

    private final MapperFacade mapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @AccessRole(value = {Role.MANAGER, Role.EXTERNAL, Role.SPECIALIST})
    @GetMapping
    @ApiOperation(value = "Get all categories", response = CategoryDto.class, responseContainer = "List")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        var categoryList = categoryService.findAll();
        return ResponseEntity.ok(mapper.mapAsList(categoryList, CategoryDto.class));
    }

    @AccessRole(value = {Role.MANAGER, Role.EXTERNAL, Role.SPECIALIST})
    @GetMapping("/{id}")
    @ApiOperation(value = "Find category by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user category by id"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.findById(id)));
    }



    @AccessRole(value = {Role.MANAGER, Role.EXTERNAL, Role.SPECIALIST})
    @PostMapping
    @ApiOperation(value = "Create category", response = CategoryDto.class)
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        //TODO проработать вариант "создания" уже существующей в БД Category
        var category = categoryService.create(categoryMapper.fromDto(categoryDto));
        return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update category")
    public ResponseEntity<CategoryDto> updateCategory(@NotNull @PathVariable("id") Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        var category = categoryService.update(categoryId, categoryMapper.fromDto(categoryDto));
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete category")
    public ResponseEntity<Void> delete(@NotNull @PathVariable("id") Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}