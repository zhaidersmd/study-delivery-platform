CREATE TABLE customer_study
(
    customer_id VARCHAR(100)             NOT NULL,
    study_id    VARCHAR(100)             NOT NULL,
    enabled     BOOLEAN                  NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (customer_id, study_id)
);

INSERT INTO customer_study
    (customer_id, study_id)
VALUES ('CUSTOMER-001', 'STUDY-001'),
       ('CUSTOMER-001', 'STUDY-002'),
       ('CUSTOMER-002', 'STUDY-003'),
       ('CUSTOMER-002', 'STUDY-004');