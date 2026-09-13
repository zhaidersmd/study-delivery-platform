package com.labi.studyjobservice.dto;

import com.labi.studyjobservice.entity.JobStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StudyJobResponse(
        UUID jobId,
        String deliveryId,
        String recipientId,
        String studyId,
        JobStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        String message

) {
}
