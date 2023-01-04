package com.kyaa.ecommerce.dto.requests;

import com.kyaa.ecommerce.data.models.Product;
import lombok.Data;

@Data
public class AddProductToCartRequest {
    private String username;
    private String productName;
    private Integer quantity;
}
