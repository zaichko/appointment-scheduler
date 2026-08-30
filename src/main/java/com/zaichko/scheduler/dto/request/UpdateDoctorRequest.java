package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
}
