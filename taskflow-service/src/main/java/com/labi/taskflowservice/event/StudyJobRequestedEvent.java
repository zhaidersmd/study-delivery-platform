package com.labi.taskflowservice.event;

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
