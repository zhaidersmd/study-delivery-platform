package com.labi.studyjobservice;

import com.labi.studyjobservice.entity.JobStatus;
import com.labi.studyjobservice.exception.InvalidJobStateTransitionException;
import com.labi.studyjobservice.service.JobStateMachine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JobStateMachineTest {

    private final JobStateMachine stateMachine = new JobStateMachine();

    @Test
    void shouldAllowReceivedToQueued() {

        assertDoesNotThrow(() -> stateMachine.validateTransition(JobStatus.RECEIVED, JobStatus.QUEUED));
    }

    @Test
    void shouldAllowRunningToCompleted() {
        assertDoesNotThrow(() -> stateMachine.validateTransition(JobStatus.RUNNING, JobStatus.COMPLETED));
    }

    @Test
    void shouldRejectReceivedToDelivered() {

        assertThrows(
                InvalidJobStateTransitionException.class,
                () ->
                        stateMachine.validateTransition(
                                JobStatus.RECEIVED,
                                JobStatus.DELIVERED
                        )
        );
    }

    @Test
    void shouldRejectDeliveredToRunning() {

        assertThrows(
                InvalidJobStateTransitionException.class,
                () ->
                        stateMachine.validateTransition(
                                JobStatus.DELIVERED,
                                JobStatus.RUNNING
                        )
        );
    }

    @Test
    void shouldRejectFailedToRunning() {

        assertThrows(
                InvalidJobStateTransitionException.class,
                () ->
                        stateMachine.validateTransition(
                                JobStatus.FAILED,
                                JobStatus.RUNNING
                        )
        );
    }
}
