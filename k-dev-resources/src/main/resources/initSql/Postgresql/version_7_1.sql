ALTER TABLE "dev_application"
    ADD COLUMN "app_public_type" int DEFAULT 0;

COMMENT ON COLUMN "dev_application"."app_public_type" IS '应用开启类型： 0：普通应用，1：公共库应用，2：系统库应用';

CREATE TABLE IF NOT EXISTS "dev_module" (
                                       "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                       "name" varchar(100) COLLATE "pg_catalog"."default",
                                       "path" varchar(255) COLLATE "pg_catalog"."default",
                                       "has_path" int4,
                                       "parent_id" varchar(36) COLLATE "pg_catalog"."default",
                                       "sort" int4,
                                       "when_created" varchar(50) COLLATE "pg_catalog"."default",
                                       "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                       "when_modified" varchar(50) COLLATE "pg_catalog"."default",
                                       "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                       "is_sys" int4,
                                       "app_id" varchar(36) COLLATE "pg_catalog"."default",
                                       CONSTRAINT "dev_module_pk_" PRIMARY KEY ("id")
)
;



CREATE TABLE IF NOT EXISTS "ext_plugin_interface" (
                                                 "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                                 "name" varchar(255) COLLATE "pg_catalog"."default",
                                                 "resp_type" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
                                                 "content" text COLLATE "pg_catalog"."default",
                                                 "description" varchar(1024) COLLATE "pg_catalog"."default",
                                                 "pluginid" varchar(255) COLLATE "pg_catalog"."default",
                                                 "createtime" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
                                                 "createuser" varchar(255) COLLATE "pg_catalog"."default",
                                                 "updatetime" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
                                                 "updateuser" varchar(255) COLLATE "pg_catalog"."default",
                                                 "deleted" int4 NOT NULL DEFAULT 0,
                                                 CONSTRAINT "ext_plugin_interface_pk_dev_module" PRIMARY KEY ("id")
)
;


CREATE TABLE IF NOT EXISTS "ext_plugin_tree" (
                                            "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                            "extname" varchar(255) COLLATE "pg_catalog"."default",
                                            "jarname" varchar(255) COLLATE "pg_catalog"."default",
                                            "type" int4,
                                            "createtime" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
                                            "updatetime" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
                                            "createuser" varchar(255) COLLATE "pg_catalog"."default",
                                            "updateuser" varchar(255) COLLATE "pg_catalog"."default",
                                            "status" int4 DEFAULT 0,
                                            "name" varchar(255) COLLATE "pg_catalog"."default",
                                            "clazzname" varchar(255) COLLATE "pg_catalog"."default",
                                            "description" varchar(255) COLLATE "pg_catalog"."default",
                                            "checktime" timestamp(6),
                                            CONSTRAINT "ext_plugin_tree_pk_ext_plugin_interface" PRIMARY KEY ("id")
)
;



CREATE TABLE IF NOT EXISTS "kfaas_lib" (
                                      "jarname" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                      "createtime" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
                                      "updatetime" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
                                      "createuser" varchar(255) COLLATE "pg_catalog"."default",
                                      "updateuser" varchar(255) COLLATE "pg_catalog"."default",
                                      "status" int4 DEFAULT 0,
                                      CONSTRAINT "kfaas_lib_pk_ext_plugin_tree" PRIMARY KEY ("jarname")
)
;



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




CREATE TABLE IF NOT EXISTS "sys_logic_template" (
    id varchar(36) NOT NULL,
    name varchar(255) NULL,
    module_id varchar(36) NULL,
    description text NULL,
    nodes text NULL,
    links text NULL,
    app_id varchar(36) NULL,
    when_created varchar(50) NULL,
    who_created varchar(36) NULL,
    when_modified varchar(50) NULL,
    who_modified varchar(36) NULL,
    CONSTRAINT sys_logic_template_pk_ PRIMARY KEY (id)
    )
;



