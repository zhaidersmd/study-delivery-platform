package com.labi.studyjobservice.event;

import java.util.UUID;

public record StudyJobRequestedEvent(
        UUID jobId,
        String customerId,
        String deliveryId,
        String recipientId,
        String studyId,
        String idempotencyKey

) {
}
