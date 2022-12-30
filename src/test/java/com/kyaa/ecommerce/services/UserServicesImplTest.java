package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.dto.requests.*;
import com.kyaa.ecommerce.data.dto.responses.CreateUserResponse;
import com.kyaa.ecommerce.data.dto.responses.LoginResponse;
import com.kyaa.ecommerce.data.models.Cart;
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
private UserRepository userRepository;
@Autowired
private ProductService productService;

private CreateProductRequest createProductRequest;
private CreateUserRequest createUserRequest;
private CreateUserResponse createUserResponse;
    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("kabir@gmail.com");
        createUserRequest.setUsername("kyaa");
        createUserRequest.setPassword("pass1234");

        createProductRequest = new CreateProductRequest();
        createProductRequest.setName("milk");
        createProductRequest.setQuantity(3);
        createProductRequest.setPrice(BigDecimal.valueOf(50));
        createProductRequest.setCategory(BEVERAGES);
    }
    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    void userCanBeCreatedAndIdIsGenerated(){
        createUserResponse = userService.createUser(createUserRequest);
        assertEquals(1L,createUserResponse.getId());
    }
    @Test
    void userCanBeFoundByIdTest(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
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
        userService.deleteUserById(1L);
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
//    @Test
//    void userCanAddProductToProductDb(){
//        createUserResponse = userService.createUser(createUserRequest);
//        userService.addProduct(createProductRequest, "kyaa");
//        assertEquals(1, productService.getAllProducts().size());
//    }
    @Test
    void testThatNumberOfProductsInCartIsEqualToTheNumberOfProductsAddedToCart(){
        createUserResponse = userService.createUser(createUserRequest);
        Optional<User> foundUser = userService.getUserByUsername("kyaa");
        System.out.println(foundUser.get());
        System.out.println(foundUser.get().getCart());
        var numberOfProductsInUserCartBeforeAddingAProduct = foundUser.get().getCart().getProducts().size();
        assertEquals(0, numberOfProductsInUserCartBeforeAddingAProduct);
        productService.createProduct(createProductRequest);
        Optional<Product> foundProduct = productService.getProductByName("milk");
        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
        addProductToCartRequest.setProduct(foundProduct.get());
        userService.addProductToCart(addProductToCartRequest);
        var numberOfProductsInUserCartAfterAddingAProduct = foundUser.get().getCart().getProducts().size();
        assertEquals(1, numberOfProductsInUserCartAfterAddingAProduct);
    }

}