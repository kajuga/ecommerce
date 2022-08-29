package com.edu.ecommerce.dto.cart;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;


@Builder
@Data
@Slf4j
public class AddToCartDto {

    private Long id;
    private @NotNull Long productId;
    private @NotNull Integer quantity;


}