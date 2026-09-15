CREATE TABLE taskflow_execution
(
    id                 UUID PRIMARY KEY,
    job_id             UUID                     NOT NULL,
    informatica_run_id VARCHAR(200),
    status             VARCHAR(50)              NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_taskflow_execution_job UNIQUE (job_id)
);