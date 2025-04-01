ALTER TABLE dev_team
    ADD COLUMN is_audit smallint DEFAULT NULL,
    ADD COLUMN image varchar(255) DEFAULT NULL;