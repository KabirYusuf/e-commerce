package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.CartProduct;
import com.kyaa.ecommerce.dto.requests.UpdateCartProductRequest;
import com.kyaa.ecommerce.enums.Category;
import com.kyaa.ecommerce.services.CartProductService;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartProductServiceImplTest {
    @Autowired
    private CartProductService cartProductService;
    private CartProduct cartProductOne;
    private CartProduct cartProductTwo;


    @BeforeEach
    void setUp() {
        cartProductOne = new CartProduct();
        cartProductOne.setName("milk");
        cartProductOne.setQuantity(3);
        cartProductOne.setUnitPrice(BigDecimal.valueOf(10));
        cartProductOne.setTotalPrice(BigDecimal.valueOf(30));
        cartProductOne.setCategory(Category.BEVERAGES);


        cartProductTwo = new CartProduct();
        cartProductTwo.setName("milo");
        cartProductTwo.setQuantity(5);
        cartProductTwo.setTotalPrice(BigDecimal.valueOf(34));
        cartProductTwo.setCategory(Category.BEVERAGES);
    }

    @AfterEach
    void tearDown() {
        cartProductService.deleteAllCartProducts();
    }
    @Test
    void testThatCartProductCanBeAddedToCartProductDb(){
       assertNull(cartProductOne.getId());
       CartProduct savedCartProduct = cartProductService.createCartProduct(cartProductOne);
       assertNotNull(savedCartProduct.getId());
    }
    @Test
    void testThatIfOneCartProductsAreCreatedSizeOfCartProductDbIsOne(){
        cartProductService.createCartProduct(cartProductOne);
        int cartProductDbSize = cartProductService.getAllCartProducts().size();
        assertEquals(1, cartProductDbSize);
    }
    @Test
    void cartProductCanBeDeletedByIdOfProduct(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProductOne);
        cartProductService.createCartProduct(cartProductTwo);
        int cartProductDbSizeBeforeDeletingAProduct = cartProductService.getAllCartProducts().size();
        assertEquals(2, cartProductDbSizeBeforeDeletingAProduct);
        cartProductService.deleteCartProductById(savedCartProduct.getId());
        int cartProductDbSizeAfterDeletingAProduct = cartProductService.getAllCartProducts().size();
        assertEquals(1, cartProductDbSizeAfterDeletingAProduct);
    }
    @Test
    void testThatCartProductCanBeFoundUsingCartProductId(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProductOne);
        CartProduct foundCartProduct = cartProductService.findCartProductById(savedCartProduct.getId());
        assertEquals(foundCartProduct.getName(), savedCartProduct.getName());
    }

    //  Todo update cartProduct
    @Test
    void testThatCartProductQuantityCanBeUpdated(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProductOne);
        Integer quantityBeforeUpdate = cartProductService.findCartProductById(savedCartProduct.getId()).getQuantity();
        assertEquals(3, quantityBeforeUpdate);

        UpdateCartProductRequest updateCartProductRequest = new UpdateCartProductRequest();
        updateCartProductRequest.setCartProductId(savedCartProduct.getId());
        updateCartProductRequest.setQuantity(10);
        cartProductService.updateCartProduct(updateCartProductRequest);

        CartProduct foundCartProduct= cartProductService.findCartProductById(savedCartProduct.getId());
        assertEquals(10, foundCartProduct.getQuantity());
    }

    @Test
    void testWhenCartProductQuantityIsUpdated_TotalPriceOfQuantityIsAlsoUpdated(){
        CartProduct savedCartProduct = cartProductService.createCartProduct(cartProductOne);
        Integer quantityBeforeUpdate = cartProductService.findCartProductById(savedCartProduct.getId()).getQuantity();
        assertEquals(3, quantityBeforeUpdate);
        assertEquals(BigDecimal.valueOf(30), savedCartProduct.getTotalPrice());

        UpdateCartProductRequest updateCartProductRequest = new UpdateCartProductRequest();
        updateCartProductRequest.setCartProductId(savedCartProduct.getId());
        updateCartProductRequest.setQuantity(10);
        cartProductService.updateCartProduct(updateCartProductRequest);

        CartProduct foundCartProduct= cartProductService.findCartProductById(savedCartProduct.getId());
        assertEquals(10, foundCartProduct.getQuantity());
        assertEquals(new BigDecimal("100.00"), foundCartProduct.getTotalPrice());
    }
}