package com.kyaa.ecommerce.data.dto.requests;

import com.kyaa.ecommerce.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private Category category;
    private BigDecimal price;
    private Integer quantity;
}
