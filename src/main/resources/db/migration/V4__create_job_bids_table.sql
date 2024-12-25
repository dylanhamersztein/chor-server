CREATE TABLE IF NOT EXISTS job_bids
(
    id             uuid                     default gen_random_uuid() not null primary key,
    job_request_id uuid                                               not null references job_requests (id),
    cleaner_id     uuid                                               not null references users (id),
    price          decimal                                            not null,
    created_time   timestamp with time zone default now()             not null
);

CREATE INDEX IF NOT EXISTS job_bids_job_request_id_idx ON job_bids (job_request_id);
