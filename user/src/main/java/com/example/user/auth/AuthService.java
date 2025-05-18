package com.example.user.auth;

import com.example.common.jwt.JWTUtil;
import com.example.common.models.SessionManager;
import com.example.common.models.UserDTO;
import com.example.common.models.UserSession;
import com.example.user.models.*;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final UserFactory userFactory;
    private final SessionManager sessionManager;

    @Transactional
    public String signUp(SignupRequest signupRequest) {
        if (userService.emailExists(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        BaseUser baseUser = userFactory.createUser(
                signupRequest.getType(),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.getPhone()
        );

        userService.signUp(baseUser);
        return "User created successfully";
    }

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        BaseUser principle = (BaseUser) authentication.getPrincipal();
        UserDTO userDTO = userDTOMapper.apply(principle);
        String token = jwtUtil.issueToken(
                userDTO.getId().toString(),
                Map.of(
                        "id", userDTO.getId(),
                        "type", userDTO.getType(),
                        "name", userDTO.getName(),
                        "email", userDTO.getEmail(),
                        "status", userDTO.getStatus()
                )
        );
        sessionManager.getInstance().createSession(
                userDTO.getEmail(),
                new UserSession(
                        token,
                        userDTO,
                        Instant.now().plusSeconds(3600)
                )
        );
        return new AuthResponse(token, userDTO);
    }

    public boolean isTokenValid(String token) {
        String email = jwtUtil.getSubject(token);
        return jwtUtil.isTokenValid(token, email) &&
                sessionManager.getInstance().validateSession(email, token);
    }

    public void logout(String token) {
        String email = jwtUtil.getSubject(token);
        sessionManager.getInstance().removeSession(email);
    }
}
