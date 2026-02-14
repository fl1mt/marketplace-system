package com.marketplace.notificationservice.errors;

public class InternalServerErrorException extends AppException {
    protected InternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return 500;
    }

    @Override
    public String getErrorCode() {
        return "INTERNAL_ERROR";
    }
}
