CREATE TABLE IF NOT EXISTS "open_api_log" (
                                         "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                         "access_id" varchar(100) COLLATE "pg_catalog"."default",
                                         "api_name" varchar(100) COLLATE "pg_catalog"."default",
                                         "request_params" text COLLATE "pg_catalog"."default",
                                         "request_time" varchar(20) COLLATE "pg_catalog"."default",
                                         "request_ip" varchar(20) COLLATE "pg_catalog"."default",
                                         "use_time" numeric(3,0),
                                         "success" numeric(3,0),
                                         "error_message" varchar(255) COLLATE "pg_catalog"."default",
                                         CONSTRAINT "open_api_log_pk_" PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "open_api_log"."id" IS '主键';

COMMENT ON COLUMN "open_api_log"."access_id" IS '接口入商名称';

COMMENT ON COLUMN "open_api_log"."api_name" IS '接口名称';

COMMENT ON COLUMN "open_api_log"."request_params" IS '请求参数';

COMMENT ON COLUMN "open_api_log"."request_time" IS '请求时间';

COMMENT ON COLUMN "open_api_log"."request_ip" IS '请求IP';

COMMENT ON COLUMN "open_api_log"."use_time" IS '响应时间(秒)';

COMMENT ON COLUMN "open_api_log"."success" IS '是否成功';

COMMENT ON COLUMN "open_api_log"."error_message" IS '错误信息';