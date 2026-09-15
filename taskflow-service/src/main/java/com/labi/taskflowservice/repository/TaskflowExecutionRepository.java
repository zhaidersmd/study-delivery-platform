package com.labi.taskflowservice.repository;

import com.labi.taskflowservice.entity.TaskflowExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskflowExecutionRepository extends JpaRepository<TaskflowExecution, UUID> {
    Optional<TaskflowExecution> findByJobId(UUID jobId);


}
