ALTER TABLE dev_team
    ADD COLUMN is_audit smallint DEFAULT NULL,
    ADD COLUMN image varchar(255) DEFAULT NULL;
ALTER TABLE  DEV_APPLICATION modify short_name varchar(200) not null;
