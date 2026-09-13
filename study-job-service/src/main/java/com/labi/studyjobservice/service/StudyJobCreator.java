package com.labi.studyjobservice.service;

import com.labi.studyjobservice.dto.CreateStudyJobRequest;
import com.labi.studyjobservice.entity.JobStatus;
import com.labi.studyjobservice.entity.StudyJob;
import com.labi.studyjobservice.repository.StudyJobRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class StudyJobCreator {

    private final StudyJobRepository studyJobRepository;

    public StudyJobCreator(StudyJobRepository studyJobRepository) {
        this.studyJobRepository = studyJobRepository;
    }

    @Transactional
    public StudyJob create(String customerId, String idempotencyKey, CreateStudyJobRequest request, String requestHash) {
        OffsetDateTime now = OffsetDateTime.now();
        StudyJob job = new StudyJob();


        job.setCustomerId(customerId);
        job.setIdempotencyKey(idempotencyKey);
        job.setDeliveryId(request.deliveryId());
        job.setRecipientId(request.recipientId());
        job.setStudyId(request.studyId());
        job.setStatus(JobStatus.RECEIVED);
        job.setRequestHash(requestHash);
        job.setCreatedAt(now);
        job.setUpdatedAt(now);

        studyJobRepository.saveAndFlush(job);
        return job;



    }
}
