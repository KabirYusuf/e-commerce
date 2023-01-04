package com.kyaa.ecommerce.controllers;

import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.dto.requests.CreateUserRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(productService.createProduct(createProductRequest), HttpStatus.CREATED);
    }

}
