package com.zaichko.scheduler.dto.response;

import com.zaichko.scheduler.enums.Role;

public record UserResponse(
    Long id,
    String email,
    String firstName,
    String lastName,
    Role role
){}
