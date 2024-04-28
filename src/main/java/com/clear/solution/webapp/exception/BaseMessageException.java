package com.clear.solution.webapp.exception;

public class BaseMessageException extends RuntimeException {
    private final String message;

    public BaseMessageException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
