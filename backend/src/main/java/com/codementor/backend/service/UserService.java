package com.codementor.backend.service;

import com.codementor.backend.dto.RegisterRequest;

public interface UserService {

    void registerUser(RegisterRequest request);

}