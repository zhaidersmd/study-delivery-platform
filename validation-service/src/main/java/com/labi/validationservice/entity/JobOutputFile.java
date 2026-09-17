package com.labi.validationservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "job_output_file")
@Getter
@Setter
@NoArgsConstructor
public class JobOutputFile {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID jobId;

    @Column(nullable = false, length = 200)
    private String informaticaRunId;

    @Column(nullable = false, length = 500)
    private String fileName;

    @Column(nullable = false)
    private String fileLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private JobOutputFileStatus status;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}