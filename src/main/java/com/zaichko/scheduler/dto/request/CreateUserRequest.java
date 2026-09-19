package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "Email must not be null.")
    @Email(message = "Incorrect format of email address.")
    String email,

    @NotBlank(message = "First name must not be null.")
    String firstName,

    @NotBlank(message = "Last name must not be null.")
    String lastName,

    @NotBlank(message = "Password must not be null.")
    @Size(min = 8, message = "Password length must be at least 8 symbols.")
    String password
){}
