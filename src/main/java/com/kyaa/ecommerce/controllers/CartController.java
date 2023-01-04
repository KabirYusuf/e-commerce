package com.kyaa.ecommerce.controllers;

import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PatchMapping("/add-product")
    public AddProductToCartResponse addProduct(@RequestBody AddProductToCartRequest addProductToCartRequest){
        return cartService.addProductToCart(addProductToCartRequest);
    }
}
