package com.edu.ecommerce.controllers;

import com.edu.ecommerce.common.ApiResponse;
import com.edu.ecommerce.dto.cart.AddToCartDto;
import com.edu.ecommerce.dto.cart.CartDto;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.service.impl.AuthenticationServiceImpl;
import com.edu.ecommerce.service.impl.CartServiceImpl;
import com.edu.ecommerce.service.impl.ProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class CartController {

    private final CartServiceImpl cartService;
    private final ProductServiceImpl productService;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/add")
    @ApiOperation(value = "Add product to Cart")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // first authenticate the token
        authenticationServiceImpl.authenticate(token);
        // get the user
        User user = authenticationServiceImpl.getUser(token);
        // find the product to add and add item by com.edu.fileservice.service
        Product product = productService.findById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);

        // return response
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }

    @GetMapping("/get")
    @ApiOperation(value = "Get products from a Cart")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationServiceImpl.authenticate(token);
        User user = authenticationServiceImpl.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "Delete current user's Cart")
    public ResponseEntity<Void> delete(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationServiceImpl.authenticate(token);
        User user = authenticationServiceImpl.getUser(token);
        cartService.deleteCurrentUserCart(user);
        return ResponseEntity.ok().build();
    }


}