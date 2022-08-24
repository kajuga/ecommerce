package com.edu.ecommerce.controllers;

import com.edu.ecommerce.common.ApiResponse;
import com.edu.ecommerce.dto.cart.AddToCartDto;
import com.edu.ecommerce.dto.cart.CartDto;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.exceptions.ProductNotExistException;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.service.AuthenticationService;
import com.edu.ecommerce.service.CartService;
import com.edu.ecommerce.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/cart")
public class CartController {

//    @Autowired
//    CartService cartService;
//
//    @Autowired
//    ProductServiceImpl productService;
//
//    @Autowired
//    AuthenticationService authenticationService;
//
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)
//            throws ProductNotExistException, AuthenticationFailException {
//
//        // first authenticate the token
//        authenticationService.authenticate(token);
//
//        // get the user
//        User user = authenticationService.getUser(token);
//
//        // find the product to add and add item by service
//        Product product = productService.findById(addToCartDto.getProductId());
//        cartService.addToCart(addToCartDto, product, user);
//
//        // return response
//        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
//
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
//        // first authenticate the token
//        authenticationService.authenticate(token);
//
//        // get the user
//        User user = authenticationService.getUser(token);
//
//        // get items in the cart for the user.
//        CartDto cartDto = cartService.listCartItems(user);
//
//        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
//    }

}