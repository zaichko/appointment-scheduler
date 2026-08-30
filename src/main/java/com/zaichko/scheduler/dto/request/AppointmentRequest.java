package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentRequest {
    @NotNull(message = "Patient ID must not be null.")
    @Positive(message = "Patient ID must be positive.")
    private Long patientId;

    @NotNull(message = "Time Slot ID must not be null.")
    @Positive(message = "Time Slot ID must be positive.")
    private Long timeSlotId;
}
