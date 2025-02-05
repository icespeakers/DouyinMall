package org.example.userservice.entity;

import lombok.Data;

@Data
public class User {
    /** 自增ID */
    private Long id;
    private String email;
    private String password;
}
