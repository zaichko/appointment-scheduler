package com.zaichko.scheduler.dto.response;

import java.time.LocalDateTime;

public record TimeSlotResponse(
        Long id,
        String doctorName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isBooked
){}
