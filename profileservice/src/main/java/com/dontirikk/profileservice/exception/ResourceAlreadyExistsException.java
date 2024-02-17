package com.dontirikk.profileservice.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public static final String MESSAGE = "A resource with the given value(s) already exists.";

    public ResourceAlreadyExistsException() {
        super(MESSAGE);
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
