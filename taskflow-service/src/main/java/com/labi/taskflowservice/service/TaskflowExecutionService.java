package com.labi.taskflowservice.service;

import com.labi.taskflowservice.entity.TaskflowExecution;
import com.labi.taskflowservice.repository.TaskflowExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TaskflowExecutionService {
    private final TaskflowExecutionRepository executionRepository;

    public TaskflowExecutionService(TaskflowExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    @Transactional
    public void markRunning(UUID jobId, String runId) {
        TaskflowExecution execution =   executionRepository.findByJobId(jobId)
                .orElseThrow(() -> new IllegalStateException("Taskflow execution not found for job: " + jobId));
        execution.markRunning(runId);
    }

    @Transactional
    public void markFailed(UUID jobId) {

        TaskflowExecution execution = executionRepository.findByJobId(jobId)
                .orElseThrow(() ->new IllegalStateException("Taskflow execution not found for job: " + jobId ));

        execution.markFailed();
    }
}
