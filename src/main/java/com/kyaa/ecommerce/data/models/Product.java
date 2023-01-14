package com.kyaa.ecommerce.data.models;

import com.kyaa.ecommerce.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "Products")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "unit-price", nullable = false)
    private BigDecimal unitPrice;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime = Instant.now();
    @UpdateTimestamp
    @Column(name = "updated_time")
    private Instant updatedTime = Instant.now();

}
