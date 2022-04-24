-- ----------------------------
-- Table structure for dev_sql_run
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_sql_run" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "version" int8,
    "md5" varchar(100) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "execution_time" int8,
    "success" int8
    )
;
