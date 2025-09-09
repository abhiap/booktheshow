package org.aap.booking.exception;


/**
 * Base not found exception
 */
public class ResourceNotFoundException extends BookingSystemException {
    public ResourceNotFoundException(String errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}
