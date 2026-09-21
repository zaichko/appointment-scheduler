package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateSpecialityRequest(
        String name,

        @Size(max = 5, message = "Maximum code length is 5 symbols.")
        String code,

        String description
){}