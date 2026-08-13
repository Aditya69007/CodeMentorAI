package com.codementor.backend.service.impl;

import com.codementor.backend.dto.AdminUserSummaryResponse;
import com.codementor.backend.dto.CreateAdminRequest;
import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.SuperAdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String SUPER_ADMIN_EMAIL =
            "admin@codementor.local";

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserSummaryResponse> getAllAdmins() {

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .map(this::mapToSummary)
                .toList();
    }

    @Override
    @Transactional
    public AdminUserSummaryResponse createAdmin(
            CreateAdminRequest request
    ) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "An account with this email already exists."
            );
        }

        if (request.getUsername() != null
                && !request.getUsername().isBlank()
                && userRepository.findByUsername(request.getUsername()).isPresent()) {

            throw new IllegalArgumentException(
                    "This username is already taken."
            );
        }

        User admin = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .role(Role.ADMIN)
                .provider(AuthProvider.LOCAL)
                .enabled(true)
                .build();

        User savedAdmin = userRepository.save(admin);

        return mapToSummary(savedAdmin);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found."
                        )
                );

        if (user.getRole() != Role.USER) {
            throw new IllegalArgumentException(
                    "This operation is only for normal users."
            );
        }

        userRepository.delete(user);
    }

    @Override
    public void deleteAdmin(Long adminId, String password) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "Super Admin authentication required"
            );
        }

        String superAdminEmail = authentication.getName();

        User superAdmin = userRepository
                .findByEmail(superAdminEmail)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Super Admin account not found"
                        )
                );

        if (superAdmin.getRole() != Role.SUPER_ADMIN) {

            throw new IllegalStateException(
                    "Only Super Admin can delete administrators"
            );
        }

        if (!passwordEncoder.matches(
                password,
                superAdmin.getPassword()
        )) {

            throw new IllegalArgumentException(
                    "Incorrect Super Admin password"
            );
        }

        User admin = userRepository
                .findById(adminId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Administrator not found"
                        )
                );

        if (admin.getRole() == Role.SUPER_ADMIN) {

            throw new IllegalArgumentException(
                    "A Super Admin cannot be deleted"
            );
        }

        if (admin.getRole() != Role.ADMIN) {

            throw new IllegalArgumentException(
                    "Selected user is not an administrator"
            );
        }

        if (superAdmin.getId().equals(admin.getId())) {

            throw new IllegalArgumentException(
                    "You cannot delete your own account"
            );
        }

        userRepository.delete(admin);
    }

    private AdminUserSummaryResponse mapToSummary(User user) {

        return AdminUserSummaryResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .totalSubmissions(0)
                .solvedProblems(0)
                .acceptedSubmissions(0)
                .acceptanceRate(0.0)
                .totalAiAnalyses(0)
                .totalMistakes(0)
                .build();
    }
}