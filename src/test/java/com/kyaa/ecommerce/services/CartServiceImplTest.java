package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.Address;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.data.repositories.CartProductRepository;
import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.dto.requests.CreateUserRequest;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.enums.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartServiceImplTest {
@Autowired
private CartService cartService;
@Autowired
private CartProductRepository cartProductRepository;
@Autowired
private UserService userService;
@Autowired
private ProductService productService;
private CreateUserRequest createUserRequest;
private CreateProductRequest createProductRequest;
private AddProductToCartRequest addProductToCartRequest;


    @BeforeEach
    void setUp() {
        Address address = new Address();
        createUserRequest = new CreateUserRequest();
        createUserRequest.setAddress(address);
        createUserRequest.setEmail("k.gmail.com");
        createUserRequest.setPassword("1234");
        createUserRequest.setUsername("kyaa");

        createProductRequest = new CreateProductRequest();
        createProductRequest.setName("milk");
        createProductRequest.setCategory(Category.BEVERAGES);
        createProductRequest.setPrice(BigDecimal.valueOf(50));
        createProductRequest.setQuantity(4);


    }

    @AfterEach
    void tearDown() {
        cartProductRepository.deleteAll();
        userService.deleteAllUsers();
        productService.deleteAllProducts();
    }
    @Test
    void productCanBeAddedToAUserCart(){
////        userService.createUser(createUserRequest);
////        productService.createProduct(createProductRequest);
////        Optional<User> foundUser = userService.getUserByUsername("kyaa");
////        var foundUserSizeOfCartBeforeAddingProduct = foundUser.get().getCart().getCartProducts().size();
////        assertEquals(0, foundUserSizeOfCartBeforeAddingProduct);
////        System.out.println(productService.getAllProducts());
////        System.out.println(userService.getAllUsers());
////        addProductToCartRequest = new AddProductToCartRequest();
////        addProductToCartRequest.setUsername("kyaa");
////        addProductToCartRequest.setProductName("milk");
////        addProductToCartRequest.setQuantity(2);
////        cartService.addProductToCart(addProductToCartRequest);
////        Optional<User> foundUserAfterAddingProduct = userService.getUserByUsername("kyaa");
////        System.out.println(foundUserAfterAddingProduct.get().getCart());
////        var foundUserSizeOfCartAfterAddingProduct = foundUserAfterAddingProduct.get().getCart().getCartProducts().size();
////        assertEquals(1, foundUserSizeOfCartAfterAddingProduct);
//
//
        userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUsername("kyaa");
        var numberOfProductsInUserCartBeforeAddingAProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartBeforeAddingAProduct);
        productService.createProduct(createProductRequest);
//        Optional<Product> foundProduct = productService.getProductByName("milk");
//        System.out.println(productService.getAllProducts().size());
//        System.out.println(productService.getAllProducts().get(0));
//        System.out.println(foundProduct.get());
        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
//        addProductToCartRequest.setProduct(foundProduct.get());
        addProductToCartRequest.setUsername("kyaa");
        addProductToCartRequest.setProductName("milk");
        addProductToCartRequest.setQuantity(2);
        cartService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUsername("kyaa");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart());
        var numberOfProductsInUserCartAfterAddingAProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1, numberOfProductsInUserCartAfterAddingAProduct);
    }
}