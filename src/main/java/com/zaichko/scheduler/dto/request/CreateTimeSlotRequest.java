package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateTimeSlotRequest {
    @NotNull(message = "Doctor ID must not be null.")
    @Positive(message = "Doctor ID must be positive.")
    private Long doctorId;

    @NotNull(message = "Start Time must not be null.")
    private LocalDateTime startTime;

    @NotNull(message = "End Time must not be null.")
    private LocalDateTime endTime;
}
