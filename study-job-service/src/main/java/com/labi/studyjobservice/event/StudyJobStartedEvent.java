package com.labi.studyjobservice.event;

import java.util.UUID;

public record StudyJobStartedEvent(
        UUID jobId,
        String customerId,
        String deliveryId,
        String recipientId,
        String studyId,
        String idempotencyKey,
        String informaticaRunId
) {
}
