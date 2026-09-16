package com.labi.taskflowservice.event;

import java.util.UUID;

public record StudyJobFailedEvent(

        UUID jobId,
        String informaticaRunId,
        String failureReason
) {
}
