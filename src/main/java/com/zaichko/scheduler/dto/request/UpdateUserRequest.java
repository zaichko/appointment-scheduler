package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    @NotNull(message = "User ID must not be null.")
    @Positive(message = "User ID must be positive.")
    private Long id;

    @Email(message = "Incorrect format of email address.")
    private String email;

    private String firstName;

    private String lastName;

    @Size(min = 8, message = "Password length must be at least 8 symbols.")
    private String password;
}
