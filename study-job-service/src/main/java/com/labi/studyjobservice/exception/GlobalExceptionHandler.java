package com.labi.studyjobservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ApiError> handleJobNotFound(JobNotFoundException jobNotFoundException, HttpServletRequest httpServletRequest) {
        ApiError error = new ApiError(OffsetDateTime.now(), HttpStatus.NOT_FOUND.value(), "JOB_NOT_FOUND", jobNotFoundException.getMessage(), httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IdempotencyConflictException.class)
    public ResponseEntity<ApiError> handleIdempotencyConflict(IdempotencyConflictException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(OffsetDateTime.now(), HttpStatus.CONFLICT.value(), "IDEMPOTENCY_CONFLICT", exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(StudyNotAuthorizedException.class)
    public ResponseEntity<ApiError> handleStudyNotAuthorized(StudyNotAuthorizedException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(OffsetDateTime.now(), HttpStatus.FORBIDDEN.value(), "STUDY_NOT_AUTHORIZED", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(BusinessDuplicateException.class)
    public ResponseEntity<ApiError> handleBusinessDuplicate(BusinessDuplicateException exception, HttpServletRequest request) {
        ApiError error = new ApiError(OffsetDateTime.now(), HttpStatus.CONFLICT.value(), "BUSINESS_DUPLICATE", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
