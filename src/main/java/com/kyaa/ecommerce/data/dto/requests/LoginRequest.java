package com.kyaa.ecommerce.data.dto.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
