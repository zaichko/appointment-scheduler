package com.zaichko.scheduler.exception;

public class SpecialityInUseException extends RuntimeException {
    public SpecialityInUseException(String message) {
        super(message);
    }
}
