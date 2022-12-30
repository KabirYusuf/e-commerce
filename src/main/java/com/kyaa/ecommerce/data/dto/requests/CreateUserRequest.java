package com.kyaa.ecommerce.data.dto.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String username;
    private String password;
}
