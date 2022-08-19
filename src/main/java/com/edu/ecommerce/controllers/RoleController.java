package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.role.RoleDto;
import com.edu.ecommerce.model.Role;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.security.AccessRole;
import com.edu.ecommerce.service.interfaces.RoleService;
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
@RequestMapping(path = "/role")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AccessRole(value = Role.ADMINISTRATOR)
public class RoleController {

    private final MapperFacade mapper;
    private final RoleService roleService;


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
        var userRoleList = roleService.findAll();
        return ResponseEntity.ok(mapper.mapAsList(userRoleList, RoleDto.class));
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
        UserRole roleById = roleService.findById(id);
        return ResponseEntity.ok(mapper.map(roleById, RoleDto.class));
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
        UserRole userRole = roleService.create(mapper.map(roleDto, UserRole.class));
        return new ResponseEntity<>(mapper.map(userRole, RoleDto.class), HttpStatus.CREATED);
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
        UserRole mappedUserRole = mapper.map(roleDto, UserRole.class);
        UserRole updatedUserRole = roleService.update(id, mappedUserRole);
        return ResponseEntity.ok(mapper
                .map(updatedUserRole, RoleDto.class));
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
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
