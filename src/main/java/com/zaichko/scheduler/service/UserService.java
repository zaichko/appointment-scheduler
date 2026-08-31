package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.CreateUserRequest;
import com.zaichko.scheduler.dto.request.UpdateUserRequest;
import com.zaichko.scheduler.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(UpdateUserRequest request);

    void deleteUserById (Long id);
}
