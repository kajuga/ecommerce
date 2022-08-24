package com.edu.ecommerce.controllers;


import com.edu.ecommerce.common.ApiResponse;
import com.edu.ecommerce.dto.product.ProductDto;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.mapper.ProductMapper;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.model.WishList;
import com.edu.ecommerce.repository.ProductRepository;
import com.edu.ecommerce.service.AuthenticationService;
import com.edu.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProductRepository productRepository;

    private ProductMapper productMapper;

    /*
    API to add a new product in wishlist
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto, @RequestParam("token") String token) throws AuthenticationFailException {
        // first authenticate if the token is valid
        authenticationService.authenticate(token);
        // then fetch the user linked to the token
        User user = authenticationService.getUser(token);
        Product product = productRepository.getById(productDto.getId());
        // save wish list
        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException {
        // first authenticate if the token is valid
        authenticationService.authenticate(token);
        // then fetch the user linked to the token
        User user = authenticationService.getUser(token);
        // first retrieve the wishlist items
        List<WishList> wishLists = wishListService.readWishList(user);
        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists) {
            // change each product to product DTO
            products.add(productMapper.toDto(wishList.getProduct()));
        }
        // send the response to user
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}