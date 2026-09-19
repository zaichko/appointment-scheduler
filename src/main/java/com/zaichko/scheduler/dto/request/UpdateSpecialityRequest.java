package com.zaichko.scheduler.dto.request;

public record UpdateSpecialityRequest(
    String name,

    String code,

    String description
){}
