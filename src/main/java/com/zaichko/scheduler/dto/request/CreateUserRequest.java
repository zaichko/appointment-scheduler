package com.zaichko.scheduler.dto.request;

import com.zaichko.scheduler.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Email must not be null.")
    @Email(message = "Incorrect format of email address.")
    private String email;

    @NotBlank(message = "First name must not be null.")
    private String firstName;

    @NotBlank(message = "Last name must not be null.")
    private String lastName;

    @NotBlank(message = "Password must not be null.")
    @Size(min = 8, message = "Password length must be at least 8 symbols.")
    private String password;

    @NotBlank(message = "Role cannot be blank")
    private Role role;
}
