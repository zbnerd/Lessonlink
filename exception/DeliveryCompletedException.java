package com.lessonlink.exception;

public class DeliveryCompletedException extends RuntimeException {
    public DeliveryCompletedException() {
    }

    public DeliveryCompletedException(String message) {
        super(message);
    }

    public DeliveryCompletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryCompletedException(Throwable cause) {
        super(cause);
    }

    public DeliveryCompletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
