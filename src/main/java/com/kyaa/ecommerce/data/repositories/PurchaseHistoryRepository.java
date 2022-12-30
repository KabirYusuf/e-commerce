package com.kyaa.ecommerce.data.repositories;

import com.kyaa.ecommerce.data.models.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
}
