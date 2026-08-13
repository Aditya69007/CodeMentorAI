package com.codementor.backend.config;

import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Value("${app.admin.first-name:CodeMentor}")
    private String adminFirstName;

    @Value("${app.admin.last-name:Admin}")
    private String adminLastName;

    @Override
    public void run(String... args) {

        if (adminEmail == null
                || adminEmail.isBlank()
                || adminPassword == null
                || adminPassword.isBlank()) {

            System.out.println(
                    "ADMIN INITIALIZER: Admin credentials are not configured."
            );

            return;
        }

        var existingAdmin = userRepository.findByEmail(adminEmail);

        if (existingAdmin.isPresent()) {

        User admin = existingAdmin.get();

        if (admin.getRole() != Role.SUPER_ADMIN) {
                admin.setRole(Role.SUPER_ADMIN);
                userRepository.save(admin);

                System.out.println(
                        "ADMIN INITIALIZER: Existing configured admin promoted to SUPER_ADMIN."
                );
        } else {
                System.out.println(
                        "ADMIN INITIALIZER: SUPER_ADMIN account already exists."
                );
        }

        return;
        }

        User admin = User.builder()
                .firstName(adminFirstName)
                .lastName(adminLastName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.SUPER_ADMIN)
                .provider(AuthProvider.LOCAL)
                .enabled(true)
                .build();

        userRepository.save(admin);

        System.out.println(
                "ADMIN INITIALIZER: Initial admin account created successfully."
        );
    }
}