package com.labi.validationservice.kafka;

import com.labi.validationservice.event.StudyJobCompletedEvent;
import com.labi.validationservice.service.ValidationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class StudyJobCompletedConsumer {
    private final ObjectMapper objectMapper;
    private final ValidationService validationService;

    public StudyJobCompletedConsumer(ObjectMapper objectMapper, ValidationService validationService) {
        this.objectMapper = objectMapper;
        this.validationService = validationService;
    }

    @KafkaListener(
            topics = "study-job.completed",
            groupId = "validation-service"
    )
    public void consume(String message){
        System.out.println("Validation-service started consuming study-job.completed");
        try {
            StudyJobCompletedEvent event =  objectMapper.readValue(message, StudyJobCompletedEvent.class);
            validationService.process(event);
        }catch (JacksonException exception) {
            throw new IllegalStateException("Invalid STUDY_JOB_COMPLETED event",exception);
        }
    }

}
