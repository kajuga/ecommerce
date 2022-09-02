package com.edu.ecommerce.dto.cart;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotNull;


@Builder
@Data
@Slf4j
public class AddToCartDto {

    private @NotNull Long productId;

    private @NotNull Integer quantity;

}