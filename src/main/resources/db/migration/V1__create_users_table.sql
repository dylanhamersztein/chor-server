CREATE TABLE IF NOT EXISTS users
(
    id             uuid        not null default gen_random_uuid() primary key,
    first_name     varchar(30) not null,
    middle_names   varchar(30)          default null,
    last_name      varchar(50) not null,
    address_line_1 varchar(70) not null,
    address_line_2 varchar(70),
    town           varchar(30),
    city           varchar(30),
    post_code      varchar(10) not null,
    active         boolean     not null default true
);
