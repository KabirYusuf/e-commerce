package com.kyaa.ecommerce.dto.responses;

import com.kyaa.ecommerce.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductResponse {
    private Long id;
    private String name;
    private final String message = name + " added successfully";
}
