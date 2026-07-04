package com.codementor.backend.service.impl;

import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceAlreadyExistsException;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.codementor.backend.entity.Role;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .provider("LOCAL")
                .enabled(true)
                .build();

        userRepository.save(user);
    }
}