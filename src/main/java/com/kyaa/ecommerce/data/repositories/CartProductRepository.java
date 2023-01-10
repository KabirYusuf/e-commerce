package com.kyaa.ecommerce.data.repositories;

import com.kyaa.ecommerce.data.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    void deleteCartProductById(Long id);
    CartProduct findCartProductById(Long id);
    List<CartProduct> getCartProductsByName(String cartProductName);
}
