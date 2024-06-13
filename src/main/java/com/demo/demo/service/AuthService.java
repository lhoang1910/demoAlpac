package com.demo.demo.service;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.request.LoginRequest;
import com.demo.demo.dto.response.LoginSuccessResponse;
import com.demo.demo.dto.response.MessageResponse;

public interface AuthService {
    MessageResponse createUser(CreateUserRequest user);
    LoginSuccessResponse login(LoginRequest loginRequest);
}
