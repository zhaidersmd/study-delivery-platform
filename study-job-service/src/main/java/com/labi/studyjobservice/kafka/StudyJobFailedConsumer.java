package com.labi.studyjobservice.kafka;



import com.labi.studyjobservice.event.StudyJobFailedEvent;
import com.labi.studyjobservice.service.StudyJobService;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class StudyJobFailedConsumer {

    private final ObjectMapper objectMapper;
    private final StudyJobService studyJobService;

    public StudyJobFailedConsumer(
            ObjectMapper objectMapper,
            StudyJobService studyJobService) {

        this.objectMapper = objectMapper;
        this.studyJobService = studyJobService;
    }

    @KafkaListener(
            topics = "study-job.failed",
            groupId = "study-job-service"
    )
    public void consume(String message) {

        try {

            StudyJobFailedEvent event =
                    objectMapper.readValue(
                            message,
                            StudyJobFailedEvent.class
                    );
            System.out.println("Received Failed event" + event);

            studyJobService.markJobFailed(
                    event.jobId(),
                    event.informaticaRunId(),
                    event.failureReason()
            );

        } catch (JsonParseException exception) {

            throw new IllegalStateException(
                    "Invalid STUDY_JOB_FAILED event",
                    exception
            );
        }
    }
}
