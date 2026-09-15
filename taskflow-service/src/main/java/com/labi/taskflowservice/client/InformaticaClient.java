package com.labi.taskflowservice.client;

public interface InformaticaClient {
    String startTaskflow(
            String customerId,
            String deliveryId,
            String recipientId,
            String studyId
    );
}
