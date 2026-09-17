CREATE TABLE job_output_file
(
    id                 UUID PRIMARY KEY,

    job_id             UUID                     NOT NULL,
    informatica_run_id VARCHAR(200)             NOT NULL,

    file_name          VARCHAR(500)             NOT NULL,
    file_location      TEXT                     NOT NULL,

    status             VARCHAR(50)              NOT NULL,

    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_job_output_file_job
        UNIQUE (job_id)
);

CREATE INDEX idx_job_output_file_job_id
    ON job_output_file (job_id);