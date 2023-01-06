package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.requests.CreateUserRequest;
import com.kyaa.ecommerce.dto.requests.LoginRequest;
import com.kyaa.ecommerce.dto.requests.UpdateUserRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.dto.responses.LoginResponse;
import com.kyaa.ecommerce.dto.responses.UpdateUserResponse;
import com.kyaa.ecommerce.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUserById(Long id);
    void updateUserRole(String username, Role role);

    UpdateUserResponse updateUserInfo(UpdateUserRequest updateUserRequest);
    LoginResponse login(LoginRequest loginRequest);
//    CreateProductResponse addProduct(CreateProductRequest createProductRequest, String username);
    AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest);
    String deleteAllUsers();

//    String deleteProductFromCart(String username, String productName);
}
