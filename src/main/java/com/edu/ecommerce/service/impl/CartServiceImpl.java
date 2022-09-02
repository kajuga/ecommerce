package com.edu.ecommerce.service.impl;


import com.edu.ecommerce.dto.cart.AddToCartDto;
import com.edu.ecommerce.dto.cart.CartDto;
import com.edu.ecommerce.dto.cart.CartItemDto;
import com.edu.ecommerce.mapper.CartItemDtoMapper;
import com.edu.ecommerce.mapper.ProductMapper;
import com.edu.ecommerce.model.Cart;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.repository.CartRepository;
import com.edu.ecommerce.service.interfaces.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    ProductMapper productMapper;

    @Override
    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    @Override
    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart.getId(), cart.getQuantity(), productMapper.toDto(cart.getProduct()));
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        }
        return new CartDto(cartItems,totalCost);
    }

    @Override
    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}