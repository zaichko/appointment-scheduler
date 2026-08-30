package com.zaichko.scheduler.dto.response;

public record SpecialityResponse(
        Long id,
        String name,
        String code,
        String description,
        boolean active
){}
