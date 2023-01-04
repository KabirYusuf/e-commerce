package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.repositories.ProductRepository;
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
    void testThatListOfProductIsEqualToTheNumberOfProductAdded(){
        var productDbSizeBeforeAddingProduct = productService.getAllProducts();
        assertEquals(0, productDbSizeBeforeAddingProduct.size());
        productService.createProduct(createProductRequest);
        var productDbSizeAfterAddingProduct = productService.getAllProducts();
        assertEquals(1, productDbSizeAfterAddingProduct.size());

    }

}