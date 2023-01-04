package com.kyaa.ecommerce.services;

import com.kyaa.ecommerce.data.models.Address;
import com.kyaa.ecommerce.dto.requests.CreateAddressRequest;
import com.kyaa.ecommerce.dto.responses.CreateAddressResponse;

public interface AddressService {
    CreateAddressResponse createAddress(CreateAddressRequest createAddressRequest);
    Address getAddressById(Long id);
    Address updateAddress(Address address, Long id);
}
