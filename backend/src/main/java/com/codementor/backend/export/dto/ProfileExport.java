package com.codementor.backend.export.dto;

import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileExport {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private AuthProvider provider;

    private String profilePicture;

}