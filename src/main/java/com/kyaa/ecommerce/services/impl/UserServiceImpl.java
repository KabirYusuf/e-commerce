package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.*;
import com.kyaa.ecommerce.data.repositories.UserRepository;
import com.kyaa.ecommerce.dto.requests.*;
import com.kyaa.ecommerce.dto.responses.*;
import com.kyaa.ecommerce.enums.Role;
import com.kyaa.ecommerce.exceptions.ProductException;
import com.kyaa.ecommerce.exceptions.UserException;
import com.kyaa.ecommerce.services.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OrderHistoryService orderHistoryService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest){
        if (userRepository.findUserByUsername(createUserRequest.getUsername().toLowerCase()).isPresent())throw new UserException("username already exist");
        if (userRepository.findUserByEmail(createUserRequest.getEmail()).isPresent())throw new UserException("User with this email already exist");
        User user = User.builder().
                email(createUserRequest.getEmail()).
                username(createUserRequest.getUsername().toLowerCase()).
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
        if (userRepository.findUserByUsername(username.toLowerCase())
                .isEmpty())throw new UserException("User does not exist");
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserRole(UpdateUserRoleRequest updateUserRoleRequest) {
        userRepository.updateUserRole(updateUserRoleRequest.getUsername(), updateUserRoleRequest.getRole() );
    }

    @Override
    public UpdateUserResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        Optional<User> foundUser = getUserByUsername(updateUserRequest.getUsername().toLowerCase());
        if (updateUserRequest.getFirstName() != null)foundUser.ifPresent(user->user.setFirstName(updateUserRequest.getFirstName()));
        if (updateUserRequest.getLastName() != null)foundUser.ifPresent(user -> user.setLastName(updateUserRequest.getLastName()));
        if (updateUserRequest.getAddress() != null)foundUser.ifPresent(user -> user.setAddress(updateUserRequest.getAddress()));
        userRepository.save(foundUser.get());
        return new UpdateUserResponse();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> foundUser = getUserByUsername(loginRequest.getUsername().toLowerCase());
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

        CartProduct cartProduct = new CartProduct();
        cartProduct.setName(foundProduct.get().getName());
        cartProduct.setCategory(foundProduct.get().getCategory());
        cartProduct.setQuantity(addProductToCartRequest.getQuantity());
        cartProduct.setUnitPrice(foundProduct.get().getUnitPrice());
        cartProduct.setTotalPrice(foundProduct.get().getUnitPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProduct);
        foundUser.get().getCart().getCartProducts().add(savedCartProduct);
        AddProductToCartResponse addProductToCartResponse = new AddProductToCartResponse();
        addProductToCartResponse.setName(addProductToCartRequest.getProductName());
        addProductToCartResponse.setId(savedCartProduct.getId());
        return addProductToCartResponse;
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteProductFromCart(Long cartProductId) {
//        Optional<User> foundUser = getUserByUsername(deleteCartProductFromCartRequest.getUsername());
//        foundUser.
//                get().
//                getCart().
//                getCartProducts().
//                removeIf(cartProduct -> Objects.
//                        equals(cartProduct.getId(), deleteCartProductFromCartRequest.getCartProductId()));
        cartProductService.deleteCartProductById(cartProductId);
//        return "Deleted successfully";
    }

    @Override
    public OrderProductResponse orderProduct(OrderProductRequest orderProductRequest) {
        Optional<User> foundUser = getUserById(orderProductRequest.getUserId());
        Product foundProduct = productService.getProductById(orderProductRequest.getProductId());
        OrderProductResponse orderProductResponse = new OrderProductResponse();
        if (foundProduct.getQuantity() <= orderProductRequest.
                getQuantity())throw new ProductException("Insufficient quantity");
        else if (foundProduct.
                getUnitPrice().
                multiply(new BigDecimal(orderProductRequest.getQuantity())).
                compareTo(orderProductRequest.getPrice()) > 0)throw new ProductException("Insufficient Amount");
        else{
            orderProductResponse.setMessage("Product ordered successfully");
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setProduct(foundProduct);
            orderHistory.setUser(foundUser.get());
            orderHistory.setProductName(foundProduct.getName());
            orderHistory.setQuantity(orderProductRequest.getQuantity());
            orderHistory.setUnitPrice(foundProduct.getUnitPrice());
            orderHistory.setTotalPrice(foundProduct.getUnitPrice().multiply(new BigDecimal(orderProductRequest.getQuantity())));
            orderHistoryService.save(orderHistory);
            UpdateProductRequest updateProductRequest = new UpdateProductRequest();
            updateProductRequest.setProductName(foundProduct.getName());
            updateProductRequest.setQuantity(foundProduct.getQuantity() - orderProductRequest.getQuantity());
            updateProductRequest.setPrice(foundProduct.getUnitPrice());
            productService.updateProduct(updateProductRequest);
        }
        return orderProductResponse;
    }

//    @Override
//    public CreateProductResponse addProduct(CreateProductRequest createProductRequest, String username) {
//        getUserByUsername(username);
//        return productService.createProduct(createProductRequest);
//    }
}
