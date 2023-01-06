package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.CartProduct;

import java.util.List;

public interface CartProductService {
    CartProduct  createCartProduct(CartProduct cartProduct);

    void deleteCartProductById(Long id);

    List<CartProduct> getAllCartProducts();

    String deleteAllCartProducts();
}
