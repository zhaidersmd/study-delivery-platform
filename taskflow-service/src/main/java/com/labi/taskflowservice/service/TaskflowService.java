package com.labi.taskflowservice.service;

import com.labi.taskflowservice.client.InformaticaClient;
import com.labi.taskflowservice.event.StudyJobRequestedEvent;
import com.labi.taskflowservice.event.StudyJobStartedEvent;
import com.labi.taskflowservice.kafka.TaskflowEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TaskflowService {


    private final TaskflowExecutionCreator executionCreator;
    private final InformaticaClient informaticaClient;
    private final TaskflowExecutionService executionService;
    private final TaskflowEventPublisher eventPublisher;

    public TaskflowService(TaskflowExecutionCreator executionCreator, InformaticaClient informaticaClient, TaskflowExecutionService executionService, TaskflowEventPublisher eventPublisher) {
        this.executionCreator = executionCreator;
        this.informaticaClient = informaticaClient;
        this.executionService = executionService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void process(StudyJobRequestedEvent event) {

        boolean created = executionCreator.createIfNotExists(event.jobId());
        if (!created) {
            System.out.println("Taskflow already exists for job: " + event.jobId());
            return;
        }

        try {
            System.out.println("Taskflow execution created for job: " + event.jobId());

            String runId = informaticaClient.startTaskflow(event.customerId(), event.deliveryId(), event.recipientId(), event.studyId());

            executionService.markRunning(event.jobId(), runId);
            eventPublisher.publishJobStarted(new StudyJobStartedEvent(
                    event.jobId(),
                    event.customerId(),
                    event.deliveryId(),
                    event.recipientId(),
                    event.studyId(),
                    event.idempotencyKey(),
                    runId
            ));

            System.out.println("Informatica Taskflow started. Run ID: " + runId);
        } catch (Exception exception) {
            executionService.markFailed(event.jobId());
            System.err.println("Failed to start Informatica Taskflow for job: " + event.jobId());
            throw exception;
        }
    }
}
