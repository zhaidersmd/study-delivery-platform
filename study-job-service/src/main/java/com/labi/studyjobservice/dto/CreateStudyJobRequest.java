package com.labi.studyjobservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStudyJobRequest(
        @NotBlank
        String deliveryId,

        @NotBlank
        String recipientId,

        @NotBlank
        String studyId
){
}
