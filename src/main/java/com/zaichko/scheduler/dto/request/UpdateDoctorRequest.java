package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateDoctorRequest(
    Integer experience,

    String bio,

    @Email(message = "Incorrect format of email address.")
    String email,

    String firstName,

    String lastName,

    @Size(min = 8, message = "Password length must be at least 8 symbols.")
    String password
){}
