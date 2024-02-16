--liquibase formatted sql
--changeset mate.matiovics:001_create_student_table

CREATE TABLE student(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(60),
    email VARCHAR(60)
)