package com.kyaa.ecommerce.dto.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
