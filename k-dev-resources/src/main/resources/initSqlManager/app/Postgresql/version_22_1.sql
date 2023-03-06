CREATE TABLE "sys_user_unit" (
                                 "id" VARCHAR(36) NOT NULL,
                                 "sys_user_id" VARCHAR(36) NOT NULL,
                                 "sys_unit_id" VARCHAR(36) NOT NULL,
                                 "who_created" VARCHAR(36) NOT NULL,
                                 "when_created" VARCHAR(20) NOT NULL,
                                 "app_id" VARCHAR(36),
                                 PRIMARY KEY ("id")
);
COMMENT ON COLUMN "sys_user_unit"."id" IS '主键';
COMMENT ON COLUMN "sys_user_unit"."sys_user_id" IS '用户ID';
COMMENT ON COLUMN "sys_user_unit"."sys_unit_id" IS '部门ID';
COMMENT ON COLUMN "sys_user_unit"."who_created" IS '创建人员';
COMMENT ON COLUMN "sys_user_unit"."when_created" IS '创建时间';
COMMENT ON COLUMN "sys_user_unit"."app_id" IS '关联应用';

CREATE TABLE sys_search_config (
                                   id varchar(36)  NOT NULL,
                                   data_source varchar(100) ,
                                   table_name varchar(100),
                                   columns varchar(255),
                                   primary_columns varchar(255),
                                   link varchar(255) ,
                                   labels varchar(255) ,
                                   when_created varchar(50) ,
                                   when_modified varchar(50) ,
                                   who_created varchar(36) ,
                                   who_modified varchar(36) ,
                                   title_column varchar(100) ,
                                   PRIMARY KEY (id)
) ;

alter table sys_operate_log add method varchar(255);
alter table sys_operate_log add request_method varchar(20);
alter table sys_operate_log add response_body text;