package com.codementor.backend.service;

import com.codementor.backend.dto.AuthResponse;
import com.codementor.backend.dto.LoginRequest;

public interface AuthenticationService {

    AuthResponse login(LoginRequest request);

}