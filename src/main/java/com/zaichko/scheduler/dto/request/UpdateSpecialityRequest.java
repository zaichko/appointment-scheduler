package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateSpecialityRequest {
    @Positive(message = "ID must be positive.")
    @NotNull(message = "ID must not be null.")
    private Long id;

    private String name;

    private String code;

    private String description;
}
