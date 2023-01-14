package com.kyaa.ecommerce.controllers;

import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.requests.CreateUserRequest;
import com.kyaa.ecommerce.dto.requests.OrderProductRequest;
import com.kyaa.ecommerce.dto.requests.UpdateUserRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.dto.responses.OrderProductResponse;
import com.kyaa.ecommerce.dto.responses.UpdateUserResponse;
import com.kyaa.ecommerce.enums.Role;
import com.kyaa.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }
    @GetMapping("/get-user-by-id/{userId}")
        public ResponseEntity<Optional<User>> getUserById(@PathVariable ("userId") Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable ("username") String username){
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @PutMapping("/update-user-role/{username}")
    public String changeUserRole(@PathVariable("username") String username,@RequestBody Role role){
        userService.updateUserRole(username, role);
//        return  new ResponseEntity<>("updated successfully", HttpStatus.OK);
        return "success";
    }
    @PutMapping("/update-user")
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        return  new ResponseEntity<>(userService.updateUserInfo(updateUserRequest), HttpStatus.OK);
    }
    @PostMapping("/add-product-to-cart")
    public ResponseEntity<AddProductToCartResponse> addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest){
        return new ResponseEntity<>(userService.addProductToCart(addProductToCartRequest), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete-user-by-id/{userId}")
    public String deleteUserById(@PathVariable("userId") Long userId){
        userService.deleteUserById(userId);
        return "User deleted successfully";
    }
    @DeleteMapping()
    public String deleteAllUsers(){
        userService.deleteAllUsers();
        return "Users deleted successfully";
    }

    @DeleteMapping("/delete-product-from-cart/{cartProductId}")
    public String deleteProductFromCart(@PathVariable("cartProductId") Long cartProductId){
        userService.deleteProductFromCart(cartProductId);
        return "Cart product deleted successfully";
    }

    @PostMapping("/order-product")
    public ResponseEntity<OrderProductResponse> orderProduct(@RequestBody OrderProductRequest orderProductRequest){
        return new ResponseEntity<>(userService.orderProduct(orderProductRequest), HttpStatus.CREATED);
    }

}
