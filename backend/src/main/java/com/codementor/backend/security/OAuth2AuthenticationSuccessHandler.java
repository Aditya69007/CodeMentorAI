package com.codementor.backend.security;

import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        String registrationId =
                oauthToken
                        .getAuthorizedClientRegistrationId();

        String email;

        String firstName;

        String lastName;

        String picture;

        String providerId;

        AuthProvider provider;

        if ("google".equalsIgnoreCase(registrationId)) {

        email = oauthUser.getAttribute("email");

        firstName = oauthUser.getAttribute("given_name");

        lastName = oauthUser.getAttribute("family_name");

        picture = oauthUser.getAttribute("picture");

        providerId = oauthUser.getAttribute("sub");

        provider = AuthProvider.GOOGLE;

        }else {

        throw new IllegalArgumentException(
                "Unsupported OAuth provider: " + registrationId
        );

        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .email(email)
                            .firstName(firstName)
                            .lastName(lastName)
                            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .role(Role.USER)
                            .enabled(true)
                            .emailVerified(true)
                            .provider(provider)
                            .providerId(providerId)
                            .profilePicture(picture)
                            .build();

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

        String token =
                jwtService.generateToken(user.getEmail());

        getRedirectStrategy().sendRedirect(
                request,
                response,
                frontendUrl + "/oauth-success?token=" + token
        );
    }
}