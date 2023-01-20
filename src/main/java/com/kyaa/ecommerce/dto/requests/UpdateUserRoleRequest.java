package com.kyaa.ecommerce.dto.requests;

import com.kyaa.ecommerce.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRoleRequest {
    private String username;
    private Role role;

}
