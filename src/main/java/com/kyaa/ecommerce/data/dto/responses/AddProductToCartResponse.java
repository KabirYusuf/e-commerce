package com.kyaa.ecommerce.data.dto.responses;

import lombok.Data;

@Data
public class AddProductToCartResponse {
    private String name;
    private final String message = name + " add to cart";
}
