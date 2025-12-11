package com.marketplace.exceptions;

public class DuplicateAddressException extends RuntimeException {
    public DuplicateAddressException(String message) {
        super(message);
    }
}
