package com.marketplace.notificationservice.errors;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public String getErrorCode() {
        return "BAD_REQUEST";
    }
}
