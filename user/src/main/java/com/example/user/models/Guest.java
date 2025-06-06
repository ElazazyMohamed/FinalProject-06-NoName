package com.example.user.models;

import com.example.common.models.Status;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Entity
@DiscriminatorValue("GUEST")
@NoArgsConstructor
public class Guest extends BaseUser {

    public Guest(String name, String email, String password, String phone, Status status) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setStatus(status);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST"));
    }
}
