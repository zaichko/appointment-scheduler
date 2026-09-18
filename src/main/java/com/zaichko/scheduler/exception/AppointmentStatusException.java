package com.zaichko.scheduler.exception;

public class AppointmentStatusException extends RuntimeException {
    public AppointmentStatusException(String message) {
        super(message);
    }
}
