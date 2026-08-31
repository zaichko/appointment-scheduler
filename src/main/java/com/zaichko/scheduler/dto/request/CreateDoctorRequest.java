package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateDoctorRequest {
    private Integer experience;
    private String bio;
    private List<Long> specialitiesId;

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
}
