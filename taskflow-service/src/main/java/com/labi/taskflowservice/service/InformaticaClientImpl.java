package com.labi.taskflowservice.service;

import com.labi.taskflowservice.client.InformaticaClient;
import com.labi.taskflowservice.client.InformaticaStartFeignClient;
import com.labi.taskflowservice.client.InformaticaStatusFeignClient;
import com.labi.taskflowservice.entity.InformaticaRunResponse;
import com.labi.taskflowservice.entity.InformaticaTaskflowStatus;
import com.labi.taskflowservice.entity.StartTaskflowRequest;
import org.springframework.stereotype.Service;

@Service
public class InformaticaClientImpl implements InformaticaClient {

    private final InformaticaStartFeignClient feignClient;
    private final InformaticaStatusFeignClient statusClient;

    public InformaticaClientImpl(InformaticaStartFeignClient feignClient, InformaticaStatusFeignClient statusClient) {
        this.feignClient = feignClient;
        this.statusClient = statusClient;
    }

    @Override
    public String startTaskflow(String customerId, String deliveryId, String recipientId, String studyId) {
        StartTaskflowRequest startTaskflowRequest = new StartTaskflowRequest(customerId, deliveryId, recipientId, studyId);
        InformaticaRunResponse response = feignClient.startTaskflow();

        return response.runId();
    }

    @Override
    public InformaticaTaskflowStatus getTaskFlowStatus(String runId) {
        return statusClient.getTaskflowStatus(runId);
    }
}
