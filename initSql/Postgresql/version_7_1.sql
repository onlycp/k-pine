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


COMMENT ON COLUMN "dev_module"."id" IS 'ID';

COMMENT ON COLUMN "dev_module"."name" IS '名称';

COMMENT ON COLUMN "dev_module"."path" IS '路径';

COMMENT ON COLUMN "dev_module"."has_path" IS '是否有路径';

COMMENT ON COLUMN "dev_module"."parent_id" IS '父节点';

COMMENT ON COLUMN "dev_module"."sort" IS '排序';

COMMENT ON COLUMN "dev_module"."when_created" IS '创建时间';

COMMENT ON COLUMN "dev_module"."who_created" IS '创建人';

COMMENT ON COLUMN "dev_module"."when_modified" IS '修改时间';

COMMENT ON COLUMN "dev_module"."who_modified" IS '修改人';

COMMENT ON COLUMN "dev_module"."is_sys" IS '是否系统';

COMMENT ON COLUMN "dev_module"."app_id" IS '关联应用';
    
    
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



COMMENT ON COLUMN "ext_plugin_interface"."name" IS '接口名称';

COMMENT ON COLUMN "ext_plugin_interface"."resp_type" IS '返回值类型';

COMMENT ON COLUMN "ext_plugin_interface"."content" IS '接口使用说明Demo';

COMMENT ON COLUMN "ext_plugin_interface"."description" IS '接口描述';

COMMENT ON COLUMN "ext_plugin_interface"."pluginid" IS '插件id';

COMMENT ON COLUMN "ext_plugin_interface"."deleted" IS '1:逻辑删';

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


COMMENT ON COLUMN "ext_plugin_tree"."extname" IS '插件名称';

COMMENT ON COLUMN "ext_plugin_tree"."jarname" IS 'jar包名称';

COMMENT ON COLUMN "ext_plugin_tree"."type" IS '1:一级节点；2:二级节点';

COMMENT ON COLUMN "ext_plugin_tree"."status" IS '服务器是否存在该包，0:不存在;1:存在';

COMMENT ON COLUMN "ext_plugin_tree"."name" IS '中文名';

COMMENT ON COLUMN "ext_plugin_tree"."clazzname" IS '调用插件方法名';

COMMENT ON COLUMN "ext_plugin_tree"."description" IS '描述';

COMMENT ON COLUMN "ext_plugin_tree"."checktime" IS '插件检测时间';

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


COMMENT ON COLUMN "kfaas_lib"."jarname" IS 'jar包名称';

COMMENT ON COLUMN "kfaas_lib"."status" IS '服务器是否存在该包，0:不存在;1:存在';

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
CREATE TABLE IF NOT EXISTS "sys_logic_template" (
                                               "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                               "name" varchar(255) COLLATE "pg_catalog"."default",
                                               "module_id" varchar(36) COLLATE "pg_catalog"."default",
                                               "description" text COLLATE "pg_catalog"."default",
                                               "nodes" text COLLATE "pg_catalog"."default",
                                               "links" text COLLATE "pg_catalog"."default",
                                               "app_id" varchar(36) COLLATE "pg_catalog"."default",
                                               "when_created" varchar(50) COLLATE "pg_catalog"."default",
                                               "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                               "when_modified" varchar(50) COLLATE "pg_catalog"."default",
                                               "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                               CONSTRAINT "sys_logic_template_pk_sys_i18n" PRIMARY KEY ("id")
)
;



COMMENT ON COLUMN "sys_logic_template"."id" IS 'id';

COMMENT ON COLUMN "sys_logic_template"."name" IS '名称';

COMMENT ON COLUMN "sys_logic_template"."module_id" IS '关联模块';

COMMENT ON COLUMN "sys_logic_template"."description" IS '简介';

COMMENT ON COLUMN "sys_logic_template"."nodes" IS '节点列表';

COMMENT ON COLUMN "sys_logic_template"."links" IS '连接列表';

COMMENT ON COLUMN "sys_logic_template"."app_id" IS '关联应用';

COMMENT ON COLUMN "sys_logic_template"."when_created" IS '创建时间';

COMMENT ON COLUMN "sys_logic_template"."who_created" IS '创建人';

COMMENT ON COLUMN "sys_logic_template"."when_modified" IS '修改时间';

COMMENT ON COLUMN "sys_logic_template"."who_modified" IS '修改人';