--liquibase formatted sql
--changeset mate.matiovics:002_make_email_property_unique

ALTER TABLE student ADD CONSTRAINT u_email UNIQUE(email)