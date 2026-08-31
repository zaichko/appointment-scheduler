package com.zaichko.scheduler.mapper;

import com.zaichko.scheduler.dto.response.UserResponse;
import com.zaichko.scheduler.entity.User;
import org.springframework.stereotype.Component;

@Component
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
