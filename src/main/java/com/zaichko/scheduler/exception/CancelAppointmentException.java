package com.zaichko.scheduler.exception;

public class CancelAppointmentException extends RuntimeException {
    public CancelAppointmentException(String message) {
        super(message);
    }
}
