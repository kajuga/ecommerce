package com.edu.userservice.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    @Size(message = "Name must be from 1 to 55 characters.", min = 1, max = 55)
    private String name;

    @Size(message = "Description must be from 1 to 255 characters.", min = 1, max = 255)
    @JsonProperty("description")
    private String description;

}
