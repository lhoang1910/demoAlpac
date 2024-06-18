package com.demo.demo.service;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.request.FilterRequest;
import com.demo.demo.dto.response.WrapResponse;

import java.util.List;

public interface UserService {
    WrapResponse<?> deleteUserById(String id);
    WrapResponse<?> updateUserById(String id, CreateUserRequest createUserRequest);
    WrapResponse<?> getAllUser();
    WrapResponse<?> saveAllUser(List<CreateUserRequest> users);
    WrapResponse<?> findAllUsers(FilterRequest request);
}
