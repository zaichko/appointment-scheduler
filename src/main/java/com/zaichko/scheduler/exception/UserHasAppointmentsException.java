package com.zaichko.scheduler.exception;

public class UserHasAppointmentsException extends RuntimeException {
    public UserHasAppointmentsException(String message) {
        super(message);
    }
}
