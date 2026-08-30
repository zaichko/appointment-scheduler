package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDoctorSpecialitiesRequest {
    @Positive(message = "Doctor ID must be positive.")
    @NotNull(message = "Doctor ID must not be null.")
    private Long id;

    private Set<Long> specialitiesId;
}
