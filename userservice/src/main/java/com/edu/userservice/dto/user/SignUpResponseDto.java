package com.edu.userservice.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpResponseDto {

    private String status;
    private String message;


}