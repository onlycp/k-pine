ALTER TABLE  DEV_APPLICATION modify short_name varchar(200) not null;
ALTER TABLE dev_team ADD is_audit TINYINT DEFAULT NULL;
ALTER TABLE dev_team ADD image VARCHAR(255) DEFAULT NULL;
