package com.edu.userservice.controllers;

import com.edu.userservice.dto.role.RoleDto;
import com.edu.userservice.dto.user.UserDto;
import com.edu.userservice.mapper.UserMapper;
import com.edu.userservice.model.UserRole;
import com.edu.userservice.security.UserPrincipal;
import com.edu.userservice.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MapperFacade mapperFacade;


    @GetMapping
    @ApiOperation(value = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get all users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<UserDto>> findAllUsers() {
        log.info("Find all user");
        var userDtos = userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/external")
    @ApiOperation(value = "Get all external users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get all external users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<UserDto>> findAllExternalUsers() {
        var allExternalUsers = userService.getAllExternalUsers();
        var collect = allExternalUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @GetMapping("/user-role/{roleId}")
    @ApiOperation(value = "Get all users by user role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get all users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<UserDto>> findAllExternalUsersByExternalTypeId(@NotNull @PathVariable("roleId") Long id) {
        var allUsersByRoleId = userService.getAllUsersByRoleId(id);
        var collect = allUsersByRoleId.stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<UserDto> findUserById(@NotNull @PathVariable("id") Long id) {

        var user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping(path = "/current")
    @ApiOperation(value = "Get current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<UserDto> findCurrentUser(@ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var user = userService.findById(userPrincipal.getId());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping(path = "/{id}/role")
    @ApiOperation(value = "Get user role by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user roles"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<RoleDto> findUserRoleByUserId(@NotNull @PathVariable("id") Long id) {

        var user = userService.findById(id);
        UserRole userRole = user.getUserRole();
        return ResponseEntity.ok(mapperFacade.map(userRole, RoleDto.class));
    }

    @PostMapping
    @ApiOperation(value = "Create user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        var user = userService.create(userMapper.fromDto(userDto));
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<UserDto> updateUser(@NotNull @PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        var user = userService.update(id, userMapper.fromDto(userDto));
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully change the status to removed for user"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> delete(@NotNull @PathVariable("id") Long id) {
            userService.delete(id);
            return ResponseEntity.ok().build();
    }
}