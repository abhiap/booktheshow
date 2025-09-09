package org.aap.booking.exception;


/**
 * Base business logic exception
 */
public class BusinessLogicException extends BookingSystemException {
    public BusinessLogicException(String errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}
