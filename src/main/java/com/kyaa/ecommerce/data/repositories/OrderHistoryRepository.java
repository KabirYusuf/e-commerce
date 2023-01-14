package com.kyaa.ecommerce.data.repositories;

import com.kyaa.ecommerce.data.models.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}
