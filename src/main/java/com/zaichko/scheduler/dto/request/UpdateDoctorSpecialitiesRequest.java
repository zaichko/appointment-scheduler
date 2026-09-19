package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UpdateDoctorSpecialitiesRequest(
        @NotEmpty(message = "Speciality IDs list must not be empty")
        Set<Long> specialitiesId
){}
