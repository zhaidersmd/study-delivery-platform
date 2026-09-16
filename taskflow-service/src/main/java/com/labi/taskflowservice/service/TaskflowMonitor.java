package com.labi.taskflowservice.service;

import com.labi.taskflowservice.client.InformaticaClient;
import com.labi.taskflowservice.entity.InformaticaTaskflowStatus;
import com.labi.taskflowservice.entity.TaskflowExecution;
import com.labi.taskflowservice.entity.TaskflowExecutionStatus;
import com.labi.taskflowservice.event.StudyJobCompletedEvent;
import com.labi.taskflowservice.event.StudyJobFailedEvent;
import com.labi.taskflowservice.kafka.TaskflowEventPublisher;
import com.labi.taskflowservice.repository.TaskflowExecutionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskflowMonitor {

    private final TaskflowExecutionRepository executionRepository;
    private final InformaticaClient informaticaClient;
    private final TaskflowExecutionService executionService;
    private final TaskflowEventPublisher eventPublisher;

    public TaskflowMonitor(TaskflowExecutionRepository executionRepository, InformaticaClient informaticaClient, TaskflowExecutionService executionService, TaskflowEventPublisher eventPublisher) {
        this.executionRepository = executionRepository;
        this.informaticaClient = informaticaClient;
        this.executionService = executionService;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 30000)
    public void monitorRunningTaskflows() {
        List<TaskflowExecution> executions = executionRepository.findByStatus(TaskflowExecutionStatus.RUNNING);

        for (TaskflowExecution execution : executions) {
            try {
                InformaticaTaskflowStatus status = informaticaClient.getTaskFlowStatus(execution.getInformaticaRunId());
                System.out.println("Taskflow " + execution.getJobId() + " status: " + status.status());
                if ("SUCCESS".equalsIgnoreCase(status.status())) {
                    executionService.markCompleted(execution.getJobId());
                    eventPublisher.publishJobCompleted(new StudyJobCompletedEvent(execution.getJobId(),execution.getInformaticaRunId()));
                }

                else if ("FAILED".equalsIgnoreCase(status.status())) {
                    executionService.markFailed(execution.getJobId());
                    eventPublisher.publishJobFailed(new StudyJobFailedEvent(execution.getJobId(),execution.getInformaticaRunId(),status.errorMessage()));
                }


            } catch (Exception e) {
                System.err.println("Unable to check Taskflow status for job " + execution.getJobId());
            }
        }
    }
}
