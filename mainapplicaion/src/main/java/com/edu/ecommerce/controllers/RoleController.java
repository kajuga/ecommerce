package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.role.RoleDto;
import com.edu.ecommerce.model.Role;
import com.edu.ecommerce.security.AccessRole;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping(path = "/role")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AccessRole(value = Role.ADMINISTRATOR)
public class RoleController {

    private final RestTemplate restTemplate;

    @Autowired
    public RoleController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }


    @AccessRole(value = {Role.MANAGER, Role.EXTERNAL, Role.SPECIALIST})
    @GetMapping
    @ApiOperation(value = "Get all user roles", response = RoleDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get all user roles"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<RoleDto>> findAll() {
        ResponseEntity<RoleDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8082/role", RoleDto[].class);
        List<RoleDto> roles = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
        return ResponseEntity.ok(roles);
    }

    @AccessRole(value = {Role.MANAGER, Role.EXTERNAL, Role.SPECIALIST})
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get user's role by role id", response = RoleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user roles by id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<RoleDto> findById(@NotNull @PathVariable("id") Long id) {
        return restTemplate.getForEntity("http://localhost:8082/role/"+ id, RoleDto.class);
    }

    @AccessRole(value = {Role.MANAGER, Role.SPECIALIST})
    @PostMapping
    @ApiOperation(value = "Create role", response = RoleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create user role"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<RoleDto> create(@Valid @RequestBody RoleDto roleDto) {
        return restTemplate.postForEntity("http://localhost:8082/role/", roleDto, RoleDto.class);
    }

    @AccessRole(value = {Role.MANAGER, Role.SPECIALIST})
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update user role", response = RoleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update user role"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<RoleDto> update(@NotNull @PathVariable("id") Long id, @RequestBody RoleDto roleDto) {
       restTemplate.put("http://localhost:8082/role/" + id, roleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AccessRole(value = {Role.MANAGER, Role.SPECIALIST})
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete user role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete user role"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> delete(@NotNull @PathVariable("id") Long id) {
        restTemplate.delete("http://localhost:8082/role/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
