package com.edu.ecommerce.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpDto {


    @ApiModelProperty(hidden = true)
    private Long id;


    @Size(message = "First name can't be larger than 255 symbols", max = 255)
    @Pattern(regexp = "[A-Za-z\u0430-\u044f\u0451\u0401\u0410-\u044f\\-]+",
            message = "First name can contain only cyrillic and english letters, -")
    private String firstName;

    private String lastName;

    private String email;

    private String password;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}