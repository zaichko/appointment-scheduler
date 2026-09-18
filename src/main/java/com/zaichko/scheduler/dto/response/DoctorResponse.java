package com.zaichko.scheduler.dto.response;

import java.util.List;

public record DoctorResponse (
        Long id,
        String fullName,
        String email,
        Integer experience,
        String bio,
        List<String> specialities
){}
