package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.dto.cart.AddToCartDto;
import com.edu.ecommerce.dto.cart.CartDto;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;

public interface CartService {

    public void addToCart(AddToCartDto addToCartDto, Product product, User user);

    public CartDto listCartItems(User user);

    public void deleteUserCartItems(User user);


}