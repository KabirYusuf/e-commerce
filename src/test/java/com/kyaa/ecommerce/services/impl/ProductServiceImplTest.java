package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.Address;
import com.kyaa.ecommerce.data.models.CartProduct;
import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.dto.requests.CreateUserRequest;
import com.kyaa.ecommerce.dto.requests.UpdateProductRequest;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.repositories.ProductRepository;
import com.kyaa.ecommerce.services.CartProductService;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static com.kyaa.ecommerce.enums.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartProductService cartProductService;

    private CreateProductRequest createProductRequest;
    @BeforeEach
    void setUp(){
        createProductRequest = new CreateProductRequest();
        createProductRequest.setName("Milk");
        createProductRequest.setCategory(BEVERAGES);
        createProductRequest.setPrice(BigDecimal.valueOf(40));
        createProductRequest.setQuantity(3);

    }
    @AfterEach
    void afterEach(){
        productRepository.deleteAll();
        userService.deleteAllUsers();
        cartProductService.deleteAllCartProducts();
    }

    @Test
    void productCanBeCreated(){
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);
        assertNotNull(createProductResponse.getId());
    }
    @Test
    void productCanBeSearchedForUsingProductName(){
        productService.createProduct(createProductRequest);
        Optional<Product> foundProduct = productService.getProductByName("Milk");
        assertNotNull(foundProduct);
    }
    @Test
    void productCanBeSearchedForById(){
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);
        Product foundProduct = productService.getProductById(createProductResponse.getId());
        assertNotNull(foundProduct);
    }
    @Test
    void testThatListOfProductIsEqualToTheNumberOfProductAdded(){
        var productDbSizeBeforeAddingProduct = productService.getAllProducts();
        assertEquals(0, productDbSizeBeforeAddingProduct.size());
        productService.createProduct(createProductRequest);
        var productDbSizeAfterAddingProduct = productService.getAllProducts();
        assertEquals(1, productDbSizeAfterAddingProduct.size());

    }
    @Test
    void testThatProductCanBeUpdated(){
        CreateProductResponse createProductResponse = productService.createProduct(createProductRequest);
        Product foundProductBeforeUpdate = productService.getProductById(createProductResponse.getId());
        assertEquals(3, foundProductBeforeUpdate.getQuantity());
        assertEquals(new BigDecimal("40.00"), foundProductBeforeUpdate.getPrice());
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductName("MILK");
        updateProductRequest.setQuantity(5);
        updateProductRequest.setPrice(BigDecimal.valueOf(10));
        productService.updateProduct(updateProductRequest);
        Product foundProductAfterUpdate = productService.getProductById(createProductResponse.getId());
        assertEquals(5, foundProductAfterUpdate.getQuantity());
        assertEquals(new BigDecimal("10.00"), foundProductAfterUpdate.getPrice());

    }
    @Test
    void testThatWhenAProductIsUpdated_ProductsWithSameNameInUsersCartGetUpdated(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        Address address = new Address();
        createUserRequest.setUsername("kyaa");
        createUserRequest.setPassword("1234");
        createUserRequest.setEmail("k@gmail.com");
        createUserRequest.setAddress(address);
        userService.createUser(createUserRequest);
        productService.createProduct(createProductRequest);
        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
        addProductToCartRequest.setUsername("kyaa");
        addProductToCartRequest.setProductName("milk");
        addProductToCartRequest.setQuantity(2);
        userService.addProductToCart(addProductToCartRequest);
        var cartProductsBeforeUpdate = cartProductService.getCartProductsByName("milk");
        assertEquals(new BigDecimal("40.00"), cartProductsBeforeUpdate.get(0).getUnitPrice());
        assertEquals(new BigDecimal("80.00"), cartProductsBeforeUpdate.get(0).getTotalPrice());
        assertEquals(1, cartProductsBeforeUpdate.size());
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductName("milk");
        updateProductRequest.setPrice(BigDecimal.valueOf(50));
        productService.updateProduct(updateProductRequest);
        var cartProductsAfterUpdate = cartProductService.getCartProductsByName("milk");
        assertEquals(new BigDecimal("50.00"), cartProductsAfterUpdate.get(0).getUnitPrice());
        assertEquals(new BigDecimal("100.00"), cartProductsAfterUpdate.get(0).getTotalPrice());
        assertEquals(1, cartProductsAfterUpdate.size());

    }

}