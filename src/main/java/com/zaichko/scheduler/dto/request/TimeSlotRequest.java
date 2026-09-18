package com.zaichko.scheduler.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TimeSlotRequest(
    @Future(message = "Date must be future date.")
    @NotNull(message = "Start Time must not be null.")
    LocalDateTime startTime,

    @Future(message = "Date must be future date.")
    @NotNull(message = "End Time must not be null.")
    LocalDateTime endTime
){
    @AssertTrue(message = "End time must be strictly after start time")
    public boolean isValidTimeRange() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }
}
