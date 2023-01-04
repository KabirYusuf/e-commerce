package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;

public interface CartService {
    AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest);

    String deleteProductFromCart(Long cartProductId);


}
