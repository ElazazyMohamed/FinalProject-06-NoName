package com.example.user.auth;

import com.example.common.models.UserDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {

    private String token;
    private UserDTO userDTO;

    public String getToken() {
        return token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
