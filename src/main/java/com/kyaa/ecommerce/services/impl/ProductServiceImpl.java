package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.dto.requests.UpdateCartProductRequest;
import com.kyaa.ecommerce.dto.requests.UpdateProductRequest;
import com.kyaa.ecommerce.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.repositories.ProductRepository;
import com.kyaa.ecommerce.exceptions.ProductException;
import com.kyaa.ecommerce.services.CartProductService;
import com.kyaa.ecommerce.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        if (productRepository.findProductByName(createProductRequest.getName().toLowerCase()).isPresent())throw new ProductException("Product already exist");
        Product product = Product.builder().
                name(createProductRequest.getName().toLowerCase()).
                quantity(createProductRequest.getQuantity()).
                unitPrice(createProductRequest.getPrice()).
                category(createProductRequest.getCategory()).
                build();
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, CreateProductResponse.class);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        if (productRepository.findProductByName(name.toLowerCase()).isEmpty())throw new ProductException("Product not found");
        return productRepository.findProductByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    public String updateProduct(UpdateProductRequest updateProductRequest) {
        Optional<Product> foundProduct = getProductByName(updateProductRequest.getProductName().toLowerCase());
        if (updateProductRequest.getPrice() != null)foundProduct.get().setUnitPrice(updateProductRequest.getPrice());
        if (updateProductRequest.getCategory() != null) foundProduct.get().setCategory(updateProductRequest.getCategory());
        if (updateProductRequest.getQuantity() != null)foundProduct.get().setQuantity(updateProductRequest.getQuantity());
        productRepository.save(foundProduct.get());
//        UpdateCartProductRequest updateCartProductRequest = new UpdateCartProductRequest();
//        updateCartProductRequest.s
        cartProductService.updateListOfCartProducts(updateProductRequest.getProductName(), updateProductRequest.getPrice());
        return "Product updated successfully";
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findProductById(id).orElseThrow(() -> new ProductException("Product doesnt not exist"));
    }
}
