package com.labi.studyjobservice.event;

import java.util.UUID;

public record StudyJobCompletedEvent(
        UUID jobId,
        String informaticaRunId
) {
}
