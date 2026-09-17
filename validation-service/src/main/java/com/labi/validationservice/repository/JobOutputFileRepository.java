package com.labi.validationservice.repository;

import com.labi.validationservice.entity.JobOutputFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobOutputFileRepository extends JpaRepository<JobOutputFile, UUID> {
    Optional<JobOutputFile> findByJobId(UUID jobId);

}
