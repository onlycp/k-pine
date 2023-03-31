

CREATE TABLE IF NOT EXISTS "sys_i18n" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "i18n_key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "message" text COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    CONSTRAINT "sys_i18n_pk_kfaas_lib" PRIMARY KEY ("id")
    )
;





ALTER TABLE "sys_api"
    ADD COLUMN "module_id" VARCHAR(36) null ;

COMMENT ON COLUMN "sys_api"."module_id" IS '关联模块';


