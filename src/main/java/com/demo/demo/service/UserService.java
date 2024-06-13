package com.demo.demo.service;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.response.MessageResponse;

public interface UserService {
    MessageResponse deleteUserById(String id);
    MessageResponse updateUserById(String id, CreateUserRequest createUserRequest);
}
