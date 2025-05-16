package com.example.user.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@DiscriminatorValue("USER")
@NoArgsConstructor
public class User extends BaseUser {

    public User(String name, String email, String password, String phone, Status status) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setStatus(status);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}