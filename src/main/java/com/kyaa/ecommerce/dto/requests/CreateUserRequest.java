package com.kyaa.ecommerce.dto.requests;

import com.kyaa.ecommerce.data.models.Address;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String username;
    private String password;
    private Address address;
//    private String streetNumber;
//    private String streetName;
//    private String town;
//    private String city;
}
