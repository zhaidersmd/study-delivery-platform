package com.labi.studyjobservice.service;

import com.labi.studyjobservice.dto.CreateStudyJobRequest;
import com.labi.studyjobservice.dto.StudyJobResponse;
import com.labi.studyjobservice.entity.JobStatus;
import com.labi.studyjobservice.entity.StatusChangedBy;
import com.labi.studyjobservice.entity.StudyJob;
import com.labi.studyjobservice.exception.BusinessDuplicateException;
import com.labi.studyjobservice.exception.IdempotencyConflictException;
import com.labi.studyjobservice.exception.JobNotFoundException;
import com.labi.studyjobservice.exception.StudyNotAuthorizedException;
import com.labi.studyjobservice.repository.CustomerStudyRepository;
import com.labi.studyjobservice.repository.StudyJobRepository;
import jakarta.transaction.Transactional;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class StudyJobService {

    private final StudyJobRepository studyJobRepository;
    private final StudyJobCreator studyJobCreator;
    private final CustomerStudyRepository customerStudyRepository;
    private final JobTransitionService jobTransitionService;
    private final OutboxEventService outboxEventService;

    public StudyJobService(StudyJobRepository studyJobRepository, StudyJobCreator studyJobCreator, CustomerStudyRepository customerStudyRepository, JobTransitionService jobTransitionService, OutboxEventService outboxEventService) {
        this.studyJobRepository = studyJobRepository;
        this.studyJobCreator = studyJobCreator;
        this.customerStudyRepository = customerStudyRepository;
        this.jobTransitionService = jobTransitionService;
        this.outboxEventService = outboxEventService;
    }

    private String generateRequestHash(String customerId, CreateStudyJobRequest request, String idempotencyKey) {

        String value = String.join("|", request.deliveryId(), request.recipientId(), request.studyId());

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate request hash", e);
        }
    }

    private StudyJobResponse toResponse(StudyJob job, String message) {
        return new StudyJobResponse(job.getId(), job.getDeliveryId(), job.getRecipientId(), job.getStudyId(), job.getStatus(), job.getCreatedAt(), job.getUpdatedAt(), message);
    }

    @Transactional()
    public StudyJobResponse getJob(UUID jobId) {

        StudyJob job = studyJobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Study job not found: " + jobId));

        return toResponse(job, "Study job found");
    }


    private StudyJobResponse handleExistingJob(StudyJob job, String requestHash) {

        if (!job.getRequestHash().equals(requestHash)) {

            throw new IdempotencyConflictException("Idempotency key was reused with different request data");
        }

        return toResponse(job, "Existing idempotent request");
    }

    @Transactional
    public StudyJobResponse createJob(String customerId, String idempotencyKey, CreateStudyJobRequest request) {

        boolean authorized = customerStudyRepository.isStudyAuthorized(customerId, request.studyId());

        if (!authorized) {
            throw new StudyNotAuthorizedException("Customer " + customerId + " is not authorized to run study " + request.studyId());
        }

        String requestHash = generateRequestHash(customerId, request, idempotencyKey);

        /*
         * Step 1:createJob
         * Check whether this exact idempotency key was
         * already used.
         */
        var existingJob = studyJobRepository.findByCustomerIdAndIdempotencyKey(customerId, idempotencyKey);

        if (existingJob.isPresent()) return handleExistingJob(existingJob.get(), requestHash);

        /*
         * Step 2:
         * Try to create the job.
         *
         * The database constraints are the final protection
         * against concurrent requests.
         */

        try {
            StudyJob studyJob = studyJobCreator.create(customerId, idempotencyKey, request, requestHash);
            jobTransitionService.transition(studyJob, JobStatus.QUEUED, StatusChangedBy.STUDY_JOB_SERVICE,"Job accepted and queued for Taskflow execution" );
            outboxEventService.createStudyJobRequestedEvent(
                    studyJob.getId(),
                    studyJob.getCustomerId(),
                    studyJob.getDeliveryId(),
                    studyJob.getRecipientId(),
                    studyJob.getStudyId(),
                    studyJob.getIdempotencyKey()
            );
            return toResponse(studyJob, "Study job accepted");


        } catch (DataIntegrityViolationException exception) {
            return handleConstraintViolation(exception, customerId, idempotencyKey, requestHash);
        }
    }

    private StudyJobResponse handleConstraintViolation(DataIntegrityViolationException exception, String customerId, String idempotencyKey, String requestHash) {
        String constraintName = extractConstraintName(exception);

        if ("uk_study_job_idempotency".equals(constraintName)) {
            return handleConcurrentIdempotency(customerId, idempotencyKey, requestHash);
        }

        if ("uk_active_business_job".equals(constraintName)) {
            throw new BusinessDuplicateException("An active job already exists for this customer, " + "delivery, recipient and study");
        }

        throw new IllegalStateException("Unexpected database constraint violation: " + constraintName, exception);

    }

    private String extractConstraintName(DataIntegrityViolationException exception) {
        Throwable cause = exception;
        while (cause != null) {
            if (cause instanceof PSQLException psqlException) {
                if (psqlException.getServerErrorMessage() != null) {
                    return psqlException.getServerErrorMessage().getConstraint();
                }
            }
            cause = cause.getCause();
        }


        return null;
    }

    private StudyJobResponse handleConcurrentIdempotency(String customerId, String idempotencyKey, String requestHash) {
        StudyJob job = studyJobRepository.findByCustomerIdAndIdempotencyKey(customerId, idempotencyKey).orElseThrow(() -> new IllegalStateException("Idempotency constraint was violated, " + "but the existing job could not be found"));

        if (!job.getRequestHash().equals(requestHash)) {
            throw new IdempotencyConflictException("Idempotency key was concurrently reused " + "with different request data");
        }

        return toResponse(job, "Existing idempotent request");


    }

    @Transactional
    public void markJobStarted(
            UUID jobId,
            String informaticaRunId) {

        StudyJob job =
                studyJobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new JobNotFoundException(
                                        "Job not found: " + jobId
                                )
                        );
        job.setInformaticaRunId(informaticaRunId);
        studyJobRepository.save(job);
        jobTransitionService.transition(
                job,
                JobStatus.RUNNING,
                StatusChangedBy.TASKFLOW_SERVICE,
                "Informatica Taskflow started. Run ID: "
                        + informaticaRunId
        );
    }

}


