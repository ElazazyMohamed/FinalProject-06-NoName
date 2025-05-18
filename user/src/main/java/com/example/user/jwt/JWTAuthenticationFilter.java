package com.example.user.jwt;

import com.example.common.jwt.BaseJWTAuthenticationFilter;
import com.example.common.jwt.JWTUtil;
import com.example.common.models.UserDTO;
import com.example.common.services.UserDetailsProvider;
import com.example.user.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends BaseJWTAuthenticationFilter {

    public JWTAuthenticationFilter(JWTUtil jwtUtil, UserService userService) {
        super(jwtUtil, new UserDetailsProvider() {
            @Override
            public UserDetails getUserDetails(String email) {
                UserDTO userDTO = userService.getUserByEmail(email);
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userDTO.getType().name()));
                    }

                    @Override
                    public String getPassword() {
                        return "";
                    }

                    @Override
                    public String getUsername() {
                        return userDTO.getEmail();
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return userDTO.getStatus() == com.example.common.models.Status.ACTIVE;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return userDTO.getStatus() == com.example.common.models.Status.ACTIVE;
                    }
                };
            }
        });
    }
}
