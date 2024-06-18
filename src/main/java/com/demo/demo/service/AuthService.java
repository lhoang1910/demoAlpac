package com.demo.demo.service;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.request.LoginRequest;
import com.demo.demo.dto.response.LoginSuccessResponse;
import com.demo.demo.dto.response.WrapResponse;

public interface AuthService {
    WrapResponse<?> createUser(CreateUserRequest user);
    WrapResponse<LoginSuccessResponse> login(LoginRequest loginRequest);

}
