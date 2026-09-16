package com.labi.taskflowservice.event;

import java.util.UUID;

public record StudyJobCompletedEvent (
        UUID jobId,
        String informaticaRunId
){
}
