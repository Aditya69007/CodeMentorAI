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

System.out.println("========== GOOGLE OAUTH ==========");
System.out.println("Email : " + oauthUser.getAttribute("email"));
System.out.println("Given : " + oauthUser.getAttribute("given_name"));
System.out.println("Family: " + oauthUser.getAttribute("family_name"));
System.out.println("==================================");

        String email = oauthUser.getAttribute("email");

        String firstName = oauthUser.getAttribute("given_name");

        String lastName = oauthUser.getAttribute("family_name");

        String picture = oauthUser.getAttribute("picture");

        String providerId = oauthUser.getAttribute("sub");

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
                        .provider(AuthProvider.GOOGLE)
                        .providerId(providerId)
                        .profilePicture(picture)
                        .build();
                System.out.println("Creating new Google user...");

                    return userRepository.save(newUser);
                });

        if (user.getProvider() == AuthProvider.LOCAL) {
            user.setProvider(AuthProvider.GOOGLE);
        }

        user.setProviderId(providerId);
        user.setProfilePicture(picture);
        user.setEmailVerified(true);

        userRepository.save(user);
        System.out.println("OAuth user processing completed.");
        
        return oauthUser;
    }
}