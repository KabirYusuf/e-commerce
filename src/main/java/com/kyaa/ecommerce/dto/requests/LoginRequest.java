package com.kyaa.ecommerce.dto.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
