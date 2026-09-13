package com.labi.studyjobservice.repository;

import com.labi.studyjobservice.entity.CustomerStudy;
import com.labi.studyjobservice.entity.CustomerStudyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerStudyRepository extends JpaRepository<CustomerStudy, CustomerStudyId> {
    @Query("""
       SELECT COUNT(cs) > 0
       FROM CustomerStudy cs
       WHERE cs.id.customerId = :customerId
         AND cs.id.studyId = :studyId
         AND cs.enabled = true
       """)
    boolean isStudyAuthorized(
            @Param("customerId") String customerId,
            @Param("studyId") String studyId
    );
}

