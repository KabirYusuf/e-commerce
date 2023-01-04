package com.kyaa.ecommerce.dto.requests;

import com.kyaa.ecommerce.data.models.Address;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Address address;
}
