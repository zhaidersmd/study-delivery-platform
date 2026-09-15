package com.labi.studyjobservice.service;

import com.labi.studyjobservice.entity.OutboxEvent;
import com.labi.studyjobservice.entity.OutboxEventStatus;
import com.labi.studyjobservice.messaging.StudyJobEventPublisher;
import com.labi.studyjobservice.repository.OutboxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final StudyJobEventPublisher eventPublisher;

    private static final Logger log = LoggerFactory.getLogger(OutboxPublisher.class);

    public OutboxPublisher(OutboxEventRepository outboxEventRepository, StudyJobEventPublisher eventPublisher) {
        this.outboxEventRepository = outboxEventRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        List<OutboxEvent> events = outboxEventRepository.findTop100ByStatusOrderByCreatedAtAsc(OutboxEventStatus.PENDING);

        for (OutboxEvent outboxEvent : events) {
            try {
                eventPublisher.publish(outboxEvent);
                outboxEvent.markPublished();
                outboxEventRepository.save(outboxEvent);

            } catch (Exception exception) {
                outboxEvent.markFailed(exception.getMessage());
                outboxEventRepository.save(outboxEvent);

                log.error("Failed to publish outbox event {}", outboxEvent.getId(), exception);
            }
        }
    }
}
