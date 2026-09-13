package com.labi.studyjobservice.controller;

import com.labi.studyjobservice.dto.CreateStudyJobRequest;
import com.labi.studyjobservice.dto.StudyJobResponse;
import com.labi.studyjobservice.service.StudyJobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/study-jobs")
public class StudyJobController {

    private final StudyJobService studyJobService;

    public StudyJobController(StudyJobService studyJobService) {
        this.studyJobService = studyJobService;
    }

    @PostMapping
    public ResponseEntity<StudyJobResponse> createJob(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestHeader("X-Customer-Id") String customerId, @Valid @RequestBody CreateStudyJobRequest request) {

        StudyJobResponse response = studyJobService.createJob(customerId, idempotencyKey, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<StudyJobResponse> getJob(@PathVariable UUID jobId) {

        StudyJobResponse response = studyJobService.getJob(jobId);

        return ResponseEntity.ok(response);
    }
}
