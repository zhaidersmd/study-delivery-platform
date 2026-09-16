package com.labi.taskflowservice.client;

import com.labi.taskflowservice.entity.InformaticaTaskflowStatus;

public interface InformaticaClient {
    String startTaskflow(
            String customerId,
            String deliveryId,
            String recipientId,
            String studyId
    );

    InformaticaTaskflowStatus getTaskFlowStatus(String runId);
}
