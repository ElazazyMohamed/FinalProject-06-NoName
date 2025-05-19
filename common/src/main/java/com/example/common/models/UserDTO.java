package com.example.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Type type;
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Status status;

}
