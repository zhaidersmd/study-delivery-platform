package com.labi.taskflowservice.kafka;

import com.labi.taskflowservice.event.StudyJobRequestedEvent;
import com.labi.taskflowservice.service.TaskflowService;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class StudyJobRequestedConsumer {

    private final ObjectMapper objectMapper;
    private final TaskflowService taskflowService;

    public StudyJobRequestedConsumer(ObjectMapper objectMapper, TaskflowService taskflowService) {
        this.objectMapper = objectMapper;
        this.taskflowService = taskflowService;
    }

    @KafkaListener(
            topics = "study-job.requested",
            groupId = "taskflow-service"
    )
    public void consume(String message){
        System.out.println("Received STUDY_JOB_REQUESTED: " + message);


        try {
            StudyJobRequestedEvent event = objectMapper.readValue(message, StudyJobRequestedEvent.class);
            System.out.println("Received study job:");
            System.out.println("Job ID: " + event.jobId());
            System.out.println("Customer ID: " + event.customerId());
            System.out.println("Delivery ID: " + event.deliveryId());
            System.out.println("Recipient ID: " + event.recipientId());
            System.out.println("Study ID: " + event.studyId());
            System.out.println("Idempotency Key: " + event.idempotencyKey());
            taskflowService.process(event);
        } catch (JsonParseException jsonParseException){
            System.err.println("Unable to deserialize STUDY_JOB_REQUESTED event: " + jsonParseException.getMessage());
            throw new IllegalArgumentException("Invalid STUDY_JOB_REQUESTED event", jsonParseException);
        }
    }
}
