CREATE TABLE IF NOT EXISTS notes
(
    id      int primary key,
    name    varchar(255) unique,
    hash    varchar(255),
    content varchar(9999)
);