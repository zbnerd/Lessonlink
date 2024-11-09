package com.lessonlink.exception;

public class AttendanceCreateNotAllowedException extends RuntimeException {
    public AttendanceCreateNotAllowedException() {
    }

    public AttendanceCreateNotAllowedException(String message) {
        super(message);
    }

    public AttendanceCreateNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttendanceCreateNotAllowedException(Throwable cause) {
        super(cause);
    }

    public AttendanceCreateNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
