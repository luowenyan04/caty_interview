create table currency
(
    name         varchar(3),
    name_zh      varchar(6),
    rate         varchar(12),
    updated_time date,
    primary key (name)
);