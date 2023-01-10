package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.dto.requests.CreateAddressRequest;
import com.kyaa.ecommerce.dto.responses.CreateAddressResponse;
import com.kyaa.ecommerce.data.repositories.AddressRepository;
import com.kyaa.ecommerce.services.AddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    private CreateAddressRequest createAddressRequest;

    @BeforeEach
    void setUp() {
        createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setStreetNumber("2");
        createAddressRequest.setStreetName("Ibadan Street");
        createAddressRequest.setTown("Bwari");
        createAddressRequest.setCity("Abuja");
    }

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }
    @Test
    void addressCanBeCreatedTest(){
        CreateAddressResponse createAddressResponse = addressService.createAddress(createAddressRequest);
        assertNotNull(createAddressResponse.getId());
    }
}