package com.kyaa.ecommerce.data.repositories;

import com.kyaa.ecommerce.data.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
