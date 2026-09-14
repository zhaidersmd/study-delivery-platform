CREATE TABLE job_status_history
(
    id         BIGSERIAL PRIMARY KEY,

    job_id     UUID                     NOT NULL,

    old_status VARCHAR(50),

    new_status VARCHAR(50)              NOT NULL,

    reason     TEXT,

    changed_by VARCHAR(100)             NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_job_status_history_job
        FOREIGN KEY (job_id)
            REFERENCES study_job (id)
);

CREATE INDEX idx_job_status_history_job_id
    ON job_status_history (job_id);