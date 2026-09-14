package com.labi.studyjobservice.service;

import com.labi.studyjobservice.entity.JobStatus;
import com.labi.studyjobservice.entity.StudyJob;
import com.labi.studyjobservice.exception.InvalidJobStateTransitionException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class JobStateMachine {

    private final Map<JobStatus, Set<JobStatus>> transitions = createTransitions();

    public void validateTransition(JobStatus current, JobStatus target) {

        Set<JobStatus> allowedTargets = transitions.getOrDefault(current, Set.of());

        if (!allowedTargets.contains(target)) {
            throw new InvalidJobStateTransitionException("Invalid job state transition: " + current + " -> " + target);
        }
    }

    public JobStatus transition(StudyJob job, JobStatus target) {

        JobStatus current = job.getStatus();

        validateTransition(current, target);

        job.setStatus(target);
        job.setUpdatedAt(OffsetDateTime.now());
        return current;
    }

    private Map<JobStatus, Set<JobStatus>> createTransitions() {

        Map<JobStatus, Set<JobStatus>> map =
                new EnumMap<>(JobStatus.class);

        map.put(
                JobStatus.RECEIVED,
                EnumSet.of(
                        JobStatus.QUEUED,
                        JobStatus.CANCELLED
                )
        );

        map.put(
                JobStatus.QUEUED,
                EnumSet.of(
                        JobStatus.RUNNING,
                        JobStatus.CANCELLED
                )
        );

        map.put(
                JobStatus.RUNNING,
                EnumSet.of(
                        JobStatus.COMPLETED,
                        JobStatus.FAILED,
                        JobStatus.CANCELLED
                )
        );

        map.put(
                JobStatus.COMPLETED,
                EnumSet.of(
                        JobStatus.VALIDATING
                )
        );

        map.put(
                JobStatus.VALIDATING,
                EnumSet.of(
                        JobStatus.VALIDATED,
                        JobStatus.FAILED
                )
        );

        map.put(
                JobStatus.VALIDATED,
                EnumSet.of(
                        JobStatus.DELIVERY_PENDING
                )
        );

        map.put(
                JobStatus.DELIVERY_PENDING,
                EnumSet.of(
                        JobStatus.DELIVERED,
                        JobStatus.FAILED
                )
        );

        map.put(
                JobStatus.FAILED,
                Set.of()
        );

        map.put(
                JobStatus.DELIVERED,
                Set.of()
        );

        map.put(
                JobStatus.CANCELLED,
                Set.of()
        );

        return Map.copyOf(map);
    }
}
