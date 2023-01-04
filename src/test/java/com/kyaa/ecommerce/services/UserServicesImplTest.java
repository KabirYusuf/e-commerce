package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.Address;
import com.kyaa.ecommerce.dto.requests.*;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.dto.responses.LoginResponse;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.data.repositories.UserRepository;
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
private ProductService productService;

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
        Optional<Product> foundProduct = productService.getProductByName("milk");
        System.out.println(productService.getAllProducts().size());
        System.out.println(productService.getAllProducts().get(0));
//        System.out.println(foundProduct.get());
        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
//        addProductToCartRequest.setProduct(foundProduct.get());
        addProductToCartRequest.setUsername("kyaa");
        addProductToCartRequest.setProductName("milk");
        addProductToCartRequest.setQuantity(2);
        userService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUsername("kyaa");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart());
        var numberOfProductsInUserCartAfterAddingAProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1, numberOfProductsInUserCartAfterAddingAProduct);
    }

    @Test
    void testThatNumberOfProductsInAUserCartDecreasesByOneIfAProductIsDeleted(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUsername("kyaa");
        var numberOfProductsInUserCartBeforeAddingAProduct = foundUser.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartBeforeAddingAProduct);
        productService.createProduct(createProductRequest);
        Optional<Product> foundProduct = productService.getProductByName("milk");
        System.out.println(productService.getAllProducts().size());
        System.out.println(productService.getAllProducts().get(0));
//        System.out.println(foundProduct.get());
        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
//        addProductToCartRequest.setProduct(foundProduct.get());
        addProductToCartRequest.setUsername("kyaa");
        addProductToCartRequest.setProductName("milk");
        addProductToCartRequest.setQuantity(2);
        AddProductToCartResponse addProductToCartResponse = userService.addProductToCart(addProductToCartRequest);

        Optional<User> foundUserAfterAddingProductToCart = userService.getUserByUsername("kyaa");
        System.out.println(foundUserAfterAddingProductToCart.get().getCart());
        var numberOfProductsInUserCartAfterAddingAProduct = foundUserAfterAddingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(1, numberOfProductsInUserCartAfterAddingAProduct);

        userService.deleteProductFromCart(createUserResponse.getUsername(), addProductToCartResponse.getName());
        Optional<User> foundUserAfterDeletingProductToCart = userService.getUserByUsername("kyaa");
        var numberOfProductsInUserCartAfterDeletingAProduct = foundUserAfterDeletingProductToCart.get().getCart().getCartProducts().size();
        assertEquals(0, numberOfProductsInUserCartAfterDeletingAProduct);
    }

}