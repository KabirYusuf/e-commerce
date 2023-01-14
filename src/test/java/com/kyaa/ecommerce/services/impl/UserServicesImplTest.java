package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.Address;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.dto.requests.*;
import com.kyaa.ecommerce.dto.responses.*;
import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.services.CartProductService;
import com.kyaa.ecommerce.services.OrderHistoryService;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static com.kyaa.ecommerce.enums.Category.*;
import static com.kyaa.ecommerce.enums.Role.*;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class UserServicesImplTest {
@Autowired
private UserService userService;
@Autowired
private CartProductService cartProductService;
@Autowired
private ProductService productService;
@Autowired
private OrderHistoryService orderHistoryService;

private CreateProductRequest createProductRequest;
private CreateUserRequest createUserRequest;
private CreateUserResponse createUserResponse;
    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setTown("Bwari");
        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("kabir@gmail.com");
        createUserRequest.setUsername("kyaa");
        createUserRequest.setPassword("pass1234");
        createUserRequest.setAddress(address);

        createProductRequest = new CreateProductRequest();
        createProductRequest.setName("milk");
        createProductRequest.setQuantity(3);
        createProductRequest.setPrice(BigDecimal.valueOf(50));
        createProductRequest.setCategory(BEVERAGES);
    }
    @AfterEach
    void afterEach(){
        cartProductService.deleteAllCartProducts();
        orderHistoryService.deleteOrderHistories();
        userService.deleteAllUsers();
        productService.deleteAllProducts();
    }

    @Test
    void userCanBeCreatedAndIdIsGenerated(){
        createUserResponse = userService.createUser(createUserRequest);
        assertNotNull(createUserResponse.getId());
    }
    @Test
    void userCanBeFoundByIdTest(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(createUserResponse.getId());
        assertTrue(foundUser.isPresent());
    }
    @Test
    void testThatAddressIsCreatedWhenAUserIsCreated(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(createUserResponse.getId());
        assertNotNull(foundUser.get().getAddress().getId());
    }
    @Test
    void testThatCartIsCreatedWhenAUserIsCreated(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(createUserResponse.getId());
        assertNotNull(foundUser.get().getCart().getId());
    }
    @Test
    void userCanBeFoundByUsernameTest(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUsername("kyaa");
        assertTrue(foundUser.isPresent());
    }
    @Test
    void listOfUserSizeIsEqualsNumberOfUserSavedToUserDb(){
        createUserResponse = userService.createUser(createUserRequest);
        var users = userService.getAllUsers();
        assertEquals(1,users.size());
    }
    @Test
    void userDbSizeIsZeroIfTheOnlyUserInItIsDeleted(){
        createUserResponse = userService.createUser(createUserRequest);
        var usersBeforeDeleting = userService.getAllUsers();
        assertEquals(1, usersBeforeDeleting.size());
        userService.deleteUserById(createUserResponse.getId());
        var usersAfterDeleting = userService.getAllUsers();
        assertEquals(0, usersAfterDeleting.size());
    }
    @Test
    void aUserWithUserRoleCanBeUpdateToAdmin(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUserBeforeUpdate = userService.getUserByUsername("kyaa");
        assertEquals(USER, foundUserBeforeUpdate.get().getRole());
        userService.updateUserRole("kyaa", ADMIN);
        Optional<User> foundUserAfterUpdate = userService.getUserByUsername("kyaa");
        assertEquals(ADMIN, foundUserAfterUpdate.get().getRole());
    }
    @Test
    void userDetailsCanBeUpdated(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUserBeforeUpdate = userService.getUserByUsername("kyaa");
        assertNull(foundUserBeforeUpdate.get().getFirstName());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("kyaa");
        updateUserRequest.setFirstName("kabir");
        userService.updateUserInfo(updateUserRequest);
        Optional<User> foundUserAfterUpdate = userService.getUserByUsername("kyaa");
        assertEquals("kabir", foundUserAfterUpdate.get().getFirstName());
    }
    @Test
    void userCanLoginIfLoginDetailsAreFoundInDbTest(){
        createUserResponse = userService.createUser(createUserRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("kyaa");
        loginRequest.setPassword("pass1234");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertEquals("login successful", loginResponse.getMessage());
    }
    @Test
    void testThatNumberOfProductsInCartIsEqualToTheNumberOfProductsAddedToCart(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUsername("kyaa");
        var numberOfProductsInUserCartBeforeAddingAProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartBeforeAddingAProduct);

        productService.createProduct(createProductRequest);

        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();

        addProductToCartRequest.setUsername("kyaa");
        addProductToCartRequest.setProductName("milk");
        addProductToCartRequest.setQuantity(2);
        userService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUsername("kyaa");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart());
        var numberOfProductsInUserCartAfterAddingAProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1, numberOfProductsInUserCartAfterAddingAProduct);
        assertEquals(1, cartProductService.getAllCartProducts().size());
    }

    @Test
    void testThatNumberOfProductsInAUserCartDecreasesByOneIfAProductIsDeleted(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUsername("kyaa");
        var numberOfProductsInUserCartBeforeAddingAProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartBeforeAddingAProduct);
        productService.createProduct(createProductRequest);
        productService.getProductByName("milk");

        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();

        addProductToCartRequest.setUsername("kyaa");
        addProductToCartRequest.setProductName("milk");
        addProductToCartRequest.setQuantity(2);
        AddProductToCartResponse addProductToCartResponse = userService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUsername("kyaa");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart());
        var numberOfProductsInUserCartAfterAddingAProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1, numberOfProductsInUserCartAfterAddingAProduct);

