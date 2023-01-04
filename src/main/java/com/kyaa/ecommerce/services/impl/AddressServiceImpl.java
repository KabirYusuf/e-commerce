package com.kyaa.ecommerce.services.impl;

import com.kyaa.ecommerce.dto.requests.CreateAddressRequest;
import com.kyaa.ecommerce.dto.responses.CreateAddressResponse;
import com.kyaa.ecommerce.data.models.Address;
import com.kyaa.ecommerce.data.repositories.AddressRepository;
import com.kyaa.ecommerce.services.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateAddressResponse createAddress(CreateAddressRequest createAddressRequest) {
        Address address = Address.builder().
                streetNumber(createAddressRequest.getStreetNumber()).
                streetName(createAddressRequest.getStreetName()).
                city(createAddressRequest.getCity()).
                town(createAddressRequest.getTown()).
                build();
        Address savedAddress = addressRepository.save(address);
        CreateAddressResponse createAddressResponse = new CreateAddressResponse();
        createAddressResponse.setId(savedAddress.getId());
        createAddressResponse.setMessage("Address added successfully");
        return createAddressResponse;
    }

    @Override
    public Address getAddressById(Long id) {
        Address foundAddress = addressRepository.findAddressById(id);
        return foundAddress;
    }

    @Override
    public Address updateAddress(Address address, Long id) {
        Address foundAddress = getAddressById(id);
        foundAddress.setStreetNumber(address.getStreetNumber());
        foundAddress.setTown(address.getTown());
        foundAddress.setCity(address.getCity());
        foundAddress.setStreetName(address.getStreetName());
        Address updatedAddress = addressRepository.save(foundAddress);
        return updatedAddress;
    }
}
