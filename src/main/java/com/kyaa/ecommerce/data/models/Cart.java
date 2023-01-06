package com.kyaa.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "Cart-Product_id")
    private List<CartProduct>cartProducts;
}