//        DeleteCartProductFromCartRequest deleteCartProductFromCartRequest = new DeleteCartProductFromCartRequest();
//        deleteCartProductFromCartRequest.setUsername("kyaa");
//        deleteCartProductFromCartRequest.setCartProductId(addProductToCartResponse.getId());

        userService.deleteProductFromCart(addProductToCartResponse.getId());

        Optional<User> foundUserAfterDeletingProductToCart = userService.getUserByUsername("kyaa");
        var numberOfProductsInUserCartAfterDeletingAProduct = foundUserAfterDeletingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartAfterDeletingAProduct);
        assertEquals(0, cartProductService.getAllCartProducts().size());
    }
    @Test
    void testThatUserCanOrderProducts(){
        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);
        OrderProductRequest orderProductRequest = new OrderProductRequest();
        orderProductRequest.setUserId(createUserResponse.getId());
        orderProductRequest.setProductId(createProductResponse.getId());
        orderProductRequest.setQuantity(2);
        orderProductRequest.setPrice(BigDecimal.valueOf(100));
        OrderProductResponse orderProductResponse = userService.orderProduct(orderProductRequest);
        assertEquals("Product ordered successfully", orderProductResponse.getMessage());
    }
    @Test
    void testThatWhenUserOrdersAProduct_OrderHistoryIsGeneratedForThatUser(){
        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);
        OrderProductRequest orderProductRequest = new OrderProductRequest();
        orderProductRequest.setUserId(createUserResponse.getId());
        orderProductRequest.setProductId(createProductResponse.getId());
        orderProductRequest.setQuantity(2);
        orderProductRequest.setPrice(BigDecimal.valueOf(100));
        assertEquals(0, orderHistoryService.orderHistories().size());
        userService.orderProduct(orderProductRequest);
        assertNotNull(orderHistoryService.orderHistories().get(0).getId());
        assertEquals(1, orderHistoryService.orderHistories().size());
        assertEquals(createUserResponse.getId(), orderHistoryService.orderHistories().get(0).getUser().getId());
        System.out.println(orderHistoryService.orderHistories());
    }
    @Test
    void testThatProductQuantityIsUpdatedInProductDbWhenAUserSuccessfullyOrdersAProduct(){
        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);

        Optional<Product>productBeforeOrder = productService.getProductByName("milk");
        assertEquals(3, productBeforeOrder.get().getQuantity());
        OrderProductRequest orderProductRequest = new OrderProductRequest();
        orderProductRequest.setUserId(createUserResponse.getId());
        orderProductRequest.setProductId(createProductResponse.getId());
        orderProductRequest.setQuantity(2);
        orderProductRequest.setPrice(BigDecimal.valueOf(100));
        userService.orderProduct(orderProductRequest);
        assertEquals(1, orderHistoryService.orderHistories().size());

        Optional<Product> productAfterOrder = productService.getProductByName("milk");
        assertEquals(1, productAfterOrder.get().getQuantity());

    }

}