package com.zaichko.scheduler.exception;

public class DoctorHasTimeSlotsException extends RuntimeException {
    public DoctorHasTimeSlotsException(String message) {
        super(message);
    }
}
