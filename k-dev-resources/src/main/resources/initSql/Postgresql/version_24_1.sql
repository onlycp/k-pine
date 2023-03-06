CREATE TABLE sys_cache (
       id varchar(36) NOT NULL,
       code varchar(255) DEFAULT NULL,
       value varchar(255) DEFAULT NULL,
       when_expired varchar(30) DEFAULT NULL,
       when_created varchar(30) DEFAULT NULL,
       app_id varchar(36) DEFAULT NULL,
       PRIMARY KEY (id)
);