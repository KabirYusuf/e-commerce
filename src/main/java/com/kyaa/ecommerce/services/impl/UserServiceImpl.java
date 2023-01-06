package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.*;
import com.kyaa.ecommerce.data.repositories.UserRepository;
import com.kyaa.ecommerce.dto.requests.*;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.dto.responses.LoginResponse;
import com.kyaa.ecommerce.dto.responses.UpdateUserResponse;
import com.kyaa.ecommerce.enums.Role;
import com.kyaa.ecommerce.exceptions.UserException;
import com.kyaa.ecommerce.services.AddressService;
import com.kyaa.ecommerce.services.CartProductService;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


import static com.kyaa.ecommerce.enums.Role.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest){
        if (userRepository.findUserByUsername(createUserRequest.getUsername()).isPresent())throw new UserException("username already exist");
        if (userRepository.findUserByEmail(createUserRequest.getEmail()).isPresent())throw new UserException("User with this email already exist");
        User user = User.builder().
                email(createUserRequest.getEmail()).
                username(createUserRequest.getUsername()).
                password(createUserRequest.getPassword()).
                role(USER).
                address(createUserRequest.getAddress()).
                cart(new Cart())
                .build();
        userRepository.save(user);
        return modelMapper.map(user, CreateUserResponse.class);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (userRepository.findById(id).
                isEmpty())throw new UserException("User does not exist");
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername (String username){
        if (userRepository.findUserByUsername(username)
                .isEmpty())throw new UserException("User does not exist");
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        getUserById(id);
        userRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void updateUserRole(String username, Role role) {
        userRepository.updateUserRole(username, role );
    }

    @Override
    public UpdateUserResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        Optional<User> foundUser = getUserByUsername(updateUserRequest.getUsername());
        if (updateUserRequest.getFirstName() != null)foundUser.ifPresent(user->user.setFirstName(updateUserRequest.getFirstName()));
        if (updateUserRequest.getLastName() != null)foundUser.ifPresent(user -> user.setLastName(updateUserRequest.getLastName()));
        if (updateUserRequest.getAddress() != null)foundUser.ifPresent(user -> user.setAddress(updateUserRequest.getAddress()));
        userRepository.save(foundUser.get());
        return new UpdateUserResponse();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> foundUser = getUserByUsername(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        if (Objects.equals(loginRequest.getPassword(), foundUser.get().getPassword())){
            loginResponse.setMessage("login successful");
        }else loginResponse.setMessage("password is incorrect");
        return loginResponse;
    }

    @Override
    public AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest) {
        Optional<User> foundUser = getUserByUsername(addProductToCartRequest.getUsername());
        Optional<Product> foundProduct = productService.getProductByName(addProductToCartRequest.getProductName().toLowerCase());
        //Todo: create a model for cart product
        CartProduct cartProduct = new CartProduct();
        cartProduct.setName(foundProduct.get().getName());
        cartProduct.setCategory(foundProduct.get().getCategory());
        cartProduct.setQuantity(addProductToCartRequest.getQuantity());
        cartProduct.setPrice(foundProduct.get().getPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct);
        foundUser.get().getCart().getCartProducts().add(savedCartProduct);
        AddProductToCartResponse addProductToCartResponse = new AddProductToCartResponse();
        addProductToCartResponse.setName(addProductToCartRequest.getProductName());
        addProductToCartResponse.setId(savedCartProduct.getId());
        return addProductToCartResponse;
    }

    @Override
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "Users deleted successfully";
    }

//    @Override
//    public String deleteProductFromCart(String userName, String productName) {
//        Optional<User> foundUser = getUserByUsername(userName);
//        foundUser.
//                get().
//                getCart().
//                getCartProducts().
//                removeIf(cartProduct -> Objects.
//                        equals(cartProduct.getName(), productName));
//        return null;
//    }

//    @Override
//    public CreateProductResponse addProduct(CreateProductRequest createProductRequest, String username) {
//        getUserByUsername(username);
//        return productService.createProduct(createProductRequest);
//    }
}
