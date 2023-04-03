package com.edu.userservice.controllers;

import com.edu.userservice.dto.user.LoginDto;
import com.edu.userservice.dto.user.SignInResponseDto;
import com.edu.userservice.dto.user.SignUpDto;
import com.edu.userservice.dto.user.SignUpResponseDto;
import com.edu.userservice.exceptions.CustomException;
import com.edu.userservice.mapper.UserMapper;
import com.edu.userservice.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@Slf4j
public class ExternalUserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MapperFacade mapperFacade;


    @PostMapping("/signUp")
    @ApiOperation(value = "User Registration (create new user)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public SignUpResponseDto SignUp(@Valid @RequestBody SignUpDto signUpDto) throws CustomException {
        return userService.createUnregisteredExternalUser(signUpDto);
    }

}
