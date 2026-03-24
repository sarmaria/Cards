package com.banking.cards.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s is not found for the %s with value %s", resource, field, value));
    }
}
