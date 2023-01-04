package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.data.models.Product;

import java.util.*;

public interface ProductService {
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);
    Optional<Product> getProductByName(String name);
    List<Product> getAllProducts();
    String deleteAllProducts();
}
