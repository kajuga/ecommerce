package com.edu.ecommerce.dto.cart;


import com.edu.ecommerce.model.Cart;
import com.edu.ecommerce.model.Product;
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

//    @ApiModelProperty(hidden = true)
    private Long id;

    private @NotNull Integer quantity;

    //TODO заменить продукт на дто какой-нибудь
    private @NotNull Product product;

    public CartItemDto(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());

    }
}