package com.codementor.backend.security;

import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest
    ) throws OAuth2AuthenticationException {

        OAuth2User oauthUser =
                new DefaultOAuth2UserService().loadUser(userRequest);

        System.out.println("Google attributes: " + oauthUser.getAttributes());
                
        String registrationId =
                userRequest
                        .getClientRegistration()
                        .getRegistrationId();
        String email;

        String firstName;

        String lastName;

        String picture;

        String providerId;

        AuthProvider provider;

        if ("google".equals(registrationId)) {

        email = oauthUser.getAttribute("email");

        firstName = oauthUser.getAttribute("given_name");

        lastName = oauthUser.getAttribute("family_name");

        picture = oauthUser.getAttribute("picture");

        providerId = oauthUser.getAttribute("sub");

        provider = AuthProvider.GOOGLE;

        } else {

        throw new OAuth2AuthenticationException(
                "Unsupported OAuth Provider"
        );

        }

                        
        User user = userRepository
                .findByEmail(email)
                .orElseGet(() -> {

                User newUser = User.builder()
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(
                                passwordEncoder.encode(
                                        UUID.randomUUID().toString()
                                )
                        )
                        .role(Role.USER)
                        .enabled(true)
                        .emailVerified(true)
                        .provider(provider)
                        .providerId(providerId)
                        .profilePicture(picture)
                        .build();
                System.out.println(
                        "Creating new OAuth user (" + provider + ")..."
                );

                    return userRepository.save(newUser);
                });

        if (picture != null) {
        user.setProfilePicture(picture);
        }

        if (firstName != null && !firstName.isBlank()) {
        user.setFirstName(firstName);
        }

        if (lastName != null && !lastName.isBlank()) {
        user.setLastName(lastName);
        }

        user.setProvider(provider);
        user.setProviderId(providerId);
        user.setEmailVerified(true);

        userRepository.save(user);
        System.out.println("OAuth user processing completed.");
        
        return oauthUser;
    }
}