package com.kyaa.ecommerce.data.repositories;

import com.kyaa.ecommerce.data.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
