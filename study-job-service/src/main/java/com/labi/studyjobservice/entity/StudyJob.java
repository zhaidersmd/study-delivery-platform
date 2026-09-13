package com.labi.studyjobservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "study_job")
@Getter @Setter @AllArgsConstructor
public class StudyJob {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @Column(name = "delivery_id", nullable = false)
    private String deliveryId;

    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    @Column(name = "study_id", nullable = false)
    private String studyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(name = "informatica_run_id")
    private String informaticaRunId;

    @Column(name = "output_file_path")
    private String outputFilePath;

    @Column(name = "expected_row_count")
    private Long expectedRowCount;

    @Column(name = "actual_row_count")
    private Long actualRowCount;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "request_hash", nullable = false)
    private String requestHash;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    public StudyJob() {
    }

    // getters and setters
}
