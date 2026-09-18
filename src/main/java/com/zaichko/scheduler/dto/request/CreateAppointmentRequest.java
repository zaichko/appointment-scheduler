package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateAppointmentRequest(
    @NotNull(message = "Patient ID must not be null.")
    @Positive(message = "Patient ID must be positive.")
    Long patientId,

    @NotNull(message = "Time Slot ID must not be null.")
    @Positive(message = "Time Slot ID must be positive.")
    Long timeSlotId
){}
