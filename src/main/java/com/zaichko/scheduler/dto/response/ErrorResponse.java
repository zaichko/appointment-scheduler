package com.zaichko.scheduler.dto.response;

public record ErrorResponse(
    int statusCode,
    String message
){}
