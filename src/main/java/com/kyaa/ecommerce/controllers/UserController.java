package com.kyaa.ecommerce.controllers;

import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.requests.CreateUserRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }
    @PatchMapping ("/add-product")
    public AddProductToCartResponse addProduct(@RequestBody AddProductToCartRequest addProductToCartRequest){
        return userService.addProductToCart(addProductToCartRequest);
    }
}
