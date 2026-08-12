package com.codementor.backend.config;

import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameMigrationRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {

        var users = userRepository.findAll();

        for (User user : users) {

            if (user.getUsername() != null &&
                !user.getUsername().isBlank()) {
                continue;
            }

            String baseUsername =
                    (user.getFirstName() + user.getLastName())
                            .toLowerCase()
                            .replaceAll("[^a-z0-9]", "");

            String username = baseUsername;

            int count = 1;

            while (userRepository.existsByUsername(username)) {

                username = baseUsername + count;

                count++;

            }

            user.setUsername(username);

            userRepository.save(user);

            System.out.println(
                    "Generated username: " + username
            );

        }

    }

}