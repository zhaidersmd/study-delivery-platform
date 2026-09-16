package com.labi.taskflowservice.entity;

public record InformaticaTaskflowStatus (
        String assetName,
        String assetType,
        Long runId,
        String status,
        String errorMessage,
        String startTime,
        String endTime
){
}
