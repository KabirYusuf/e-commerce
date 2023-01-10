package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.CartProduct;
import com.kyaa.ecommerce.dto.requests.UpdateCartProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CartProductService {
    CartProduct  createCartProduct(CartProduct cartProduct);

    void deleteCartProductById(Long id);

    List<CartProduct> getAllCartProducts();

    String deleteAllCartProducts();

    CartProduct updateCartProduct(UpdateCartProductRequest updateCartProductRequest);

    CartProduct findCartProductById(Long cartProductId);

    List<CartProduct>getCartProductsByName(String name);

    void updateListOfCartProducts(String name, BigDecimal price);
}
