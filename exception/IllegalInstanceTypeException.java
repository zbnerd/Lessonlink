package com.lessonlink.exception;

public class IllegalInstanceTypeException extends RuntimeException {
    public IllegalInstanceTypeException() {
    }

    public IllegalInstanceTypeException(String message) {
        super(message);
    }

    public IllegalInstanceTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalInstanceTypeException(Throwable cause) {
        super(cause);
    }

    public IllegalInstanceTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
