package com.dontirikk.profileservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public static final String MESSAGE = "The requested resource could not be found.";

    public ResourceNotFoundException() {
        super(MESSAGE);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
