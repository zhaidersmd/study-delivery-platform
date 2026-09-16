package com.labi.taskflowservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InformaticaRunResponse (
        @JsonProperty("RunId")
        String runId

){
}
