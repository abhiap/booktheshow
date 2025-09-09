package org.aap.booking.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String errorCode;
    private String message;
    private String timestamp;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ErrorResponse() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
