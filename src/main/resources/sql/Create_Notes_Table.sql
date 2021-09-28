CREATE SEQUENCE IF NOT EXISTS counter START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS notes
(
    id      int primary key,
    name    varchar(255) unique,
    hash    varchar(255),
    created timestamp,
    content varchar(9999)
);