package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.dto.requests.*;
import com.kyaa.ecommerce.dto.responses.*;
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
    void deleteAllUsers();


    void deleteProductFromCart(Long cartProductId);

    OrderProductResponse orderProduct(OrderProductRequest orderProductRequest);
}
