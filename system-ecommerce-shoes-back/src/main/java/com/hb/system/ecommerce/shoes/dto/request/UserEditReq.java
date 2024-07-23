package com.hb.system.ecommerce.shoes.dto.request;

import lombok.Data;

@Data
public class UserEditReq {
    String email;
    String username;
    String password;
    int role;
}
