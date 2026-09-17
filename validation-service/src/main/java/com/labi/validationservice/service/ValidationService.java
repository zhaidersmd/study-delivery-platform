package com.labi.validationservice.service;

import com.labi.validationservice.entity.JobOutputFile;
import com.labi.validationservice.event.StudyJobCompletedEvent;
import com.labi.validationservice.repository.JobOutputFileRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final JobOutputFileRepository outputFileRepository;

    public ValidationService(JobOutputFileRepository outputFileRepository) {
        this.outputFileRepository = outputFileRepository;
    }

    public void process(StudyJobCompletedEvent event){
        System.out.println("Starting validation for job: "+ event.informaticaRunId());

        JobOutputFile outputFile = outputFileRepository.findByJobId(event.jobId())
                .orElseThrow(() ->  new IllegalStateException("Output file metadata not found for job: " + event.jobId()));

        System.out.println(  "Informatica Run ID: "+ outputFile.getInformaticaRunId());
        System.out.println("File name: " + outputFile.getFileName());
        System.out.println("File location: " + outputFile.getFileLocation()
        );
    }

}
