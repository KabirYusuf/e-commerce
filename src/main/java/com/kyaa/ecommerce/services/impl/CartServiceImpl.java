package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.CartProduct;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.dto.requests.AddProductToCartRequest;
import com.kyaa.ecommerce.dto.responses.AddProductToCartResponse;
import com.kyaa.ecommerce.services.CartService;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public AddProductToCartResponse addProductToCart(AddProductToCartRequest addProductToCartRequest) {
//        Optional<User> foundUser = userService.getUserByUsername(addProductToCartRequest.getUsername());
//        Optional<Product> foundProduct = productService.getProductByName(addProductToCartRequest.getProductName().toLowerCase());
////        CartProduct cartProduct = CartProduct.builder().
////                quantity(addProductToCartRequest.getQuantity()).
////                name(foundProduct.get().getName()).
////                category(foundProduct.get().getCategory()).
////                price(foundProduct.get().getPrice().multiply(new BigDecimal(addProductToCartRequest.getQuantity()))).
////                build();
//        CartProduct cartProduct = new CartProduct();
//        cartProduct.setQuantity(addProductToCartRequest.getQuantity());
//        cartProduct.setCategory(foundProduct.get().getCategory());
//        cartProduct.setName(foundProduct.get().getName());
//        cartProduct.setPrice(foundProduct.get().getPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
//        foundUser.get().getCart().getCartProducts().add(cartProduct);
//        AddProductToCartResponse addProductToCartResponse = new AddProductToCartResponse();
//        return addProductToCartResponse;


        Optional<User> foundUser = userService.getUserByUsername(addProductToCartRequest.getUsername());
        Optional<Product> foundProduct = productService.getProductByName(addProductToCartRequest.getProductName().toLowerCase());
        //Todo: create a model for cart product
//        Product product = new Product();
//        product.setName(foundProduct.get().getName());
//        product.setCategory(foundProduct.get().getCategory());
//        product.setQuantity(addProductToCartRequest.getProduct().getQuantity());
//        product.setPrice(foundProduct.get().getPrice().multiply(new BigDecimal(product.getQuantity())));
//        foundUser.get().getCart().getProducts().add(product);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setName(foundProduct.get().getName());
        cartProduct.setCategory(foundProduct.get().getCategory());
        cartProduct.setQuantity(addProductToCartRequest.getQuantity());
        cartProduct.setUnitPrice(foundProduct.get().getPrice());
        cartProduct.setTotalPrice(foundProduct.get().getPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
        foundUser.get().getCart().getCartProducts().add(cartProduct);
//        AddProductToCartResponse addProductToCartResponse = new AddProductToCartResponse();
//        addProductToCartResponse.setName(addProductToCartResponse.getName());
//        return addProductToCartResponse;
        return null;

    }

    @Override
    public String deleteProductFromCart(Long cartProductId) {
        return null;
    }
}
