package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.mapper.UserMapper;
import com.edu.ecommerce.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/signUp")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class SignUpUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ApiOperation(value = "Sign up")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Sign up {}", userDto);
        var user = userService.create(userMapper.fromDto(userDto));
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.CREATED);
    }

}