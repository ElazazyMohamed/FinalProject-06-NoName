package com.example.user.models;

import com.example.user.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

// Factory class to create different types of users
@Component
@AllArgsConstructor
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public BaseUser createUser(
            Type type,
            String name,
            String email,
            String password,
            String phone
    ) {
        BaseUser user;
        switch (type.name()) {
            case "ADMIN":
                user = new Admin(name, email, passwordEncoder.bCryptPasswordEncoder().encode(password), phone, Status.ACTIVE);
                break;
            case "GUEST":
                user = new Guest(name, email, passwordEncoder.bCryptPasswordEncoder().encode(password), phone, Status.ACTIVE);
                break;
            case "USER":
                user = new User(name, email, passwordEncoder.bCryptPasswordEncoder().encode(password), phone, Status.ACTIVE);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
        return user;
    }
}