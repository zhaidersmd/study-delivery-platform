package com.labi.validationservice.event;

import java.util.UUID;

public record StudyJobCompletedEvent(
        UUID jobId,
        String informaticaRunId
) {}
