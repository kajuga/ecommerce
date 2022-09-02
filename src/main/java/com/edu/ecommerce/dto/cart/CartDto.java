package com.edu.ecommerce.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {

    @JsonProperty("Cart items")
    private List<CartItemDto> cartItems;

    @JsonProperty("Total cost")
    private double totalCost;

}
