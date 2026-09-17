package com.labi.validationservice.service;

import com.labi.validationservice.entity.JobOutputFile;
import com.labi.validationservice.entity.JobOutputFileStatus;
import com.labi.validationservice.event.StudyJobCompletedEvent;
import com.labi.validationservice.repository.JobOutputFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class ValidationService {

    @Value("${validation.output-base-folder}")
    private String outputBaseFolder;

    @Value("${validation.output-file-name}")
    private String outputFileName;

    private final JobOutputFileRepository outputFileRepository;

    public ValidationService(JobOutputFileRepository outputFileRepository) {
        this.outputFileRepository = outputFileRepository;
    }

    public void process(StudyJobCompletedEvent event) {

        System.out.println("Starting validation for job: " + event.jobId());

        String fileLocation = outputBaseFolder + "/" + event.informaticaRunId() + "/" + outputFileName;

        JobOutputFile outputFile = new JobOutputFile();

        outputFile.setId(UUID.randomUUID());
        outputFile.setJobId(event.jobId());
        outputFile.setInformaticaRunId(event.informaticaRunId());
        outputFile.setFileName(outputFileName);
        outputFile.setFileLocation(fileLocation);
        outputFile.setStatus(JobOutputFileStatus.EXPECTED);
        outputFile.setCreatedAt(OffsetDateTime.now());
        outputFile.setUpdatedAt(OffsetDateTime.now());

        outputFileRepository.save(outputFile);

        System.out.println("Output file expected at: " + fileLocation);

        // Validation started
        outputFile.setStatus(JobOutputFileStatus.VALIDATING);
        outputFile.setUpdatedAt(OffsetDateTime.now());

        outputFileRepository.save(outputFile);

        System.out.println("Validation in progress for file: " + outputFile.getFileName());

        // Actual validation is intentionally omitted for now.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Simulate successful validation
        outputFile.setStatus(JobOutputFileStatus.VALIDATED);
        outputFile.setUpdatedAt(OffsetDateTime.now());

        outputFileRepository.save(outputFile);

        System.out.println("Validation completed for file: " + outputFile.getFileName());
    }
}
