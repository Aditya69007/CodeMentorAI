package com.codementor.backend.dto;

import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private AuthProvider provider;

    private String profilePicture;

}