package com.labi.studyjobservice.kafka;

import com.labi.studyjobservice.event.StudyJobStartedEvent;
import com.labi.studyjobservice.service.StudyJobService;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class StudyJobStartedConsumer {
    private final ObjectMapper objectMapper;
    private final StudyJobService studyJobService;

    public StudyJobStartedConsumer(ObjectMapper objectMapper, StudyJobService studyJobService) {
        this.objectMapper = objectMapper;
        this.studyJobService = studyJobService;
    }

    @KafkaListener(topics = "study-job.started", groupId = "study-job-service")
    public void consume(String message) {

        try {

            StudyJobStartedEvent event = objectMapper.readValue(message, StudyJobStartedEvent.class);

            studyJobService.markJobStarted(event.jobId(), event.informaticaRunId());

        } catch (JsonParseException exception) {

            throw new IllegalStateException("Invalid STUDY_JOB_STARTED event", exception);
        }
    }
}
