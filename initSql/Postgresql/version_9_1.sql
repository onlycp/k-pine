

CREATE TABLE IF NOT EXISTS "sys_i18n" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "i18n_key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "message" text COLLATE "pg_catalog"."default",
    "app_id" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    CONSTRAINT "sys_i18n_pk_kfaas_lib" PRIMARY KEY ("id")
    )
;



COMMENT ON COLUMN "sys_i18n"."id" IS '主键';

COMMENT ON COLUMN "sys_i18n"."i18n_key" IS '键名';

COMMENT ON COLUMN "sys_i18n"."message" IS '国际化配置信息，JSON保存';

COMMENT ON COLUMN "sys_i18n"."app_id" IS '归属应用ID';

COMMENT ON COLUMN "sys_i18n"."when_created" IS '创建时间';

COMMENT ON COLUMN "sys_i18n"."who_created" IS '创建人员';

COMMENT ON COLUMN "sys_i18n"."when_modified" IS '修改时间';

COMMENT ON COLUMN "sys_i18n"."who_modified" IS '修改人员';

COMMENT ON TABLE "sys_i18n" IS '系统表-国际化配置';

ALTER TABLE "sys_api"
    ADD COLUMN "module_id" VARCHAR(36) null ;

COMMENT ON COLUMN "sys_api"."module_id" IS '关联模块';


