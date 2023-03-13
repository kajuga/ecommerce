package com.edu.ecommerce.dto.cart;


import com.edu.ecommerce.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDto {

    private Long id;
    private @NotNull Integer quantity;
    private @NotNull ProductDto product;
}