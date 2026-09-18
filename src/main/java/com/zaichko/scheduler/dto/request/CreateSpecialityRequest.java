package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSpecialityRequest(
    @NotBlank(message = "Speciality name must not be blank.")
    String name,

    @Size(max = 5, message = "Maximum code length is 5 symbols.")
    String code,
    String description
){}
