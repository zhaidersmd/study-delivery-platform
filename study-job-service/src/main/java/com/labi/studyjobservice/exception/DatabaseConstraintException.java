package com.labi.studyjobservice.exception;

public class DatabaseConstraintException extends RuntimeException {

    public DatabaseConstraintException(String message) {
        super(message);
    }
}
