package com.labi.studyjobservice.service;

import com.labi.studyjobservice.entity.JobStatus;
import com.labi.studyjobservice.entity.JobStatusHistory;
import com.labi.studyjobservice.entity.StatusChangedBy;
import com.labi.studyjobservice.entity.StudyJob;
import com.labi.studyjobservice.repository.JobStatusHistoryRepository;
import com.labi.studyjobservice.repository.StudyJobRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class JobTransitionService {

    private final StudyJobRepository studyJobRepository;
    private final JobStatusHistoryRepository historyRepository;
    private final JobStateMachine stateMachine;


    public JobTransitionService(StudyJobRepository studyJobRepository, JobStatusHistoryRepository historyRepository, JobStateMachine stateMachine) {
        this.studyJobRepository = studyJobRepository;
        this.historyRepository = historyRepository;
        this.stateMachine = stateMachine;
    }


    public void transition(StudyJob job, JobStatus target, StatusChangedBy changedBy, String reason) {
        JobStatus oldStatus = stateMachine.transition(job, target);
        studyJobRepository.save(job);

        JobStatusHistory history = new JobStatusHistory(
                job.getId(),
                oldStatus,
                target,
                reason,
                changedBy,
                OffsetDateTime.now()
        );

        historyRepository.save(history);
    }

}
