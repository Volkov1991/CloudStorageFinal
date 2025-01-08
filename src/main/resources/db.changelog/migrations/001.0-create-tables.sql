--changeset 1
CREATE SCHEMA IF NOT EXISTS cloudstoragefinal;

--changeset 2
CREATE TABLE cloudstoragefinal.users
(
    id       SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(256)       NOT NULL
);

--changeset 3
CREATE TABLE cloudstoragefinal.files
(
    id        SERIAL PRIMARY KEY NOT NULL,
    file_name VARCHAR            NOT NULL,
    size      BIGINT             NOT NULL,
    content   BYTEA              NOT NULL,
    user_id   SERIAL REFERENCES cloudstoragefinal.users (id) ON DELETE CASCADE,
    CONSTRAINT uq_files UNIQUE (file_name, user_id)
);
