package com.marketplace.notificationservice.errors;

public abstract class AppException extends RuntimeException{
    protected AppException(String message){
        super(message);
    }

    public abstract int getStatus();
    public abstract String getErrorCode();
}
