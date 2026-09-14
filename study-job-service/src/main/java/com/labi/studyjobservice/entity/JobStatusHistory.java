package com.labi.studyjobservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_status_history")
@NoArgsConstructor @Getter @Setter
public class JobStatusHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    private JobStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private JobStatus newStatus;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "changed_by", nullable = false)
    private StatusChangedBy changedBy;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public JobStatusHistory(UUID jobId, JobStatus oldStatus, JobStatus newStatus, String reason, StatusChangedBy changedBy, OffsetDateTime createdAt) {
        this.jobId = jobId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
        this.changedBy = changedBy;
        this.createdAt = createdAt;
    }
}
