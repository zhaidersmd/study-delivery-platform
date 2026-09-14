package com.labi.studyjobservice.exception;

public class InvalidJobStateTransitionException extends RuntimeException{
    public InvalidJobStateTransitionException(String message) {
        super(message);
    }
}
