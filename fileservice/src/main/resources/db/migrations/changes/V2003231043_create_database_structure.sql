CREATE TABLE fileservice_bd.file
(
    id           BIGINT(20) AUTO_INCREMENT NOT NULL primary key,
    name         VARCHAR(255)              NOT NULL,
    size         INT(11)                   NOT NULL,
    created_date TIMESTAMP                 NOT NULL DEFAULT CURRENT_TIMESTAMP
);

