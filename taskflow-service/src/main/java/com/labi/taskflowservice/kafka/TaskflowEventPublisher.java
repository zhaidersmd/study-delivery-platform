package com.labi.taskflowservice.kafka;

import com.labi.taskflowservice.event.StudyJobStartedEvent;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

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


}
