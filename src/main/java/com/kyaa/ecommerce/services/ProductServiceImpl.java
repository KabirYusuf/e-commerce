package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.dto.requests.CreateProductRequest;
import com.kyaa.ecommerce.data.dto.responses.CreateProductResponse;
import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.repositories.ProductRepository;
import com.kyaa.ecommerce.exceptions.ProductException;
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
    private ModelMapper modelMapper;
    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        if (getProductByName(createProductRequest.getName().substring(0,1).toUpperCase()).isPresent())throw new ProductException("Product already exist");
        Product product = Product.builder().
                name(createProductRequest.getName().substring(0,1).toUpperCase()).
                quantity(createProductRequest.getQuantity()).
                price(createProductRequest.getPrice()).
                category(createProductRequest.getCategory()).
                build();
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, CreateProductResponse.class);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
