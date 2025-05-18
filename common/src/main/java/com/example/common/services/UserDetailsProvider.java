package com.example.common.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsProvider {
    UserDetails getUserDetails(String email);
}