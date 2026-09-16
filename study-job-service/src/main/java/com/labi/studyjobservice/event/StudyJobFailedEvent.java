package com.labi.studyjobservice.event;

import java.util.UUID;

public record StudyJobFailedEvent(
        UUID jobId,
        String informaticaRunId,
        String failureReason
) {
}
