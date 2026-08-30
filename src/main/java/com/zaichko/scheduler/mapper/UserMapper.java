package com.zaichko.scheduler.mapper;

import com.zaichko.scheduler.dto.response.UserResponse;
import com.zaichko.scheduler.entity.User;

public class UserMapper {
    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
