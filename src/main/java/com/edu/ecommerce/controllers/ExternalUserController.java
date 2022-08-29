package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.login.LoginDto;
import com.edu.ecommerce.dto.user.SignInResponseDto;
import com.edu.ecommerce.dto.user.SignUpDto;
import com.edu.ecommerce.dto.user.SignUpResponseDto;
import com.edu.ecommerce.dto.user.UserDto;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.exceptions.CustomException;
import com.edu.ecommerce.mapper.UserMapper;
import com.edu.ecommerce.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
//@CrossOrigin
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
    public ResponseEntity<UserDto> SignUp(@Valid @RequestBody UserDto userDto) {
        var user = userService.createUnregisteredExternalUser(userMapper.fromDto(userDto));
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.CREATED);
    }


    @PostMapping("/signIn")
    @ApiOperation(value = "User Sign In (login registered user)")
    public SignInResponseDto SignIn(@Valid @RequestBody LoginDto loginDto) throws CustomException, AuthenticationFailException {
        return userService.signIn(loginDto);
    }

}
