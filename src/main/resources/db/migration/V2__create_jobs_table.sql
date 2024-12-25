CREATE TABLE IF NOT EXISTS jobs
(
    id          uuid default gen_random_uuid() not null primary key,
    location    geography(Point, 4326)         not null,
    start_time  timestamp with time zone       not null,
    duration    varchar                        not null,
    job_details varchar,
    customer_id uuid                           not null references users (id),
    cleaner_id  uuid                           not null references users (id)
);
