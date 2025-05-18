package com.example.user.models;

import com.example.common.models.Type;
import com.example.common.models.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<BaseUser, UserDTO> {

    @Override
    public UserDTO apply(BaseUser baseUser) {
        Type userType;

        switch (baseUser.getClass().getSimpleName().toUpperCase()) {
            case "USER":
                userType = Type.USER;
                break;
            case "ADMIN":
                userType = Type.ADMIN;
                break;
            case "GUEST":
                userType = Type.GUEST;
                break;
            default:
                throw new IllegalArgumentException("Unknown user type: " + baseUser.getClass().getSimpleName().toUpperCase());
        }
        return new UserDTO(
                userType,
                baseUser.getId(),
                baseUser.getName(),
                baseUser.getEmail(),
                baseUser.getPhone(),
                baseUser.getStatus()
        );
    }
}
