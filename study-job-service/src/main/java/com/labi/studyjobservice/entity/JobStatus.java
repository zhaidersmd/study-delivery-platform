package com.labi.studyjobservice.entity;

public enum JobStatus {

    RECEIVED,
    QUEUED,
    RUNNING,
    COMPLETED,
    VALIDATING,
    VALIDATED,
    DELIVERY_PENDING,
    DELIVERED,
    FAILED,
    CANCELLED
}
