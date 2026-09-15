package com.labi.taskflowservice.mock;

import com.labi.taskflowservice.entity.InformaticaRunResponse;
import com.labi.taskflowservice.entity.StartTaskflowRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/mock/informatica")
public class MockInformaticaController {

    @PostMapping("/taskflows/run")
    public InformaticaRunResponse startTaskflow(@RequestBody StartTaskflowRequest request) {
        String runId = "INF-" + UUID.randomUUID();

        System.out.println("Mock Informatica received Taskflow request");

        System.out.println("Customer: " + request.customerId());

        System.out.println("Delivery: " + request.deliveryId());

        System.out.println("Recipient: " + request.recipientId());

        System.out.println("Study: " + request.studyId());

        System.out.println("Generated runId: " + runId);

        return new InformaticaRunResponse(runId);
    }
}
