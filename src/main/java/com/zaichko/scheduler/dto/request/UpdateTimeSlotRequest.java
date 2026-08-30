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
public class UpdateTimeSlotRequest {
    @Positive
    @NotNull(message = "ID must not be null.")
    private Long id;

    @NotNull(message = "Start Time must not be null.")
    private LocalDateTime startTime;

    @NotNull(message = "End Time must not be null.")
    private LocalDateTime endTime;
}
