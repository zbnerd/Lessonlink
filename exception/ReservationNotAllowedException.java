package com.lessonlink.exception;

public class ReservationNotAllowedException extends RuntimeException {
    public ReservationNotAllowedException() {
    }

    public ReservationNotAllowedException(String message) {
        super(message);
    }

    public ReservationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationNotAllowedException(Throwable cause) {
        super(cause);
    }

    public ReservationNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
