package com.edu.ecommerce.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {

    @ApiModelProperty(value = "email", example = "huntflow-test-16@andersenlab.com")
    @NotNull(message = "Email is mandatory.")
    @Size(message = "Email must be from 1 to 255 characters.", min = 1)
    private String email;

    @ApiModelProperty(value = "password", example = "159753CFThn")
    @NotNull(message = "Password is mandatory.")
    @Size(message = "Password must be from 1 to 255 characters.", min = 1)
    private String password;
}