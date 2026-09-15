package com.labi.taskflowservice.service;

import com.labi.taskflowservice.entity.TaskflowExecution;
import com.labi.taskflowservice.entity.TaskflowExecutionStatus;
import com.labi.taskflowservice.repository.TaskflowExecutionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class TaskflowExecutionCreator {

    private final TaskflowExecutionRepository executionRepository;

    public TaskflowExecutionCreator(TaskflowExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean createIfNotExists(UUID jobId) {
        if (executionRepository.findByJobId(jobId).isPresent()) {
            return false;
        }

        TaskflowExecution execution = new TaskflowExecution();
        execution.setId(UUID.randomUUID());
        execution.setJobId(jobId);
        execution.setStatus(TaskflowExecutionStatus.STARTING);
        execution.setCreatedAt(OffsetDateTime.now());
        execution.setUpdatedAt(OffsetDateTime.now());

        try {
            executionRepository.saveAndFlush(execution);
            return true;
        } catch (DataIntegrityViolationException exception) {
            return false;
        }

    }
}
