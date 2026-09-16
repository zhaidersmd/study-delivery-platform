package com.labi.taskflowservice.repository;

import com.labi.taskflowservice.entity.TaskflowExecution;
import com.labi.taskflowservice.entity.TaskflowExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskflowExecutionRepository extends JpaRepository<TaskflowExecution, UUID> {
    Optional<TaskflowExecution> findByJobId(UUID jobId);


    List<TaskflowExecution> findByStatus(TaskflowExecutionStatus taskflowExecutionStatus);
}
