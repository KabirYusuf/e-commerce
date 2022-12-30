package com.kyaa.ecommerce.data.dto.requests;

import com.kyaa.ecommerce.data.models.Product;
import lombok.Data;

@Data
public class AddProductToCartRequest {
    private String username;
    private Product product;
}
