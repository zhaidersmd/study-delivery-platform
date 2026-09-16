package com.labi.studyjobservice.kafka;

import com.labi.studyjobservice.event.StudyJobCompletedEvent;
import com.labi.studyjobservice.event.StudyJobStartedEvent;
import com.labi.studyjobservice.service.StudyJobService;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class StudyJobCompletedConsumer {

    private final ObjectMapper objectMapper;
    private final StudyJobService studyJobService;

    public StudyJobCompletedConsumer(ObjectMapper objectMapper, StudyJobService studyJobService) {
        this.objectMapper = objectMapper;
        this.studyJobService = studyJobService;
    }

    @KafkaListener(topics = "study-job.completed", groupId = "study-job-service")
    public void consume(String message) {

        try {

            StudyJobCompletedEvent event = objectMapper.readValue(message, StudyJobCompletedEvent.class);
            System.out.println("Received Completed event" + event);
            studyJobService.markJobCompleted(event.jobId(), event.informaticaRunId());

        } catch (JsonParseException exception) {

            throw new IllegalStateException( "Invalid STUDY_JOB_COMPLETED event", exception);
        }
    }
}
