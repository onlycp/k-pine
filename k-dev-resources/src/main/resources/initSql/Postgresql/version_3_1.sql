CREATE TABLE  IF NOT EXISTS "open_account" (
                                         "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                         "access_name" varchar(128) COLLATE "pg_catalog"."default",
                                         "access_id" varchar(36) COLLATE "pg_catalog"."default",
                                         "auth_type" int4,
                                         "sign_key" varchar(50) COLLATE "pg_catalog"."default",
                                         "validate_sign" int4 DEFAULT 0,
                                         "valid_date" varchar(20) COLLATE "pg_catalog"."default",
                                         "invalid_date" varchar(20) COLLATE "pg_catalog"."default",
                                         "status" int4 DEFAULT 1,
                                         "auth_params" text COLLATE "pg_catalog"."default",
                                         "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                         "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                         "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                         "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                         CONSTRAINT "open_account_pk_dev_power_tree" PRIMARY KEY ("id")
)
;


COMMENT ON COLUMN "open_account"."id" IS '主键';

COMMENT ON COLUMN "open_account"."access_name" IS '接入者名称';

COMMENT ON COLUMN "open_account"."access_id" IS '接入者ID';

COMMENT ON COLUMN "open_account"."auth_type" IS '授权类型
1：简单模式，即access_id为access_token, 此时token是固定的';

COMMENT ON COLUMN "open_account"."sign_key" IS '签名密钥';

COMMENT ON COLUMN "open_account"."validate_sign" IS '是否验签';

COMMENT ON COLUMN "open_account"."valid_date" IS '生效日期';

COMMENT ON COLUMN "open_account"."invalid_date" IS '失效日期';

COMMENT ON COLUMN "open_account"."status" IS '是否启用
';

COMMENT ON COLUMN "open_account"."auth_params" IS '参数配置';

COMMENT ON COLUMN "open_account"."who_created" IS '创建人';

COMMENT ON COLUMN "open_account"."when_created" IS '创建时间';

COMMENT ON COLUMN "open_account"."who_modified" IS '修改人';

COMMENT ON COLUMN "open_account"."when_modified" IS '修改时间';

CREATE TABLE IF NOT EXISTS  "open_account_api" (
                                             "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                             "account_id" varchar(36) COLLATE "pg_catalog"."default",
                                             "api_id" varchar(36) COLLATE "pg_catalog"."default",
                                             "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                             "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                             CONSTRAINT "open_account_api_pk_open_account" PRIMARY KEY ("id")
)
;


COMMENT ON COLUMN "open_account_api"."id" IS 'id';

COMMENT ON COLUMN "open_account_api"."account_id" IS '账号id';

COMMENT ON COLUMN "open_account_api"."api_id" IS '接口id';

COMMENT ON COLUMN "open_account_api"."when_created" IS '创建时间';

COMMENT ON COLUMN "open_account_api"."who_created" IS '创建人';

COMMENT ON TABLE "open_account_api" IS '开放账号接口关联';

CREATE TABLE IF NOT EXISTS "dev_power_tree" (
                                           "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                           "name" varchar(128) COLLATE "pg_catalog"."default",
                                           "parent_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "note" varchar(255) COLLATE "pg_catalog"."default",
                                           "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                           "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                           "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                           "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                           "path" text COLLATE "pg_catalog"."default",
                                           CONSTRAINT "dev_power_tree_pk_dev_power_link" PRIMARY KEY ("id")
)
;


COMMENT ON COLUMN "dev_power_tree"."id" IS '主键';

COMMENT ON COLUMN "dev_power_tree"."name" IS '名称';

COMMENT ON COLUMN "dev_power_tree"."parent_id" IS '父级id';

COMMENT ON COLUMN "dev_power_tree"."note" IS '说明';

COMMENT ON COLUMN "dev_power_tree"."who_created" IS '创建人';

COMMENT ON COLUMN "dev_power_tree"."when_created" IS '创建时间';

COMMENT ON COLUMN "dev_power_tree"."who_modified" IS '修改人';

COMMENT ON COLUMN "dev_power_tree"."when_modified" IS '修改时间';

COMMENT ON COLUMN "dev_power_tree"."path" IS '树路径';

COMMENT ON TABLE "dev_power_tree" IS '开发-能力树表';


CREATE TABLE IF NOT EXISTS "dev_power_link" (
                                           "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                           "tree_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "power_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "power_type" int4,
                                           "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                           "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                           CONSTRAINT "dev_power_link_pk_" PRIMARY KEY ("id")
);


COMMENT ON COLUMN "dev_power_link"."id" IS '主键';

COMMENT ON COLUMN "dev_power_link"."tree_id" IS '能力树id';

COMMENT ON COLUMN "dev_power_link"."power_id" IS '能力id';

COMMENT ON COLUMN "dev_power_link"."power_type" IS '能力类型 1: 逻辑编排 2:函数 3:kutils 4:逻辑编排模板';

COMMENT ON COLUMN "dev_power_link"."who_created" IS '创建人';

COMMENT ON COLUMN "dev_power_link"."when_created" IS '创建时间';