CREATE TABLE outbox_event
(
    id             UUID PRIMARY KEY,

    aggregate_type VARCHAR(100)             NOT NULL,

    aggregate_id   UUID                     NOT NULL,

    event_type     VARCHAR(100)             NOT NULL,

    payload        TEXT                     NOT NULL,

    status         VARCHAR(30)              NOT NULL DEFAULT 'PENDING',

    created_at     TIMESTAMP WITH TIME ZONE NOT NULL,

    published_at   TIMESTAMP WITH TIME ZONE,

    retry_count    INTEGER                  NOT NULL DEFAULT 0,

    last_error     TEXT
);

CREATE INDEX idx_outbox_event_pending
    ON outbox_event (status, created_at);