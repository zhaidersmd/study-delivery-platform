package com.labi.studyjobservice.exception;

public class StudyNotAuthorizedException extends RuntimeException{
    public StudyNotAuthorizedException(String message) {
        super(message);
    }
}
