package com.kyaa.ecommerce.controllers;

import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.dto.requests.*;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.dto.responses.UpdateUserResponse;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(productService.createProduct(createProductRequest), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @DeleteMapping()
    public String deleteAllProducts(){
        productService.deleteAllProducts();
        return "Users deleted successfully";
    }
    @PutMapping("/update-product")
    public ResponseEntity<String> updateUser(@RequestBody UpdateProductRequest updateProductRequest){
        return  new ResponseEntity<>(productService.updateProduct(updateProductRequest), HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getUserById(@PathVariable ("productId") Long productId){
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

}
