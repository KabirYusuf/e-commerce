package com.kyaa.ecommerce.dto.requests;

import lombok.Data;

@Data
public class CreateAddressRequest {
    private String streetNumber;
    private String streetName;
    private String town;
    private String city;

}
