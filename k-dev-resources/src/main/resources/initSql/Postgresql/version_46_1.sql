CREATE TABLE if not exists "biz_demo_user" (
      "id" varchar(36)  NOT NULL,
      "age" int4,
      "birthday" varchar(20) ,
      "id_card" varchar(20) ,
      "name" varchar(100) ,
      "when_created" varchar(20) ,
      "who_created" varchar(36) ,
      PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists "dev_pine_plugin" (
        "id" varchar(36)  NOT NULL,
        "app_id" varchar(36) ,
        "author" varchar(50) ,
        "enable_status" int4 DEFAULT 0,
        "file_id" varchar(36) ,
        "note" text ,
        "plugin_name" varchar(50) ,
        "plugin_version" varchar(10) ,
        "when_created" varchar(36) ,
        "when_modified" varchar(20) ,
        "who_created" varchar(36) ,
        "who_modified" varchar(36) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "dev_pine_plugin"."app_id" IS '归属应用id';

COMMENT ON COLUMN "dev_pine_plugin"."author" IS '插件作者';

COMMENT ON COLUMN "dev_pine_plugin"."enable_status" IS '是否启动';

COMMENT ON COLUMN "dev_pine_plugin"."file_id" IS '文件id';

COMMENT ON COLUMN "dev_pine_plugin"."note" IS '说明';

COMMENT ON COLUMN "dev_pine_plugin"."plugin_name" IS '插件名称';

COMMENT ON COLUMN "dev_pine_plugin"."plugin_version" IS '插件版本号';

COMMENT ON COLUMN "dev_pine_plugin"."when_created" IS '创建时间';

COMMENT ON COLUMN "dev_pine_plugin"."when_modified" IS '修改时间';

COMMENT ON COLUMN "dev_pine_plugin"."who_created" IS '创建人';

COMMENT ON COLUMN "dev_pine_plugin"."who_modified" IS '修改人员';

CREATE TABLE if not exists "dev_table" (
          "id" varchar(36)  NOT NULL,
          "data_source" varchar(100) ,
          "name" varchar(100) ,
          "comment" varchar(255) ,
          "when_created" varchar(100) ,
          "when_modified" varchar(100) ,
          "who_created" varchar(100) ,
          "who_modified" varchar(100) ,
          PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists "dev_table_column" (
         "id" varchar(36)  NOT NULL,
         "table_id" varchar(36) ,
         "name" varchar(100) ,
         "comment" varchar(255) ,
         "sort" int4,
         "type" varchar(50) ,
         "length" varchar(36) ,
         "decimal_point" varchar(100) ,
         "is_primary" int4,
         "is_null" int4,
         "when_created" varchar(100) ,
         "when_modified" varchar(100) ,
         "who_created" varchar(100) ,
         "who_modified" varchar(100) ,
         "is_label_column" int4 NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "dev_table_column"."is_label_column" IS '是否为显示字段（用于迁移选择数据时）';

CREATE TABLE if not exists "dev_table_update_log" (
         "id" varchar(36)  NOT NULL,
         "operate_type" int4,
         "table_name" varchar(100) ,
         "column_name" varchar(100) ,
         "version_tag" varchar(100) ,
         "when_created" varchar(100) ,
         "when_modified" varchar(100) ,
         "who_created" varchar(100) ,
         "who_modified" varchar(100) ,
         "old_table_name" varchar(100) ,
         "old_column_name" varchar(100) ,
         "type" varchar(36) ,
         "length" varchar(36) ,
         "decimal_point" int4,
         "is_primary" int4,
         "is_null" int4,
         "sort" varchar(36) ,
         "comment" varchar(255) ,
         "table_id" varchar(100) ,
         "is_column_label" int4,
         PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists "number_regulation" (
          "id" varchar(36)  NOT NULL,
          "sort" varchar(255) ,
          "type" varchar(255) ,
          "value" varchar(255) ,
          PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "number_regulation"."sort" IS '排序';

COMMENT ON COLUMN "number_regulation"."type" IS '规则类型';

COMMENT ON COLUMN "number_regulation"."value" IS '规则值';

CREATE TABLE if not exists "number_serial" (
          "id" varchar(36)  NOT NULL,
          "create_time" varchar(255) ,
          "name" varchar(255) ,
          "number" varchar(255) ,
          "regulationId" varchar(64) ,
          "remark" varchar(255) ,
          "start_value" varchar(255) ,
          "step_value" varchar(255) ,
          "update_time" varchar(255) ,
          PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "number_serial"."create_time" IS '创建时间';

COMMENT ON COLUMN "number_serial"."name" IS '流水号名称';

COMMENT ON COLUMN "number_serial"."number" IS '流水号编号';

COMMENT ON COLUMN "number_serial"."regulationId" IS '规则ID';

COMMENT ON COLUMN "number_serial"."remark" IS '备注';

COMMENT ON COLUMN "number_serial"."start_value" IS '开始值';

COMMENT ON COLUMN "number_serial"."step_value" IS '步长值';

COMMENT ON COLUMN "number_serial"."update_time" IS '更新时间';

CREATE TABLE if not exists "rep_app" (
        "id" varchar(36)  NOT NULL,
        "app_name" varchar(50) ,
        "app_note" varchar(256) ,
        "when_created" varchar(20) ,
        "when_modified" varchar(20) ,
        "who_created" varchar(36) ,
        "who_modified" varchar(36) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "rep_app"."app_name" IS '报表名称';

COMMENT ON COLUMN "rep_app"."app_note" IS '报表描述';

COMMENT ON COLUMN "rep_app"."when_created" IS '创建时间';

COMMENT ON COLUMN "rep_app"."when_modified" IS '修改时间';

COMMENT ON COLUMN "rep_app"."who_created" IS '创建人';

COMMENT ON COLUMN "rep_app"."who_modified" IS '更新人';

CREATE TABLE if not exists "rep_dataset" (
        "id" varchar(36)  NOT NULL,
        "column_def" text ,
        "ds_meta" text ,
        "ds_name" varchar(50) ,
        "ds_note" varchar(255) ,
        "ds_type" int4,
        "rep_app_id" varchar(36) ,
        "when_created" varchar(20) ,
        "when_modified" varchar(20) ,
        "who_created" varchar(36) ,
        "who_modified" varchar(36) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "rep_dataset"."column_def" IS '列定义';

COMMENT ON COLUMN "rep_dataset"."ds_meta" IS '配置信息';

COMMENT ON COLUMN "rep_dataset"."ds_name" IS '数据集名称';

COMMENT ON COLUMN "rep_dataset"."ds_note" IS '描述信息';

COMMENT ON COLUMN "rep_dataset"."ds_type" IS '数据集类型 1：sql 2:json 3:excel';

COMMENT ON COLUMN "rep_dataset"."rep_app_id" IS '报表应用id';

COMMENT ON COLUMN "rep_dataset"."when_created" IS '创建时间';

COMMENT ON COLUMN "rep_dataset"."when_modified" IS '修改时间';

COMMENT ON COLUMN "rep_dataset"."who_created" IS '创建人员';

COMMENT ON COLUMN "rep_dataset"."who_modified" IS '修改人员';

CREATE TABLE if not exists "sys_auto_serial" (
        "id" varchar(36)  NOT NULL,
        "auto_num" int4,
        "category" varchar(100) ,
        "create_time" varchar(20) ,
        "create_user" varchar(50) ,
        "key" varchar(50) ,
        "locked" int4 DEFAULT 0,
        "num_length" int4,
        "start_num" int4,
        "step" int4,
        "tpl" varchar(100) ,
        "type" int4,
        "update_time" varchar(20) ,
        "update_user" varchar(50) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "sys_auto_serial"."auto_num" IS '当前编号';

COMMENT ON COLUMN "sys_auto_serial"."category" IS '分类';

COMMENT ON COLUMN "sys_auto_serial"."key" IS '计算方式key';

COMMENT ON COLUMN "sys_auto_serial"."locked" IS '是否被锁 1：已锁 0：未锁';

COMMENT ON COLUMN "sys_auto_serial"."num_length" IS '编号长度，不够前面补0';

COMMENT ON COLUMN "sys_auto_serial"."start_num" IS '初始值';

COMMENT ON COLUMN "sys_auto_serial"."step" IS '步长';

COMMENT ON COLUMN "sys_auto_serial"."tpl" IS '模板';

COMMENT ON COLUMN "sys_auto_serial"."type" IS '计算方式1: 按日 2：按月 3:按年';

CREATE TABLE if not exists "sys_data_change" (
        "id" varchar(32)  NOT NULL,
        "name" varchar(255) ,
        "table_name" varchar(255) ,
        "object_name" varchar(255) ,
        "operator" varchar(255) ,
        "oper_type" varchar(255) ,
        "oper_time" varchar(255) ,
        "content" varchar(255) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "sys_data_change"."id" IS '主键';

COMMENT ON COLUMN "sys_data_change"."name" IS '模块名';

COMMENT ON COLUMN "sys_data_change"."table_name" IS '表名';

COMMENT ON COLUMN "sys_data_change"."object_name" IS '对象名称';

COMMENT ON COLUMN "sys_data_change"."operator" IS '操作人员';

COMMENT ON COLUMN "sys_data_change"."oper_type" IS '操作类型';

COMMENT ON COLUMN "sys_data_change"."oper_time" IS '操作时间';

COMMENT ON COLUMN "sys_data_change"."content" IS '变更内容';

COMMENT ON TABLE "sys_data_change" IS '数据变更记录';

CREATE TABLE if not exists "sys_excel" (
          "id" varchar(36)  NOT NULL,
          "app_id" varchar(36) ,
          "data_from" int4,
          "data_from_id" varchar(36) ,
          "data_json" text ,
          "name" varchar(255) ,
          "when_created" varchar(30) ,
          "when_modified" varchar(30) ,
          "who_created" varchar(36) ,
          "who_modified" varchar(36) ,
          PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "sys_excel"."app_id" IS '关联应用';

COMMENT ON COLUMN "sys_excel"."data_from" IS '1. 普通 2. Excel文件 3. 数据源';

COMMENT ON COLUMN "sys_excel"."data_from_id" IS '数据来源ID：如果data_from为2，则为fileId；如果为3，则为datasetId';

COMMENT ON COLUMN "sys_excel"."data_json" IS '数据配置';

COMMENT ON COLUMN "sys_excel"."name" IS '名称';

COMMENT ON COLUMN "sys_excel"."when_created" IS '创建时间';

COMMENT ON COLUMN "sys_excel"."when_modified" IS '修改时间';

COMMENT ON COLUMN "sys_excel"."who_created" IS '创建人';

COMMENT ON COLUMN "sys_excel"."who_modified" IS '修改人';

CREATE TABLE if not exists "sys_hint_select" (
        "id" varchar(36)  NOT NULL,
        "code" varchar(100) ,
        "db_id" varchar(100) ,
        "flow_id" varchar(50) ,
        "remark" text ,
        "select_fields" text ,
        "select_sql" text ,
        "type" varchar(50) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "sys_hint_select"."code" IS '简称';

COMMENT ON COLUMN "sys_hint_select"."db_id" IS '数据源';

COMMENT ON COLUMN "sys_hint_select"."flow_id" IS '逻辑编排,优化级最高';

COMMENT ON COLUMN "sys_hint_select"."remark" IS '备注说明';

COMMENT ON COLUMN "sys_hint_select"."select_fields" IS '字段说明';

COMMENT ON COLUMN "sys_hint_select"."select_sql" IS '下拉SQL';

COMMENT ON COLUMN "sys_hint_select"."type" IS '类型:0=普通下拉,1=树下拉';

CREATE TABLE if not exists "sys_logic_flow_mock" (
        "id" varchar(36)  NOT NULL,
        "name" varchar(90) ,
        "flow_id" varchar(36) ,
        "depend_id" varchar(36) ,
        "request_argv" text ,
        "assert_expr" varchar(900) ,
        "enable_mock" int4,
        "who_created" varchar(36) ,
        "when_created" varchar(20) ,
        "who_modified" varchar(36) ,
        "when_modified" varchar(20) ,
        PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "sys_logic_flow_mock"."id" IS '主键';

COMMENT ON COLUMN "sys_logic_flow_mock"."name" IS '名称';

COMMENT ON COLUMN "sys_logic_flow_mock"."flow_id" IS '逻辑编排id';

COMMENT ON COLUMN "sys_logic_flow_mock"."depend_id" IS '依赖mockId';

COMMENT ON COLUMN "sys_logic_flow_mock"."request_argv" IS '请求参数';

COMMENT ON COLUMN "sys_logic_flow_mock"."assert_expr" IS '表达式';

COMMENT ON COLUMN "sys_logic_flow_mock"."enable_mock" IS '是否启用';

COMMENT ON COLUMN "sys_logic_flow_mock"."who_created" IS '创建人';

COMMENT ON COLUMN "sys_logic_flow_mock"."when_created" IS '创建时间';

COMMENT ON COLUMN "sys_logic_flow_mock"."who_modified" IS '修改人员';

COMMENT ON COLUMN "sys_logic_flow_mock"."when_modified" IS '修改时间';

COMMENT ON TABLE "sys_logic_flow_mock" IS '逻辑编排mock';

CREATE TABLE if not exists "sys_mq_channel" (
       "id" varchar(36)  NOT NULL,
       "batch_consumer" int4,
       "channel_name" varchar(50) ,
       "consumer_group" varchar(50) ,
       "consumer_thread" int4,
       "enable" int4,
       "message_name" varchar(50) ,
       "topic" varchar(50) ,
       "zk_address" varchar(50) ,
       PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "sys_mq_channel"."batch_consumer" IS '批量消费数';

COMMENT ON COLUMN "sys_mq_channel"."channel_name" IS '通道名称';

COMMENT ON COLUMN "sys_mq_channel"."consumer_group" IS '消费组名';

COMMENT ON COLUMN "sys_mq_channel"."consumer_thread" IS '消费线程数';

COMMENT ON COLUMN "sys_mq_channel"."enable" IS '是否启用 0-否 1-是';

COMMENT ON COLUMN "sys_mq_channel"."message_name" IS '消息处理逻辑编排';

COMMENT ON COLUMN "sys_mq_channel"."topic" IS '主题';

COMMENT ON COLUMN "sys_mq_channel"."zk_address" IS 'zk地址';

CREATE TABLE if not exists "wf_demo_leave" (
                                          "id" varchar(36)  NOT NULL,
                                          "day" int4,
                                          "proc_inst_id" varchar(36) ,
                                          "reason" varchar(255) ,
                                          PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "wf_demo_leave"."day" IS '请假天数';

COMMENT ON COLUMN "wf_demo_leave"."proc_inst_id" IS '实例id';

COMMENT ON COLUMN "wf_demo_leave"."reason" IS '请假原因';

ALTER TABLE "dev_ota_channel" ADD PRIMARY KEY ("id");

ALTER TABLE "kfaas_lib" ADD COLUMN "id" varchar(36)  NOT NULL;

ALTER TABLE "kfaas_lib" ADD COLUMN "create_time" varchar(20) ;

ALTER TABLE "kfaas_lib" ADD COLUMN "create_user" varchar(255) ;

ALTER TABLE "kfaas_lib" ADD COLUMN "jar_name" varchar(255) ;

ALTER TABLE "kfaas_lib" ADD COLUMN "update_time" varchar(20) ;

ALTER TABLE "kfaas_lib" ADD COLUMN "update_user" varchar(255) ;

COMMENT ON COLUMN "kfaas_lib"."jar_name" IS 'jar包名称';

COMMENT ON COLUMN "kfaas_lib"."status" IS '服务器是否存在该包，0:不存在;1:存在';

ALTER TABLE "sys_unit" ADD COLUMN "unit_level" int4;

ALTER TABLE "sys_unit" ADD COLUMN "required_unit" int4 DEFAULT 0;






















