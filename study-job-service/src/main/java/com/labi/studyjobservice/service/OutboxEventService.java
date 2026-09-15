package com.labi.studyjobservice.service;

import com.labi.studyjobservice.entity.OutboxEvent;
import com.labi.studyjobservice.entity.OutboxEventStatus;
import com.labi.studyjobservice.event.StudyJobRequestedEvent;
import com.labi.studyjobservice.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class OutboxEventService {
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OutboxEventService(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    public void createStudyJobRequestedEvent(UUID jobId, String customerId, String deliveryId, String recipientId, String studyId, String idempotencyKey) {

        StudyJobRequestedEvent event = new StudyJobRequestedEvent(jobId, customerId, deliveryId, recipientId, studyId, idempotencyKey);

        try {
            String payload = objectMapper.writeValueAsString(event);
            OutboxEvent outboxEvent = new OutboxEvent(
                    UUID.randomUUID(),
                    "STUDY_JOB",
                    jobId,
                    "STUDY_JOB_REQUESTED",
                    payload,
                    OutboxEventStatus.PENDING,
                    OffsetDateTime.now()
            );

            outboxEventRepository.save(outboxEvent);
        }catch (Exception jsonProcessingException) {
            throw new IllegalStateException("Unable to serialize study job event", jsonProcessingException);
        }
    }
}
