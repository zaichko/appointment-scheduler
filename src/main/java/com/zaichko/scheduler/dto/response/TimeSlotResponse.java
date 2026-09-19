package com.zaichko.scheduler.dto.response;

import java.time.OffsetDateTime;

public record TimeSlotResponse(
        Long id,
        String doctorName,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        boolean isAvailable
){}
