package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDoctorRequest {
    @Positive(message = "Doctor ID must be positive.")
    @NotNull(message = "Doctor ID must not be null.")
    private Long id;

    private Integer experience;

    private String bio;

    @Email(message = "Incorrect format of email address.")
    private String email;

    private String firstName;

    private String lastName;

    @Size(min = 8, message = "Password length must be at least 8 symbols.")
    private String password;
}
