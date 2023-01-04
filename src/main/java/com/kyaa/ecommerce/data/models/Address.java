package com.kyaa.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "town")
    private String town;
    @Column(name = "city")
    private String city;
}
