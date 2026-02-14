package com.marketplace.notificationservice.errors;

public class NotFoundException extends AppException {
    public NotFoundException(String message){
        super(message);
    }

    @Override
    public int getStatus() {
        return 404;
    }

    @Override
    public String getErrorCode() {
        return "NOT_FOUND";
    }
}
