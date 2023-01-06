package com.kyaa.ecommerce.dto.requests;

import lombok.Data;

@Data
public class DeleteCartProductFromCartRequest {
    private String username;
    private Long cartProductId;
}
