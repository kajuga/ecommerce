package com.edu.ecommerce.controllers;

import com.edu.ecommerce.dto.login.LoginDto;
import com.edu.ecommerce.model.JwtToken;
import com.edu.ecommerce.model.Login;
import com.edu.ecommerce.service.interfaces.TokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
@CrossOrigin
public class TokenController {

    private final TokenService tokenService;

    @PostMapping(path = "/login")
    @ApiOperation(value = "Create token", response = JwtToken.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created token"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<JwtToken> getToken(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(tokenService.createToken(fromDto(loginDto)));

    }


    private Login fromDto(LoginDto loginDto) {
        return Login.builder()
                .email(loginDto.getEmail())
                .password(loginDto.getPassword())
                .build();
    }

}