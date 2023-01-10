package com.kyaa.ecommerce.dto.requests;

import com.kyaa.ecommerce.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private Category category;
}
