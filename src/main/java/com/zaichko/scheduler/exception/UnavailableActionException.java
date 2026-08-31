package com.zaichko.scheduler.exception;

public class UnavailableActionException extends RuntimeException {
    public UnavailableActionException(String message) {
        super(message);
    }
}
