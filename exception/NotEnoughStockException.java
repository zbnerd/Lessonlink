package com.lessonlink.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {

    }
    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
