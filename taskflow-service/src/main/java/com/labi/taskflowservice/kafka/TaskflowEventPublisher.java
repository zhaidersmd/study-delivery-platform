package com.labi.taskflowservice.kafka;

import com.labi.taskflowservice.event.StudyJobCompletedEvent;
import com.labi.taskflowservice.event.StudyJobFailedEvent;
import com.labi.taskflowservice.event.StudyJobStartedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
// publish event when taskflow is started for study-job-service to consume
public class TaskflowEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TaskflowEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishJobStarted(StudyJobStartedEvent event) {

        try {

            String payload = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("study-job.started", event.jobId().toString(), payload);

        } catch (JsonParseException exception) {

            throw new IllegalStateException("Unable to serialize STUDY_JOB_STARTED event", exception);

        }

    }

    public void publishJobCompleted(StudyJobCompletedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("study-job.completed", event.jobId().toString(), payload);
            System.out.println("Published Completed Event for TaskflowRunId " + event.informaticaRunId());
        }catch (JsonParseException exception) {
            throw new IllegalStateException("Unable to serialize STUDY_JOB_COMPLETED event", exception);
        }
    }

    public void publishJobFailed(StudyJobFailedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("study-job.failed", event.jobId().toString(), payload);
            System.out.println("Published Failed Event for TaskflowRunId " + event.informaticaRunId());
        }catch (JsonParseException exception) {
            throw new IllegalStateException("Unable to serialize STUDY_JOB_FAILED event", exception);
        }
    }


}
