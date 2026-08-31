package com.zaichko.scheduler.exception;

public class BookedSlotException extends RuntimeException {
    public BookedSlotException(String message) {
        super(message);
    }
}
