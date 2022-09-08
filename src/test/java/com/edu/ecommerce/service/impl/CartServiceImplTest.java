package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.dto.cart.CartDto;
import com.edu.ecommerce.dto.cart.CartItemDto;
import com.edu.ecommerce.dto.product.ProductDto;
import com.edu.ecommerce.mapper.CartItemDtoMapper;
import com.edu.ecommerce.mapper.ProductMapper;
import com.edu.ecommerce.model.Cart;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.repository.CartRepository;
import com.edu.ecommerce.service.interfaces.CartService;
import com.edu.ecommerce.service.interfaces.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


class CartServiceImplTest {

    private CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private CategoryService categoryService = Mockito.mock(CategoryService.class);
    private ProductMapper productMapper = new ProductMapper(categoryService);
    private CartItemDtoMapper cartItemDtoMapper = new CartItemDtoMapper(productMapper);
    private CartService cartService = new CartServiceImpl(cartRepository, cartItemDtoMapper);


    @Test
    public void testListCartItems() {
        //Описание поведение моков
        Mockito.when(cartRepository.findAllByUserOrderByCreatedDateDesc(Mockito.any()))
                .thenReturn(List.of(
                        Cart.builder().quantity(20).product(Product.builder().category(Category.builder().build()).price(220.56).build()).build(),
                        Cart.builder().quantity(30).product(Product.builder().category(Category.builder().build()).price(100.56).build()).build()
                ));


        // ТЕЛО ТЕСТА
        User user = new User();

        CartDto actualCartDto = cartService.listCartItems(user);

        CartDto expectedCartDto = CartDto.builder()
                .cartItems(List.of(
                        CartItemDto.builder()
                                .quantity(20)
                                .product(ProductDto.builder().price(220.56).build())
                                .build(),
                        CartItemDto.builder()
                                .quantity(30)
                                .product(ProductDto.builder().price(100.56).build())
                                .build()
                ))
                .totalCost(7428.0)
                .build();

        Assertions.assertEquals(expectedCartDto, actualCartDto);
    }

}