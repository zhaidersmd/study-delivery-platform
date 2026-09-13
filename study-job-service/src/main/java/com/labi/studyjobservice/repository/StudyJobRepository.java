package com.labi.studyjobservice.repository;

import com.labi.studyjobservice.entity.StudyJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudyJobRepository extends JpaRepository<StudyJob, UUID> {

    Optional<StudyJob> findByCustomerIdAndIdempotencyKey(String customerId, String idempotencyKey);
}
