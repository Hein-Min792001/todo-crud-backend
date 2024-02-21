package com.example.todocrudbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
