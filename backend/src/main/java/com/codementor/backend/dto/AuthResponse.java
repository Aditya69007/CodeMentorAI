package com.codementor.backend.dto;

import com.codementor.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String message;

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String profilePicture;
}