package com.edu.ecommerce.service;

import com.edu.ecommerce.dto.cart.AddToCartDto;
import com.edu.ecommerce.dto.cart.CartDto;
import com.edu.ecommerce.dto.cart.CartItemDto;
import com.edu.ecommerce.mapper.CartItemDtoMapper;
import com.edu.ecommerce.model.Cart;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemDtoMapper cartItemDtoMapper;


    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        // first get all the cart items for user
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        // convert cart to cartItemDto
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            cartItems.add(cartItemDtoMapper.toDto(cart));
        }

        // calculate the total price
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        }

        // return cart DTO
        return new CartDto(cartItems,totalCost);
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}