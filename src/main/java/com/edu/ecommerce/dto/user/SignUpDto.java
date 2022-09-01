package com.edu.ecommerce.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpDto {



    @Size(message = "First name can't be larger than 255 symbols", max = 255)
    @Pattern(regexp = "[A-Za-z\u0430-\u044f\u0451\u0401\u0410-\u044f\\-]+",
            message = "First name can contain only cyrillic and english letters, -")
    private String firstName;

    @Size(message = "Last name can't be larger than 255 symbols", max = 255)
    @Pattern(regexp = "[A-Za-z\u0430-\u044f\u0451\u0401\u0410-\u044f\\-]+",
            message = "Last name can contain only cyrillic and english letters, -")
    private String lastName;

    @Size(message = "email must be from 1 to 100 characters.", min = 1, max = 100)
    @Pattern(regexp = "[A-Za-z\\.@]+",
            message = "Email name can contain only english letters, @ and .")
    private String email;


    @Size(message = "password must be from 8 to 10 characters.At least One Upper and Lower Case Character, At least one digit, At least one symbol/special character @$!%*#?&^_-", min = 8, max = 10)
    @Pattern(regexp = "[(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,10}]+",
            message = "Password can contain only english letters, digits and special symbols")
//    @ApiModelProperty(hidden = true)
    private String password;

    @Size(message = "RoleId must be from 4 characters.", min = 4, max = 4)
    @Pattern(regexp = "[(?=.*\\d).{4}]+",
            message = "RoleId can contain only digits")
    private Long roleId;

}