package com.labi.taskflowservice.entity;

public record StartTaskflowRequest(
        String customerId,
        String deliveryId,
        String recipientId,
        String studyId
) {
}
