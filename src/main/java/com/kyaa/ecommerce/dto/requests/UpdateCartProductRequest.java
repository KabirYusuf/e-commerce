package com.kyaa.ecommerce.dto.requests;

import lombok.Data;

@Data
public class UpdateCartProductRequest {
    private Long cartProductId;
    private String cartProductName;
    private Integer quantity;
}
