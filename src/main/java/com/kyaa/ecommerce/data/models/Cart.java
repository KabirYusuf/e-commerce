package com.kyaa.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private User user;
    @ManyToMany
    private List<Product>products = new ArrayList<>();
}
