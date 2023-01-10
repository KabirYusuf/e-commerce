package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.data.models.CartProduct;
import com.kyaa.ecommerce.data.repositories.CartProductRepository;
import com.kyaa.ecommerce.dto.requests.UpdateCartProductRequest;
import com.kyaa.ecommerce.services.CartProductService;
import com.kyaa.ecommerce.services.ProductService;
import com.kyaa.ecommerce.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public CartProduct createCartProduct(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    @Override
    @Transactional
    public void deleteCartProductById(Long id) {
        cartProductRepository.deleteCartProductById(id);
    }

    @Override
    public List<CartProduct> getAllCartProducts() {
        return cartProductRepository.findAll();
    }

    @Override
    public String deleteAllCartProducts() {
        cartProductRepository.deleteAll();
        return "Cart products deleted successfully";
    }

    @Override
    public CartProduct updateCartProduct(UpdateCartProductRequest updateCartProductRequest) {
        CartProduct foundProduct = cartProductRepository.findCartProductById(updateCartProductRequest.getCartProductId());
        foundProduct.setQuantity(updateCartProductRequest.getQuantity());
        foundProduct.setTotalPrice(foundProduct.getUnitPrice().multiply(new BigDecimal(updateCartProductRequest.getQuantity())));
        return cartProductRepository.save(foundProduct);
    }

    @Override
    public CartProduct findCartProductById(Long cartProductId) {
        return cartProductRepository.findCartProductById(cartProductId);
    }

    @Override
    public List<CartProduct> getCartProductsByName(String name) {
        return cartProductRepository.getCartProductsByName(name.toLowerCase());
    }

    @Override
    public void updateListOfCartProducts(String name, BigDecimal price) {
        List<CartProduct> cartProducts = getCartProductsByName(name.toLowerCase());
        cartProducts.forEach(cartProduct->{
            cartProduct.setUnitPrice(price);
            cartProduct.setTotalPrice(price.multiply(new BigDecimal(cartProduct.getQuantity())));
            cartProductRepository.save(cartProduct);
        });

    }
}
