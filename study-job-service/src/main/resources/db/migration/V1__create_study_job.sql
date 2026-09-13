CREATE TABLE study_job
(
    id                 UUID PRIMARY KEY,

    customer_id        VARCHAR(100)             NOT NULL,

    idempotency_key    VARCHAR(200)             NOT NULL,

    delivery_id        VARCHAR(100)             NOT NULL,
    recipient_id       VARCHAR(100)             NOT NULL,
    study_id           VARCHAR(100)             NOT NULL,

    status             VARCHAR(50)              NOT NULL,

    informatica_run_id VARCHAR(200),

    output_file_path   TEXT,

    expected_row_count BIGINT,

    actual_row_count   BIGINT,

    failure_reason     TEXT,

    request_hash       VARCHAR(64)              NOT NULL,

    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL,

    version            BIGINT                   NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX uk_study_job_idempotency ON study_job (customer_id, idempotency_key);