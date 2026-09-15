package com.labi.studyjobservice.messaging;

import com.labi.studyjobservice.config.KafkaTopicConfig;
import com.labi.studyjobservice.entity.OutboxEvent;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

@Component
public class StudyJobEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public StudyJobEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OutboxEvent event) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(
                KafkaTopicConfig.STUDY_JOB_REQUESTED,
                event.getAggregateId().toString(),
                event.getPayload()
        ).get();

    }
}
