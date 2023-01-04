package com.kyaa.ecommerce.data.repositories;

import com.kyaa.ecommerce.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressById(Long id);
}
