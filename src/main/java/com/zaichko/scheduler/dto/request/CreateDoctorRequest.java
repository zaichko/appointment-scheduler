package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record CreateDoctorRequest(
    Integer experience,
    String bio,

    @NotEmpty(message = "Speciality IDs list must not be empty")
    List<Long> specialitiesId,

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
