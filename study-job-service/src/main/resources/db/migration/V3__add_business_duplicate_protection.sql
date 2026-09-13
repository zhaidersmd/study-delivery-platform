CREATE UNIQUE INDEX uk_active_business_job
    ON study_job (
                  customer_id,
                  delivery_id,
                  recipient_id,
                  study_id
        ) WHERE status IN (
    'RECEIVED',
    'QUEUED',
    'RUNNING',
    'VALIDATING',
    'VALIDATED',
    'DELIVERY_PENDING'
);