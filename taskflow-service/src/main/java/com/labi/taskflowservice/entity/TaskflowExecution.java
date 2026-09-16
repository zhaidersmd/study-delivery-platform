package com.labi.taskflowservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor @Getter @Setter @AllArgsConstructor
@Entity
@Table(name = "taskflow_execution",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_taskflow_execution_job",
            columnNames = "job_id"
    ))
public class TaskflowExecution {

    @Id
    private UUID id;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "informatica_run_id")
    private String informaticaRunId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskflowExecutionStatus status;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public void markRunning(String runId) {

        this.informaticaRunId = runId;
        this.status = TaskflowExecutionStatus.RUNNING;
        this.updatedAt = OffsetDateTime.now();
    }

    public void markFailed() {

        this.status = TaskflowExecutionStatus.FAILED;
        this.updatedAt = OffsetDateTime.now();
    }

    public void markCompleted() {
        this.status = TaskflowExecutionStatus.COMPLETED;
        this.updatedAt = OffsetDateTime.now();
    }
}
