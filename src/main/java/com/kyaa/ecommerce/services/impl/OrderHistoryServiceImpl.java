package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.OrderHistory;
import com.kyaa.ecommerce.data.repositories.OrderHistoryRepository;
import com.kyaa.ecommerce.services.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);
    }

    @Override
    public void deleteOrderHistories() {
        orderHistoryRepository.deleteAll();
    }

    @Override
    public List<OrderHistory> orderHistories() {
        return orderHistoryRepository.findAll();
    }
}
