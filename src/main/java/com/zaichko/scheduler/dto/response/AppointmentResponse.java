package com.zaichko.scheduler.dto.response;

import java.time.LocalDateTime;

public record AppointmentResponse(
    Long id,
    String patientName,
    String doctorName,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String status
) {}