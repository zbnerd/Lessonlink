package com.lessonlink.exception;

public class AttendanceUpdateNotAllowedException extends RuntimeException {
    public AttendanceUpdateNotAllowedException() {
    }

    public AttendanceUpdateNotAllowedException(String message) {
        super(message);
    }

    public AttendanceUpdateNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttendanceUpdateNotAllowedException(Throwable cause) {
        super(cause);
    }

    public AttendanceUpdateNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
