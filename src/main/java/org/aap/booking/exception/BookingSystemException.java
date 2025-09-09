package org.aap.booking.exception;

public abstract class BookingSystemException extends RuntimeException {
    private final String errorCode;
    private final Object[] args;

    protected BookingSystemException(String errorCode, String message, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    public String getErrorCode() { return errorCode; }
    public Object[] getArgs() { return args; }
}

