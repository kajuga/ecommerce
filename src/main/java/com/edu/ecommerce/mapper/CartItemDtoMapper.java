package com.edu.ecommerce.mapper;

import com.edu.ecommerce.dto.cart.CartItemDto;
import com.edu.ecommerce.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemDtoMapper {

    @Autowired
    ProductMapper productMapper;

    public CartItemDto toDto(Cart cart) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cart.getId());
        dto.setQuantity(cart.getQuantity());
        dto.setProduct(productMapper.toDto(cart.getProduct()));
        return dto;
    }
}
