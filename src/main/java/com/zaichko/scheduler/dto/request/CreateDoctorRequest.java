package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateDoctorRequest {
    @Positive(message = "User ID must be positive.")
    @NotNull(message = "User ID must not be null.")
    private Long userId;

    private Integer experience;
    private String bio;
    private List<Long> specialitiesId;
}
