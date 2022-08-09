package com.edu.ecommerce.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @ApiModelProperty(hidden = true)
    private Long id;


    @Size(message = "First name can't be larger than 255 symbols", max = 255)
    @Pattern(regexp = "[A-Za-z\u0430-\u044f\u0451\u0401\u0410-\u044f\\-]+",
            message = "First name can contain only cyrillic and english letters, -")
    private String firstName;

    @Size(message = "Last name can't be larger than 255 symbols", max = 255)
    @Pattern(regexp = "[A-Za-z\u0430-\u044f\u0451\u0401\u0410-\u044f\\-]+",
            message = "Last name can contain only cyrillic and english letters, -")
    private String lastName;

    @Size(message = "Corporate email must be from 1 to 100 characters.", min = 1, max = 100)
    @Pattern(regexp = "[A-Za-z\\.@]+",
            message = "Email name can contain only english letters, @ and .")
    private String email;

    private Long roleId;

}