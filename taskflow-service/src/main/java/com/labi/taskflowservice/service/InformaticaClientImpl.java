package com.labi.taskflowservice.service;

import com.labi.taskflowservice.client.InformaticaClient;
import com.labi.taskflowservice.client.InformaticaFeignClient;
import com.labi.taskflowservice.entity.InformaticaRunResponse;
import com.labi.taskflowservice.entity.StartTaskflowRequest;
import org.springframework.stereotype.Service;

@Service
public class InformaticaClientImpl implements InformaticaClient {

    private final InformaticaFeignClient feignClient;

    public InformaticaClientImpl(InformaticaFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public String startTaskflow(String customerId, String deliveryId, String recipientId, String studyId) {
        StartTaskflowRequest startTaskflowRequest = new StartTaskflowRequest(customerId, deliveryId, recipientId, studyId);
        InformaticaRunResponse response = feignClient.startTaskflow(startTaskflowRequest);

        return response.runId();
    }
}
