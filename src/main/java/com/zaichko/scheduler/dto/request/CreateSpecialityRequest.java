package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateSpecialityRequest {
    @NotBlank(message = "Speciality name must not be blank.")
    private String name;

    private String code;
    private String description;
}
