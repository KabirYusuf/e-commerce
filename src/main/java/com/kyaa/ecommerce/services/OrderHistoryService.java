package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.OrderHistory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderHistoryService {
    OrderHistory save(OrderHistory orderHistory);
    void deleteOrderHistories();
    List<OrderHistory> orderHistories();
}
