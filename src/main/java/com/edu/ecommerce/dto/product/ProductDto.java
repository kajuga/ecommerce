package com.edu.ecommerce.dto.product;

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
public class ProductDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String name;
    private String imageURL;
    private double price;
    private String description;

    @Size(message = "CategoryId must be from 4 characters.", min = 4, max = 4)
    @Pattern(regexp = "[(?=.*\\d).{4}]+",
            message = "CategoryId can contain only digits")
    private Long categoryId;

}
