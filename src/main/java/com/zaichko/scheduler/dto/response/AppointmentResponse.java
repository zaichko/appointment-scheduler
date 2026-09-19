package com.zaichko.scheduler.dto.response;

import java.time.OffsetDateTime;

public record AppointmentResponse(
    Long id,
    String patientName,
    String doctorName,
    OffsetDateTime startTime,
    OffsetDateTime endTime,
    String status
) {}