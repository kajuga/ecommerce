package com.edu.ecommerce.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
public class Login {

    @NotNull(message = "Email is mandatory.")
    @Size(message = "Email must be from 1 to 255 characters.", min = 1)
    private String email;

    @NotNull(message = "Password is mandatory.")
    @Size(message = "Password must be from 1 to 255 characters.", min = 1)
    private String password;
}