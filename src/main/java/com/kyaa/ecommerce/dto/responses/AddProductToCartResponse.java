package com.kyaa.ecommerce.dto.responses;

import lombok.Data;

@Data
public class AddProductToCartResponse {
    private String name;
    private Long id;
    private final String message = name + " add to cart";
}
