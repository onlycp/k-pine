
-- 以下是来自 version_1_1.sql 的内容 --

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


-- 以下是来自 version_2_1.sql 的内容 --


-- ----------------------------
-- Table structure for dev_api
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_api" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "api_name" varchar(50) COLLATE "pg_catalog"."default",
    "application_id" varchar(36) COLLATE "pg_catalog"."default",
    "api_url" varchar(128) COLLATE "pg_catalog"."default",
    "api_note" text COLLATE "pg_catalog"."default",
    "api_tags" varchar(128) COLLATE "pg_catalog"."default",
    "api_method" varchar(36) COLLATE "pg_catalog"."default" DEFAULT 'get'::character varying,
    "api_argv_type" int8,
    "api_req_argv" text COLLATE "pg_catalog"."default",
    "api_rsp_argv" text COLLATE "pg_catalog"."default",
    "api_result_handler" varchar(128) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "api_flow_id" varchar(50) COLLATE "pg_catalog"."default",
    "api_code" varchar(50) COLLATE "pg_catalog"."default",
    "call_type" int8,
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_application
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_application" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(100) COLLATE "pg_catalog"."default",
    "short_name" varchar(30) COLLATE "pg_catalog"."default",
    "description" varchar(255) COLLATE "pg_catalog"."default",
    "enable_status" int8,
    "dev_status" int8,
    "version" varchar(50) COLLATE "pg_catalog"."default",
    "who_in_charge" varchar(255) COLLATE "pg_catalog"."default",
    "system_logo" varchar(255) COLLATE "pg_catalog"."default",
    "app_type" varchar(100) COLLATE "pg_catalog"."default",
    "default_path" varchar(255) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "faas_port" int8,
    "pine_port" int8,
    "data_source" varchar(1000) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_application_version_history
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_application_version_history" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default",
    "version" varchar(50) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_document
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_document" (
    "id" varchar(255) COLLATE "pg_catalog"."default",
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "path" varchar(255) COLLATE "pg_catalog"."default",
    "content" text COLLATE "pg_catalog"."default",
    "parent_id" varchar(255) COLLATE "pg_catalog"."default",
    "order" int8 NOT NULL,
    "when_created" date,
    "who_created" varchar(255) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint
    )
;

-- ----------------------------
-- Table structure for dev_page
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_page" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "app_id" varchar(36) COLLATE "pg_catalog"."default",
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "description" varchar(255) COLLATE "pg_catalog"."default",
    "path" varchar(255) COLLATE "pg_catalog"."default",
    "app_type" varchar(100) COLLATE "pg_catalog"."default",
    "login_required" int8,
    "enable_status" int8,
    "dev_status" int8,
    "page_json" text COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_page_history
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_page_history" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "page_id" varchar(36) COLLATE "pg_catalog"."default",
    "page_json" text COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default"
    )
;

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

-- ----------------------------
-- Table structure for dev_sql_script
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_sql_script" (
                                                "sql" text COLLATE "pg_catalog"."default",
                                                "description" varchar(255) COLLATE "pg_catalog"."default",
    "version" int8 NOT NULL,
    "is_once" int8 DEFAULT '1'::bigint
    )
;

-- ----------------------------
-- Table structure for dev_team
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_team" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(100) COLLATE "pg_catalog"."default",
    "owner" varchar(36) COLLATE "pg_catalog"."default",
    "description" varchar(255) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_team_app
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_team_app" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "team_id" varchar(36) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default",
    "team_type" int8,
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_team_member
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_team_member" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "team_id" varchar(36) COLLATE "pg_catalog"."default",
    "user_id" varchar(36) COLLATE "pg_catalog"."default",
    "when_join" varchar(20) COLLATE "pg_catalog"."default",
    "who_invite" varchar(36) COLLATE "pg_catalog"."default",
    "team_role_id" varchar(36) COLLATE "pg_catalog"."default",
    "is_owner" int8,
    "app_id" varchar(255) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_topological
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_topological" (
    "id" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "app_id" varchar(36) COLLATE "pg_catalog"."default",
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "description" varchar(255) COLLATE "pg_catalog"."default",
    "page_json" text COLLATE "pg_catalog"."default",
    "enable_status" int8
    )
;

-- ----------------------------
-- Table structure for dev_view_model
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_view_model" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "tag" varchar(100) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_view_model_field
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_view_model_field" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "view_model_id" varchar(36) COLLATE "pg_catalog"."default",
    "field" varchar(50) COLLATE "pg_catalog"."default",
    "label" varchar(50) COLLATE "pg_catalog"."default",
    "type" varchar(20) COLLATE "pg_catalog"."default",
    "format_type" varchar(20) COLLATE "pg_catalog"."default",
    "format_pattern" varchar(50) COLLATE "pg_catalog"."default",
    "default_text" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "hidden" int8 DEFAULT '0'::bigint,
    "order_num" int8 DEFAULT '0'::bigint,
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for dev_view_model_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_view_model_flow" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "flow_id" varchar(36) COLLATE "pg_catalog"."default",
    "view_model_id" varchar(36) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for ext_plugin_interface
-- ----------------------------
CREATE TABLE IF NOT EXISTS "ext_plugin_interface" (
    "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "resp_type" varchar(255) COLLATE "pg_catalog"."default",
    "content" text COLLATE "pg_catalog"."default",
    "description" varchar(1024) COLLATE "pg_catalog"."default",
    "pluginId" varchar(255) COLLATE "pg_catalog"."default",
    "createTime" timestamp(6) DEFAULT now(),
    "createUser" varchar(255) COLLATE "pg_catalog"."default",
    "updateTime" timestamp(6) DEFAULT now(),
    "updateuser" varchar(255) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for ext_plugin_tree
-- ----------------------------
CREATE TABLE IF NOT EXISTS "ext_plugin_tree" (
    "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "extName" varchar(255) COLLATE "pg_catalog"."default",
    "jarName" varchar(255) COLLATE "pg_catalog"."default",
    "type" int8,
    "createTime" timestamp(6) DEFAULT now(),
    "updateTime" timestamp(6) DEFAULT now(),
    "createUser" varchar(255) COLLATE "pg_catalog"."default",
    "updateUser" varchar(255) COLLATE "pg_catalog"."default",
    "status" int8 DEFAULT '0'::bigint,
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "description" varchar(255) COLLATE "pg_catalog"."default",
    "checkTime" timestamp(6) DEFAULT now()
    )
;
-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_api" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "api_name" varchar(50) COLLATE "pg_catalog"."default",
    "api_url" varchar(128) COLLATE "pg_catalog"."default",
    "api_note" text COLLATE "pg_catalog"."default",
    "api_tags" varchar(128) COLLATE "pg_catalog"."default",
    "api_method" varchar(36) COLLATE "pg_catalog"."default" DEFAULT 'get'::character varying,
    "api_argv_type" int8,
    "api_req_argv" text COLLATE "pg_catalog"."default",
    "api_rsp_argv" text COLLATE "pg_catalog"."default",
    "api_result_handler" varchar(128) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "api_flow_id" varchar(50) COLLATE "pg_catalog"."default",
    "api_code" varchar(50) COLLATE "pg_catalog"."default",
    "call_type" int8,
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_config" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "code" varchar(255) COLLATE "pg_catalog"."default",
    "value" varchar(255) COLLATE "pg_catalog"."default",
    "is_sys" int8,
    "note" varchar(255) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "value_type" int8 DEFAULT '0'::bigint,
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_access
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_access" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "status" int8,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_access_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_access_resource" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "data_id" varchar(36) COLLATE "pg_catalog"."default",
    "access_id" varchar(36) COLLATE "pg_catalog"."default",
    "table_name" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_access_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_access_user" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "sys_user_id" varchar(36) COLLATE "pg_catalog"."default",
    "sys_data_access_id" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_resource" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "table_name" varchar(50) COLLATE "pg_catalog"."default",
    "label_field" varchar(50) COLLATE "pg_catalog"."default",
    "value_field" varchar(50) COLLATE "pg_catalog"."default",
    "query_sql" text COLLATE "pg_catalog"."default",
    "is_tree" int8,
    "is_only_leaf" int8,
    "status" int8,
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "extra_sql" text COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_dict" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_dict_item" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "group_name" varchar(50) COLLATE "pg_catalog"."default",
    "sys_dict_id" varchar(36) COLLATE "pg_catalog"."default",
    "code" varchar(50) COLLATE "pg_catalog"."default",
    "value" varchar(20) COLLATE "pg_catalog"."default",
    "order_num" int8,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_file" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "file_name" varchar(100) COLLATE "pg_catalog"."default",
    "file_original_name" varchar(100) COLLATE "pg_catalog"."default",
    "file_size" int8,
    "file_ext" text COLLATE "pg_catalog"."default",
    "file_md5" varchar(40) COLLATE "pg_catalog"."default",
    "file_from" varchar(50) COLLATE "pg_catalog"."default",
    "save_type" int8,
    "file_path" varchar(100) COLLATE "pg_catalog"."default",
    "file_content" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_logic_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_logic_flow" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(255) COLLATE "pg_catalog"."default",
    "flow_id" varchar(36) COLLATE "pg_catalog"."default",
    "application_id" varchar(36) COLLATE "pg_catalog"."default",
    "tags" varchar(255) COLLATE "pg_catalog"."default",
    "in_argv" text COLLATE "pg_catalog"."default",
    "out_argv" text COLLATE "pg_catalog"."default",
    "sub_flow_ids" text COLLATE "pg_catalog"."default",
    "note" varchar(255) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_login_log" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "operate_time" varchar(20) COLLATE "pg_catalog"."default",
    "operator" varchar(36) COLLATE "pg_catalog"."default",
    "ip" varchar(20) COLLATE "pg_catalog"."default",
    "times" int8,
    "response_code" int8,
    "response_message" varchar(100) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_menu" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "parent_id" varchar(36) COLLATE "pg_catalog"."default",
    "icon" varchar(50) COLLATE "pg_catalog"."default",
    "code" varchar(50) COLLATE "pg_catalog"."default",
    "router_path" varchar(255) COLLATE "pg_catalog"."default",
    "component_path" varchar(255) COLLATE "pg_catalog"."default",
    "is_hidden" int8 DEFAULT '0'::bigint,
    "menu_type" char(1) COLLATE "pg_catalog"."default" DEFAULT '0'::bpchar,
    "api_codes" varchar(255) COLLATE "pg_catalog"."default",
    "open_mode" int8,
    "keep_alive" int8,
    "path" text COLLATE "pg_catalog"."default",
    "order_num" int8 DEFAULT '0'::bigint,
    "status" int8 DEFAULT '1'::bigint,
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default",
    "data_type" int8,
    "theme" varchar(50) COLLATE "pg_catalog"."default",
    "page_type" int8,
    "sidebar_nav_mode" int8,
    "top_nav_mode" int8,
    "main_mode" int8,
    "page_id" varchar(36) COLLATE "pg_catalog"."default",
    "full_path" varchar(255) COLLATE "pg_catalog"."default",
    "is_dev" int8 DEFAULT '0'::bigint
    )
;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_notice" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "title" varchar(255) COLLATE "pg_catalog"."default",
    "content" text COLLATE "pg_catalog"."default",
    "type" int8,
    "status" int8 DEFAULT '1'::bigint,
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "deleted" int8,
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_notice_record
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_notice_record" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "from_who" varchar(36) COLLATE "pg_catalog"."default",
    "to_who" varchar(36) COLLATE "pg_catalog"."default",
    "notice_id" varchar(36) COLLATE "pg_catalog"."default",
    "is_read" int8 DEFAULT '0'::bigint,
    "read_time" varchar(50) COLLATE "pg_catalog"."default",
    "notice_time" varchar(50) COLLATE "pg_catalog"."default",
    "title" varchar(255) COLLATE "pg_catalog"."default",
    "content" text COLLATE "pg_catalog"."default",
    "to_who_name" varchar(255) COLLATE "pg_catalog"."default",
    "from_who_name" varchar(255) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_online_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_online_user" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id" varchar(36) COLLATE "pg_catalog"."default",
    "login_time" varchar(20) COLLATE "pg_catalog"."default",
    "login_ip" varchar(20) COLLATE "pg_catalog"."default",
    "login_token" varchar(1024) COLLATE "pg_catalog"."default",
    "expire_time" varchar(20) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_operate_log" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "module" varchar(100) COLLATE "pg_catalog"."default",
    "action" varchar(255) COLLATE "pg_catalog"."default",
    "url" varchar(255) COLLATE "pg_catalog"."default",
    "operate_time" varchar(20) COLLATE "pg_catalog"."default",
    "operator" varchar(36) COLLATE "pg_catalog"."default",
    "ip" varchar(20) COLLATE "pg_catalog"."default",
    "times" int8,
    "request_body" text COLLATE "pg_catalog"."default",
    "response_code" int8,
    "response_message" varchar(100) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_role" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "note" text COLLATE "pg_catalog"."default",
    "status" int8,
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_role_menu" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "sys_menu_id" varchar(36) COLLATE "pg_catalog"."default",
    "sys_role_id" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_task" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(100) COLLATE "pg_catalog"."default",
    "cron" varchar(50) COLLATE "pg_catalog"."default",
    "distributed" int8,
    "application_id" varchar(36) COLLATE "pg_catalog"."default",
    "task_type" int8 DEFAULT '1'::bigint,
    "task_resource_id" varchar(36) COLLATE "pg_catalog"."default",
    "class_name" varchar(255) COLLATE "pg_catalog"."default",
    "enable" int8 DEFAULT '1'::bigint,
    "last_execute_status" int8,
    "last_execute_msg" text COLLATE "pg_catalog"."default",
    "last_execute_time" varchar(20) COLLATE "pg_catalog"."default",
    "last_execute_take" int8,
    "lock_status" int8 DEFAULT '0'::bigint,
    "lock_for_most" int8 DEFAULT '30'::bigint,
    "lock_for_least" int8 DEFAULT '1'::bigint,
    "lock_for_time" varchar(20) COLLATE "pg_catalog"."default",
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;
-- ----------------------------
-- Table structure for sys_unit
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_unit" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "parent_id" varchar(36) COLLATE "pg_catalog"."default",
    "path" text COLLATE "pg_catalog"."default",
    "leader" varchar(255) COLLATE "pg_catalog"."default",
    "mobile" varchar(20) COLLATE "pg_catalog"."default",
    "email" varchar(50) COLLATE "pg_catalog"."default",
    "status" int8 DEFAULT '1'::bigint,
    "note" text COLLATE "pg_catalog"."default",
    "order_num" int8 DEFAULT '0'::bigint,
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_user" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "password" varchar(256) COLLATE "pg_catalog"."default",
    "real_name" varchar(50) COLLATE "pg_catalog"."default",
    "mobile" varchar(20) COLLATE "pg_catalog"."default",
    "email" varchar(50) COLLATE "pg_catalog"."default",
    "sex" int8,
    "sys_unit_id" varchar(36) COLLATE "pg_catalog"."default",
    "post" varchar(50) COLLATE "pg_catalog"."default",
    "status" int8 DEFAULT '1'::bigint,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "avatar" varchar(255) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_user_role" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "sys_user_id" varchar(36) COLLATE "pg_catalog"."default",
    "sys_role_id" varchar(36) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_view_model
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_view_model" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "tag" varchar(100) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_view_model_field
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_view_model_field" (
    "id" varchar(36) COLLATE "pg_catalog"."default",
    "view_model_id" varchar(36) COLLATE "pg_catalog"."default",
    "field" varchar(50) COLLATE "pg_catalog"."default",
    "label" varchar(50) COLLATE "pg_catalog"."default",
    "type" varchar(20) COLLATE "pg_catalog"."default",
    "format_type" varchar(20) COLLATE "pg_catalog"."default",
    "format_pattern" varchar(50) COLLATE "pg_catalog"."default",
    "default_text" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "hidden" int8 DEFAULT '0'::bigint,
    "order_num" int8 DEFAULT '0'::bigint
    )
;

-- ----------------------------
-- Table structure for sys_view_model_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_view_model_flow" (
    "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "flow_id" varchar(36) COLLATE "pg_catalog"."default",
    "view_model_id" varchar(36) COLLATE "pg_catalog"."default",
    "who_created" varchar(36) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(36) COLLATE "pg_catalog"."default",
    "when_modified" timestamp(6)
    )
;

-- ----------------------------
-- Primary Key structure for table dev_api
-- ----------------------------
ALTER TABLE "dev_api" ADD CONSTRAINT "pk_dev_api" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_application
-- ----------------------------
ALTER TABLE "dev_application" ADD CONSTRAINT "pk_dev_application" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_application_version_history
-- ----------------------------
ALTER TABLE "dev_application_version_history" ADD CONSTRAINT "pk_dev_application_version_history" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_document
-- ----------------------------
ALTER TABLE "dev_document" ADD CONSTRAINT "pk_dev_document" PRIMARY KEY ("order");

-- ----------------------------
-- Primary Key structure for table dev_page
-- ----------------------------
ALTER TABLE "dev_page" ADD CONSTRAINT "pk_dev_page" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_page_history
-- ----------------------------
ALTER TABLE "dev_page_history" ADD CONSTRAINT "pk_dev_page_history" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_sql_run
-- ----------------------------
ALTER TABLE "dev_sql_run" ADD CONSTRAINT "pk_dev_sql_run" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_sql_script
-- ----------------------------
ALTER TABLE "dev_sql_script" ADD CONSTRAINT "pk_dev_sql_script" PRIMARY KEY ("version");

-- ----------------------------
-- Primary Key structure for table dev_team
-- ----------------------------
ALTER TABLE "dev_team" ADD CONSTRAINT "pk_dev_team" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_team_app
-- ----------------------------
ALTER TABLE "dev_team_app" ADD CONSTRAINT "pk_dev_team_app" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table dev_team_member
-- ----------------------------
CREATE UNIQUE INDEX "uni_user_team_app_dev_team_member" ON "dev_team_member" USING btree (
    "team_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "app_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table dev_team_member
-- ----------------------------
ALTER TABLE "dev_team_member" ADD CONSTRAINT "pk_dev_team_member" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_view_model
-- ----------------------------
ALTER TABLE "dev_view_model" ADD CONSTRAINT "pk_dev_view_model" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_view_model_field
-- ----------------------------
ALTER TABLE "dev_view_model_field" ADD CONSTRAINT "pk_dev_view_model_field" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table dev_view_model_flow
-- ----------------------------
ALTER TABLE "dev_view_model_flow" ADD CONSTRAINT "pk_dev_view_model_flow" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ext_plugin_interface
-- ----------------------------
ALTER TABLE "ext_plugin_interface" ADD CONSTRAINT "pk_ext_plugin_interface" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ext_plugin_tree
-- ----------------------------
ALTER TABLE "ext_plugin_tree" ADD CONSTRAINT "pk_ext_plugin_tree" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_api
-- ----------------------------
ALTER TABLE "sys_api" ADD CONSTRAINT "pk_sys_api" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "sys_config" ADD CONSTRAINT "pk_sys_config" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_data_access
-- ----------------------------
ALTER TABLE "sys_data_access" ADD CONSTRAINT "pk_sys_data_access" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_data_access_resource
-- ----------------------------
CREATE INDEX "fk_sys_data_access_group_access_sys_data_access_resource" ON "sys_data_access_resource" USING btree (
    "access_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_data_access_resource
-- ----------------------------
ALTER TABLE "sys_data_access_resource" ADD CONSTRAINT "pk_sys_data_access_resource" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_data_access_user
-- ----------------------------
CREATE INDEX "fk_sys_data_access_group_group_id_sys_data_access_user" ON "sys_data_access_user" USING btree (
    "sys_data_access_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "fk_sys_data_access_group_user_sys_data_access_user" ON "sys_data_access_user" USING btree (
    "sys_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_data_access_user
-- ----------------------------
ALTER TABLE "sys_data_access_user" ADD CONSTRAINT "pk_sys_data_access_user" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_data_resource
-- ----------------------------
ALTER TABLE "sys_data_resource" ADD CONSTRAINT "pk_sys_data_resource" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict
-- ----------------------------
CREATE UNIQUE INDEX "idx_code_uindex_sys_dict" ON "sys_dict" USING btree (
    "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_name_uindex_sys_dict" ON "sys_dict" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "sys_dict" ADD CONSTRAINT "pk_sys_dict" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_item
-- ----------------------------
CREATE UNIQUE INDEX "idx_sys_dict_id_name_uindex_sys_dict_item" ON "sys_dict_item" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sys_dict_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_sys_dict_id_value_uindex_sys_dict_item" ON "sys_dict_item" USING btree (
    "sys_dict_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "value" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_dict_item
-- ----------------------------
ALTER TABLE "sys_dict_item" ADD CONSTRAINT "pk_sys_dict_item" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_file
-- ----------------------------
ALTER TABLE "sys_file" ADD CONSTRAINT "pk_sys_file" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_logic_flow
-- ----------------------------
ALTER TABLE "sys_logic_flow" ADD CONSTRAINT "pk_sys_logic_flow" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_login_log
-- ----------------------------
ALTER TABLE "sys_login_log" ADD CONSTRAINT "pk_sys_login_log" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "sys_menu" ADD CONSTRAINT "pk_sys_menu" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_notice
-- ----------------------------
ALTER TABLE "sys_notice" ADD CONSTRAINT "pk_sys_notice" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_notice_record
-- ----------------------------
ALTER TABLE "sys_notice_record" ADD CONSTRAINT "pk_sys_notice_record" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_online_user
-- ----------------------------
ALTER TABLE "sys_online_user" ADD CONSTRAINT "pk_sys_online_user" PRIMARY KEY ("id");


-- ----------------------------
-- Primary Key structure for table sys_operate_log
-- ----------------------------
ALTER TABLE "sys_operate_log" ADD CONSTRAINT "pk_sys_operate_log" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------
CREATE UNIQUE INDEX "idx_coce_uindex_sys_role" ON "sys_role" USING btree (
    "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_name_uindex_sys_role" ON "sys_role" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "sys_role" ADD CONSTRAINT "pk_sys_role" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role_menu
-- ----------------------------
CREATE INDEX "fk_sys_role_sys_role_menu" ON "sys_role_menu" USING btree (
    "sys_role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_sys_role_menu_uindex_sys_role_menu" ON "sys_role_menu" USING btree (
    "sys_menu_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sys_role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "sys_role_menu" ADD CONSTRAINT "pk_sys_role_menu" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_task
-- ----------------------------
ALTER TABLE "sys_task" ADD CONSTRAINT "pk_sys_task" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_unit
-- ----------------------------
ALTER TABLE "sys_unit" ADD CONSTRAINT "pk_sys_unit" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE INDEX "fk_sys_unit_id_sys_user" ON "sys_user" USING btree (
    "sys_unit_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_username_uindex_sys_user" ON "sys_user" USING btree (
    "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "sys_user" ADD CONSTRAINT "pk_sys_user" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user_role
-- ----------------------------
CREATE INDEX "fk_sys_role_key_sys_user_role" ON "sys_user_role" USING btree (
    "sys_role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "fk_sys_user_role_user_sys_user_role" ON "sys_user_role" USING btree (
    "sys_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_sys_user_role_uindex_sys_user_role" ON "sys_user_role" USING btree (
    "sys_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sys_role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "sys_user_role" ADD CONSTRAINT "pk_sys_user_role" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_view_model
-- ----------------------------
ALTER TABLE "sys_view_model" ADD CONSTRAINT "pk_sys_view_model" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_view_model_flow
-- ----------------------------
ALTER TABLE "sys_view_model_flow" ADD CONSTRAINT "pk_sys_view_model_flow" PRIMARY KEY ("id");



INSERT INTO sys_user (id, app_id, avatar, deleted, email, mobile, note, password, post, real_name, sex, status, when_created, when_modified, who_created, who_modified, sys_unit_id, username) VALUES('8116f0bc8222413fb72de98a32960b1a', NULL, NULL, 0, NULL, NULL, NULL, 'MTIzNDU2', NULL, '开发人员', 1, 1, '2022-09-28 14:45:53', '2022-09-28 14:45:53', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa', NULL, 'dev');
INSERT INTO sys_user (id, username, password, real_name, mobile, email, sex, sys_unit_id, post, status, note, who_created, when_created, who_modified, when_modified, deleted, avatar, app_id) VALUES ('056fb0eeb9a44cb0953534b4c0ca01fa', 'admin', 'MTIzNDU2', '超级管理员', NULL, NULL, 1, NULL, NULL, 1, NULL, '', '2021-12-29 16:36:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:58:32', 0, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL);
INSERT INTO sys_user_role (id, app_id, when_created, who_created, sys_role_id, sys_user_id) VALUES('8d641b3aded845feae88aef3d7e32e33', NULL, '2022-09-28 14:45:53', '056fb0eeb9a44cb0953534b4c0ca01fa', '4a30f4d346074b4ba8363944f004c1d9', '8116f0bc8222413fb72de98a32960b1a');
INSERT INTO sys_role
(id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id)
VALUES('3fc43c9c69f44144bd032d9451ba328b', '团队成员', 'team_member', '青松开发者平台-团队成员', 1, '', '2022-03-10 06:13:01', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:59', NULL);
INSERT INTO sys_role
(id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id)
VALUES('4a30f4d346074b4ba8363944f004c1d9', '团队负责人', 'team_owner', '青松开发者平台-团队负责人', 1, '', '2022-03-10 06:12:31', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:44:06', NULL);
INSERT INTO sys_role (id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id) VALUES ('10d26189026a4dba86a8e63a4c717ed6', '超级管理员', 'admin', '应用超级管理员', 1, '', '2021-12-28 15:22:56', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:41', NULL);
INSERT INTO sys_user_role (id, sys_user_id, sys_role_id, who_created, when_created, app_id) VALUES ('b5f79a7cc794423e843a2b1fd9a27007', '056fb0eeb9a44cb0953534b4c0ca01fa', '10d26189026a4dba86a8e63a4c717ed6', '', '2022-03-10 06:31:39', NULL);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('c2348bbf343a47b5852f12ee32869b13', '基础信息', NULL, 'documentation', 'system-info', 'sys/info', NULL, 0, 'M', '', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info', 0);INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('1a246429b14e4db2be0e1847a3939e98', '菜单管理', '843af15ab7694d54af793e4a5e6fb76e', 'nested', 'menu', 'menu', '/sys/menu/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/1a246429b14e4db2be0e1847a3939e98/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/menu', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('843af15ab7694d54af793e4a5e6fb76e', '系统配置', NULL, 'system', 'system-config', 'sys/conf', NULL, 0, 'M', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('58f5f98c57c74a91b6c2ca24c5df0ba9', '用户管理', 'c2348bbf343a47b5852f12ee32869b13', 'user', 'user', 'user/index', '/sys/user/index', 0, 'C', 'sys:user:list', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/58f5f98c57c74a91b6c2ca24c5df0ba9/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:28:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/user/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('611b9195b7ce4b3fb37f41023a907bda', '角色管理', 'c2348bbf343a47b5852f12ee32869b13', 'peoples', 'role', 'role/index', '/sys/role/index', 0, 'C', NULL, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/611b9195b7ce4b3fb37f41023a907bda/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/role/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('49d3319c02e542db9db32a6491193348', '字典管理', '843af15ab7694d54af793e4a5e6fb76e', 'dict', 'dictionary', 'dict/index', '/sys/dict/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193348/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:38:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('49d3319c02e542db9db32a6491193349', '字典数据', '843af15ab7694d54af793e4a5e6fb76e', '', 'dictionary-item', 'dict-item/index', '/sys/dictItem/index', 1, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193349/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2021-12-30 15:32:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict-item/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('a17e9c809f1049668633d8fe6103e740', '系统配置管理', '843af15ab7694d54af793e4a5e6fb76e', 'swagger', 'config', 'config', '/sys/config/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/a17e9c809f1049668633d8fe6103e740/', 7, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:41:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/config', 0);
INSERT INTO sys_dict (id, name, code, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('27906b13c5c0484396e284a4368b24b3', '参数类型', 'sys_config_value_type', NULL, '', '2022-01-17 17:36:40', '', '2022-01-17 17:36:40', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, value, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('53fa72c48f4c4324bd36733fe871b242', '文本', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '0', 1, NULL, '', '2022-01-17 17:36:59', '', '2022-01-17 17:36:59', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, value, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('98dba411055b460c913ab5454a6718c9', '图片', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '1', 2, NULL, '', '2022-01-17 17:37:07', '', '2022-01-17 17:37:07', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, value, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('a988ca46b6b445fc8f36d8b9d1243cb8', '颜色', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '2', 3, NULL, '', '2022-01-17 17:37:15', '', '2022-01-17 17:37:15', NULL);



-- 以下是来自 version_3_1.sql 的内容 --

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

-- 以下是来自 version_4_1.sql 的内容 --


ALTER TABLE "dev_application_version_history"
    ADD COLUMN "file_name" VARCHAR(255) null
    ;
ALTER TABLE "dev_application_version_history"
    ADD COLUMN "note" VARCHAR(255) null
;
ALTER TABLE "dev_application_version_history"
    ADD COLUMN "export_data" text null
;
ALTER TABLE "sys_logic_flow"
    ADD COLUMN "default_source_name" VARCHAR(100) null
;



-- 以下是来自 version_5_1.sql 的内容 --

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

-- 以下是来自 version_6_1.sql 的内容 --

CREATE TABLE IF NOT EXISTS "sys_logic_history" (
                                              "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                              "flow_id" varchar(36) COLLATE "pg_catalog"."default",
                                              "flow_json" text COLLATE "pg_catalog"."default",
                                              "when_created" timestamp(6),
                                              "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                              CONSTRAINT "sys_logic_history_pk_" PRIMARY KEY ("id")
)
;


-- 以下是来自 version_7_1.sql 的内容 --

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





-- 以下是来自 version_8_1.sql 的内容 --

DROP INDEX "public"."idx_sys_dict_id_name_uindex_sys_dict_item";

DROP INDEX "public"."idx_sys_dict_id_value_uindex_sys_dict_item";

CREATE INDEX "idx_sys_dict_id_name_uindex_sys_dict_item" ON "public"."sys_dict_item" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sys_dict_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

CREATE INDEX "idx_sys_dict_id_value_uindex_sys_dict_item" ON "public"."sys_dict_item" USING btree (
    "sys_dict_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "value" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- 以下是来自 version_9_1.sql 的内容 --



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




-- 以下是来自 version_10_1.sql 的内容 --

create table IF NOT EXISTS  dev_ota_channel
(
    id            varchar(36)   not null ,
    channel_name  varchar(50)   null ,
    channel_url   varchar(100)  null ,
    auth_token    varchar(50)   null ,
    sign_secret   varchar(50)   null ,
    master        int default 0 null ,
    note          varchar(255)  null ,
    when_created  varchar(20)   null ,
    who_created   varchar(36)   null ,
    when_modified varchar(20)   null ,
    who_modified  varchar(36)   null
);



-- 以下是来自 version_11_1.sql 的内容 --

delete from dev_ota_channel where id='8bab3a61164740c5958e4652d344f3f9';
INSERT INTO dev_ota_channel (id, channel_name, channel_url, auth_token, sign_secret, master, note, when_created, who_created, when_modified, who_modified) VALUES ('8bab3a61164740c5958e4652d344f3f9', '主通道', 'http://10.11.2.115:18882', 'yebKhNp2prcAXHkNFX4M8HDZc5ybsMep', '8T4xxmArYXjQWJjK', 1, '青松应用', '2022-08-23 15:56:38', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 18:41:46', '94123ca363dc4dfaa62a6bb5dcd3bf50');
delete from sys_menu where id='c137988391e440299865bd23884872a3';
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('c137988391e440299865bd23884872a3', '应用安装', '843af15ab7694d54af793e4a5e6fb76e', 'k-icon k-icon-rocket', null, 'upgrade', null, 0, 'C', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/c137988391e440299865bd23884872a3/', 14, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-08-30 16:51:26', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-08-30 16:53:01', null, 2, 'biz', 1, null, null, null, null, '/sys/conf/upgrade', 0);


-- 以下是来自 version_22_1.sql 的内容 --

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

-- 以下是来自 version_23_1.sql 的内容 --

DROP TABLE IF EXISTS rep_template;
CREATE TABLE rep_template(
                             id VARCHAR(36) NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             tpl_file_id VARCHAR(36),
                             excel_file VARCHAR(36),
                             type VARCHAR(10),
                             ds_sets VARCHAR(1024),
                             note VARCHAR(255),
                             who_created VARCHAR(36),
                             when_created VARCHAR(20),
                             who_modified VARCHAR(36),
                             when_modified VARCHAR(20),
                             PRIMARY KEY (id)
);

COMMENT ON TABLE rep_template IS '报告模板表';
COMMENT ON COLUMN rep_template.id IS '主键';
COMMENT ON COLUMN rep_template.name IS '模板名称';
COMMENT ON COLUMN rep_template.tpl_file_id IS '报告模板id';
COMMENT ON COLUMN rep_template.type IS '报告类型';
COMMENT ON COLUMN rep_template.ds_sets IS '数据集列表';
COMMENT ON COLUMN rep_template.note IS '说明';
COMMENT ON COLUMN rep_template.who_created IS '创建人';
COMMENT ON COLUMN rep_template.when_created IS '创建时间';
COMMENT ON COLUMN rep_template.who_modified IS '更新人';
COMMENT ON COLUMN rep_template.when_modified IS '更新时间';


-- 以下是来自 version_24_1.sql 的内容 --

CREATE TABLE sys_cache (
       id varchar(36) NOT NULL,
       code varchar(255) DEFAULT NULL,
       value varchar(255) DEFAULT NULL,
       when_expired varchar(30) DEFAULT NULL,
       when_created varchar(30) DEFAULT NULL,
       app_id varchar(36) DEFAULT NULL,
       PRIMARY KEY (id)
);

-- 以下是来自 version_25_1.sql 的内容 --

alter table sys_task
    add task_argv text;



-- 以下是来自 version_26_1.sql 的内容 --


alter table sys_logic_flow add module_id VARCHAR(36) null ;
alter table dev_page add module_id VARCHAR(36) null;
alter table dev_page add tags VARCHAR(36) null ;



-- 以下是来自 version_28_1.sql 的内容 --

alter table sys_dict_item alter column value type varchar(255) ;

-- 以下是来自 version_30_1.sql 的内容 --

DROP TABLE IF EXISTS dev_faas_node_type;
CREATE TABLE dev_faas_node_type(
                                   id VARCHAR(32)  ,
                                   name VARCHAR(90)    ,
                                   pub_status INT    ,
                                   icon VARCHAR(32)    ,
                                   when_created VARCHAR(255),
                                   who_created VARCHAR(255) ,
                                   when_modified VARCHAR(255) ,
                                   who_modified VARCHAR(255) ,
                                   PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dev_faas_node;
CREATE TABLE dev_faas_node(
                              id VARCHAR(32) NOT NULL   ,
                              name VARCHAR(90)    ,
                              code VARCHAR(90)   ,
                              type_id VARCHAR(32)    ,
                              config VARCHAR(1024)   ,
                              template VARCHAR(1024)    ,
                              icon VARCHAR(32)   ,
                              pub_status INT   ,
                              order_num VARCHAR(255),
                              when_created VARCHAR(255)   ,
                              who_created VARCHAR(255)    ,
                              when_modified VARCHAR(255) ,
                              who_modified VARCHAR(255)   ,
                              PRIMARY KEY (id)
) ;


DROP TABLE IF EXISTS sys_instance;
CREATE TABLE sys_instance(
                             id VARCHAR(32) NOT NULL,
                             host_name VARCHAR(32),
                             port INTEGER,
                             heart_beat_time VARCHAR(20),
                             reg_time VARCHAR(20),
                             PRIMARY KEY (id)
);



-- 以下是来自 version_31_1.sql 的内容 --

ALTER TABLE sys_task
    ADD COLUMN next_inst varchar(36);


-- 以下是来自 version_32_1.sql 的内容 --

ALTER TABLE sys_instance
    ADD COLUMN online int;


-- 以下是来自 version_34_1.sql 的内容 --

ALTER TABLE sys_search_config ADD search_columns text NULL;
ALTER TABLE sys_search_config ALTER COLUMN columns TYPE varchar(1000);


-- 以下是来自 version_35_1.sql 的内容 --


alter table sys_api alter column  api_tags type VARCHAR(255) ;
alter table sys_logic_flow alter column tags type VARCHAR(255) ;


-- 以下是来自 version_40_1.sql 的内容 --

ALTER TABLE "sys_dict"
    ALTER COLUMN "name" DROP NOT NULL,
ALTER COLUMN "code" DROP NOT NULL;


-- 以下是来自 version_41_1.sql 的内容 --

CREATE TABLE if not exists "wf_ext_category" (
                                            "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                            "category_name" varchar(50) COLLATE "pg_catalog"."default",
                                            "order_num" varchar(16) COLLATE "pg_catalog"."default",
                                            "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                            "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                            "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                            "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                            CONSTRAINT "wf_ext_category_pkey" PRIMARY KEY ("id")
);

CREATE TABLE if not exists  "wf_ext_comment" (
                                           "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                           "message" varchar(255) COLLATE "pg_catalog"."default",
                                           "proc_inst_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "task_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "task_name" varchar(50) COLLATE "pg_catalog"."default",
                                           "type" varchar(20) COLLATE "pg_catalog"."default",
                                           "user_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                           "parent_inst_id" varchar(50) COLLATE "pg_catalog"."default",
                                           CONSTRAINT "wf_ext_comment_pkey" PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists "wf_ext_node_attribute" (
                                                  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "actions" varchar(100) COLLATE "pg_catalog"."default",
                                                  "exec_mode" varchar(50) COLLATE "pg_catalog"."default",
                                                  "flow_id" varchar(100) COLLATE "pg_catalog"."default",
                                                  "form_attribute" text COLLATE "pg_catalog"."default",
                                                  "msg_send_rule" varchar(100) COLLATE "pg_catalog"."default",
                                                  "next_user" varchar(500) COLLATE "pg_catalog"."default",
                                                  "node_id" varchar(100) COLLATE "pg_catalog"."default",
                                                  "node_type" varchar(50) COLLATE "pg_catalog"."default",
                                                  "pass_ok" varchar(50) COLLATE "pg_catalog"."default",
                                                  "person" varchar(50) COLLATE "pg_catalog"."default",
                                                  "time_out" varchar(50) COLLATE "pg_catalog"."default",
                                                  "back_node" varchar(500) COLLATE "pg_catalog"."default",
                                                  "when_created" varchar(50) COLLATE "pg_catalog"."default",
                                                  "when_modified" varchar(50) COLLATE "pg_catalog"."default",
                                                  "who_created" varchar(100) COLLATE "pg_catalog"."default",
                                                  "who_modified" varchar(100) COLLATE "pg_catalog"."default",
                                                  CONSTRAINT "wf_ext_node_attribute_pkey" PRIMARY KEY ("id")
)
;

CREATE TABLE  if not exists  "wf_ext_procdef" (
                                           "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                           "category_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "content" text COLLATE "pg_catalog"."default",
                                           "deploy_md5" varchar(255) COLLATE "pg_catalog"."default",
                                           "deploy_status" int2,
                                           "deploy_time" varchar(20) COLLATE "pg_catalog"."default",
                                           "form_key" varchar(50) COLLATE "pg_catalog"."default",
                                           "form_page_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "icon" varchar(50) COLLATE "pg_catalog"."default",
                                           "inst_desc" varchar(255) COLLATE "pg_catalog"."default",
                                           "order_num" int4,
                                           "proc_definition_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "proc_definition_key" varchar(100) COLLATE "pg_catalog"."default",
                                           "proc_name" varchar(100) COLLATE "pg_catalog"."default",
                                           "proc_version" int4,
                                           "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                           "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                           "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                           "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                           "work_num" varchar(100) COLLATE "pg_catalog"."default",
                                           CONSTRAINT "wf_ext_procdef_pkey" PRIMARY KEY ("id")
)
;
CREATE TABLE  if not exists  "wf_ext_procdef" (
                                                  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "category_id" varchar(36) COLLATE "pg_catalog"."default",
                                                  "content" text COLLATE "pg_catalog"."default",
                                                  "deploy_md5" varchar(255) COLLATE "pg_catalog"."default",
                                                  "deploy_status" int2,
                                                  "deploy_time" varchar(20) COLLATE "pg_catalog"."default",
                                                  "form_key" varchar(50) COLLATE "pg_catalog"."default",
                                                  "form_page_id" varchar(36) COLLATE "pg_catalog"."default",
                                                  "icon" varchar(50) COLLATE "pg_catalog"."default",
                                                  "inst_desc" varchar(255) COLLATE "pg_catalog"."default",
                                                  "order_num" int4,
                                                  "proc_definition_id" varchar(36) COLLATE "pg_catalog"."default",
                                                  "proc_definition_key" varchar(100) COLLATE "pg_catalog"."default",
                                                  "proc_name" varchar(100) COLLATE "pg_catalog"."default",
                                                  "proc_version" int4,
                                                  "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                                  "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                                  "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                                  "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                                  "work_num" varchar(100) COLLATE "pg_catalog"."default",
                                                  CONSTRAINT "wf_ext_procdef_pkey" PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists  "wf_ext_procinst" (
                                            "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                            "bill_code" varchar(50) COLLATE "pg_catalog"."default",
                                            "bill_title" varchar(100) COLLATE "pg_catalog"."default",
                                            "form_data" text COLLATE "pg_catalog"."default",
                                            "main_inst_id" varchar(50) COLLATE "pg_catalog"."default",
                                            "main_task_id" varchar(50) COLLATE "pg_catalog"."default",
                                            "proc_inst_id" varchar(36) COLLATE "pg_catalog"."default",
                                            "starter" varchar(36) COLLATE "pg_catalog"."default",
                                            "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                            CONSTRAINT "wf_ext_procinst_pkey" PRIMARY KEY ("id")
)
;

ALTER TABLE "wf_ext_category"
    ALTER COLUMN "order_num" TYPE varchar(16) USING "order_num"::varchar(16);

ALTER TABLE "dev_ota_channel"
    ALTER COLUMN "master" TYPE varchar(32) USING "master"::varchar(32);
ALTER TABLE "sys_logic_history"
    ALTER COLUMN "when_created" TYPE varchar(20) USING "when_created"::varchar(20);
ALTER TABLE "ext_plugin_tree" RENAME COLUMN "extName" TO "ext_name";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "jarName" TO "jar_name";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "createTime" TO "create_time";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "updateTime" TO "update_time";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "createUser" TO "create_user";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "updateUser" TO "update_user";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "checkTime" TO "check_time";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "pluginId" TO "plugin_id";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "createTime" TO "create_time";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "createUser" TO "create_user";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "updateTime" TO "update_time";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "updateuser" TO "update_user";
ALTER TABLE "ext_plugin_interface" ADD COLUMN "deleted" int4 DEFAULT 0;
CREATE TABLE if not exists "dev_page_template" (
                                                   "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                                   "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                                   "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                                   "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                                   "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                                   "deleted" int2,
                                                   "app_id" varchar(36) COLLATE "pg_catalog"."default",
                                                   "name" varchar(255) COLLATE "pg_catalog"."default",
                                                   "description" varchar(255) COLLATE "pg_catalog"."default",
                                                   "app_type" varchar(100) COLLATE "pg_catalog"."default",
                                                   "page_json" text COLLATE "pg_catalog"."default",
                                                   "tags" varchar(255) COLLATE "pg_catalog"."default",
                                                   "module_id" varchar(36) COLLATE "pg_catalog"."default",
                                                   CONSTRAINT "dev_page_template_pkey" PRIMARY KEY ("id")
)
;
ALTER TABLE "dev_page_template"
    ALTER COLUMN "when_created" TYPE varchar(20) USING "when_created"::varchar(20),
ALTER COLUMN "when_modified" TYPE varchar(20) USING "when_modified"::varchar(20);
ALTER TABLE "sys_logic_history"
    ADD COLUMN "version_tag" varchar(50);
ALTER TABLE "sys_logic_history"
    ADD COLUMN "version_tag_time" varchar(50);
ALTER TABLE "dev_faas_node" ALTER COLUMN "config" TYPE text;

ALTER TABLE "dev_page_history"
    ADD COLUMN "version_tag" varchar(50);
ALTER TABLE "dev_page_history"
    ADD COLUMN "version_tag_time" varchar(50);

INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('0913bc0b384c44d99e384b992cb7fe40', '064b3b44b85a45fe87fcce88d72b2519', 1, 1, '开发平台', NULL, '/dev', 1, '2023-03-29 10:43:48', '2023-03-30 09:55:00', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('09e4ec197da14de3844b6b04c4fa5ee9', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '开发平台管理', NULL, NULL, 3, '2023-03-29 10:48:15', '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('5a10d15671704b6cbc01cc3a8bed365a', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '公共库', NULL, NULL, 4, '2022-05-31 15:18:08', '2022-05-31 15:24:40', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('6e2c61bac14a42ca9b24bbab853022a0', '0b00821bab714d8299d07a68c25873ed', 0, 0, '接口', 'bea8f78657644fa5808a863cce3c1182', NULL, 1, '2023-04-11 14:18:15', '2023-04-11 14:21:21', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('90f2c2ea0f9942d181388e24fd6ee936', '064b3b44b85a45fe87fcce88d72b2519', 1, 1, 'v3开发', '0913bc0b384c44d99e384b992cb7fe40', '/dev/v3', 1, '2023-03-29 17:37:35', '2023-03-30 09:55:12', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('9bbe33574d0547e78f72f5982bea26cd', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '子页面', '0913bc0b384c44d99e384b992cb7fe40', NULL, 0, '2023-03-29 10:46:23', '2023-03-30 09:55:08', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('d4820db91eab4cdc9c82703cf1d4df83', '064b3b44b85a45fe87fcce88d72b2519', 0, 0, '流程关联表单页', '1e329b86988b4dd79b49887b774b0879', NULL, 0, '2023-03-29 16:28:46', '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('e0047b3716fa48789d8d2377b1d23195', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '系统配置', NULL, NULL, 2, '2023-03-29 09:27:04', '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('fbe9d1e36a37423aa9ab4711c569093f', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '基础功能', NULL, NULL, 2, '2023-03-29 09:26:54', '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');

ALTER TABLE "ext_plugin_tree"
    ADD COLUMN "clazz_name" varchar(255);


-- 以下是来自 version_42_1.sql 的内容 --

drop table if exists dev_view_model;
drop table if exists dev_view_model_field;
drop table if exists dev_view_model_flow;
drop table if exists sys_view_model;
drop table if exists sys_view_model_field;
drop table if exists sys_view_model_flow;


CREATE TABLE if not EXISTS wf_cc_inst(
                                         inst_id VARCHAR(32),
                                         actor_id VARCHAR(50),
                                         creator VARCHAR(50),
                                         create_time VARCHAR(50),
                                         finish_time VARCHAR(50),
                                         status int2
);

COMMENT ON TABLE wf_cc_inst IS '抄送实例表';
COMMENT ON COLUMN wf_cc_inst.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_cc_inst.actor_id IS '参与者ID';
COMMENT ON COLUMN wf_cc_inst.creator IS '发起人';
COMMENT ON COLUMN wf_cc_inst.create_time IS '抄送时间';
COMMENT ON COLUMN wf_cc_inst.finish_time IS '完成时间';
COMMENT ON COLUMN wf_cc_inst.status IS '状态';


CREATE TABLE if not EXISTS wf_hist_inst(
                                           id VARCHAR(32) NOT NULL,
                                           process_id VARCHAR(32) NOT NULL,
                                           inst_state int2 NOT NULL,
                                           creator VARCHAR(500),
                                           create_time VARCHAR(50) NOT NULL,
                                           end_time VARCHAR(50),
                                           expire_time VARCHAR(50),
                                           priority int2,
                                           parent_id VARCHAR(32),
                                           inst_no VARCHAR(50),
                                           variable TEXT,
                                           PRIMARY KEY (id)
);

COMMENT ON TABLE wf_hist_inst IS '历史流程实例表';
COMMENT ON COLUMN wf_hist_inst.id IS '主键ID';
COMMENT ON COLUMN wf_hist_inst.process_id IS '流程定义ID';
COMMENT ON COLUMN wf_hist_inst.inst_state IS '状态';
COMMENT ON COLUMN wf_hist_inst.creator IS '发起人';
COMMENT ON COLUMN wf_hist_inst.create_time IS '发起时间';
COMMENT ON COLUMN wf_hist_inst.end_time IS '完成时间';
COMMENT ON COLUMN wf_hist_inst.expire_time IS '期望完成时间';
COMMENT ON COLUMN wf_hist_inst.priority IS '优先级';
COMMENT ON COLUMN wf_hist_inst.parent_id IS '父流程ID';
COMMENT ON COLUMN wf_hist_inst.inst_no IS '流程实例编号';
COMMENT ON COLUMN wf_hist_inst.variable IS '附属变量json存储';

CREATE TABLE if not EXISTS wf_hist_task(
                                           id VARCHAR(32) NOT NULL,
                                           inst_id VARCHAR(32) NOT NULL,
                                           task_name VARCHAR(100) NOT NULL,
                                           display_name VARCHAR(200) NOT NULL,
                                           task_type int2 NOT NULL,
                                           perform_type int2,
                                           task_state int2 NOT NULL,
                                           operator VARCHAR(500),
                                           create_time VARCHAR(50) NOT NULL,
                                           finish_time VARCHAR(50),
                                           expire_time VARCHAR(50),
                                           action_url VARCHAR(200),
                                           parent_task_id VARCHAR(32),
                                           variable TEXT,
                                           PRIMARY KEY (id)
);

COMMENT ON TABLE wf_hist_task IS '历史任务表';
COMMENT ON COLUMN wf_hist_task.id IS '主键ID';
COMMENT ON COLUMN wf_hist_task.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_hist_task.task_name IS '任务名称';
COMMENT ON COLUMN wf_hist_task.display_name IS '任务显示名称';
COMMENT ON COLUMN wf_hist_task.task_type IS '任务类型';
COMMENT ON COLUMN wf_hist_task.perform_type IS '参与类型';
COMMENT ON COLUMN wf_hist_task.task_state IS '任务状态';
COMMENT ON COLUMN wf_hist_task.operator IS '任务处理人';
COMMENT ON COLUMN wf_hist_task.create_time IS '任务创建时间';
COMMENT ON COLUMN wf_hist_task.finish_time IS '任务完成时间';
COMMENT ON COLUMN wf_hist_task.expire_time IS '任务期望完成时间';
COMMENT ON COLUMN wf_hist_task.action_url IS '任务处理url';
COMMENT ON COLUMN wf_hist_task.parent_task_id IS '父任务ID';
COMMENT ON COLUMN wf_hist_task.variable IS '附属变量json存储';

CREATE TABLE if not EXISTS wf_hist_task_actor(
                                                 task_id VARCHAR(32) NOT NULL,
                                                 actor_id VARCHAR(500) NOT NULL
);

COMMENT ON TABLE wf_hist_task_actor IS '历史任务参与者表';
COMMENT ON COLUMN wf_hist_task_actor.task_id IS '任务ID';
COMMENT ON COLUMN wf_hist_task_actor.actor_id IS '参与者ID';

CREATE TABLE if not EXISTS wf_inst(
                                      id VARCHAR(32) NOT NULL,
                                      parent_id VARCHAR(32),
                                      process_id VARCHAR(32) NOT NULL,
                                      creator VARCHAR(500),
                                      create_time VARCHAR(50) NOT NULL,
                                      expire_time VARCHAR(50),
                                      last_update_time VARCHAR(50),
                                      last_updator VARCHAR(500),
                                      priority int2,
                                      parent_node_name VARCHAR(100),
                                      inst_no VARCHAR(50),
                                      variable TEXT,
                                      version int4,
                                      PRIMARY KEY (id)
);

COMMENT ON TABLE wf_inst IS '流程实例表';
COMMENT ON COLUMN wf_inst.id IS '主键ID';
COMMENT ON COLUMN wf_inst.parent_id IS '父流程ID';
COMMENT ON COLUMN wf_inst.process_id IS '流程定义ID';
COMMENT ON COLUMN wf_inst.creator IS '发起人';
COMMENT ON COLUMN wf_inst.create_time IS '发起时间';
COMMENT ON COLUMN wf_inst.expire_time IS '期望完成时间';
COMMENT ON COLUMN wf_inst.last_update_time IS '上次更新时间';
COMMENT ON COLUMN wf_inst.last_updator IS '上次更新人';
COMMENT ON COLUMN wf_inst.priority IS '优先级';
COMMENT ON COLUMN wf_inst.parent_node_name IS '父流程依赖的节点名称';
COMMENT ON COLUMN wf_inst.inst_no IS '流程实例编号';
COMMENT ON COLUMN wf_inst.variable IS '附属变量json存储';
COMMENT ON COLUMN wf_inst.version IS '版本';

CREATE TABLE if not EXISTS wf_process(
                                         id VARCHAR(32) NOT NULL,
                                         name VARCHAR(100),
                                         display_name VARCHAR(200),
                                         type VARCHAR(100),
                                         instance_url VARCHAR(200),
                                         state int2,
                                         content TEXT,
                                         version int4,
                                         create_time VARCHAR(50),
                                         creator VARCHAR(50),
                                         PRIMARY KEY (id)
);

COMMENT ON TABLE wf_process IS '流程定义表';
COMMENT ON COLUMN wf_process.id IS '主键ID';
COMMENT ON COLUMN wf_process.name IS '流程名称';
COMMENT ON COLUMN wf_process.display_name IS '流程显示名称';
COMMENT ON COLUMN wf_process.type IS '流程类型';
COMMENT ON COLUMN wf_process.instance_url IS '实例url';
COMMENT ON COLUMN wf_process.state IS '流程是否可用';
COMMENT ON COLUMN wf_process.content IS '流程模型定义';
COMMENT ON COLUMN wf_process.version IS '版本';
COMMENT ON COLUMN wf_process.create_time IS '创建时间';
COMMENT ON COLUMN wf_process.creator IS '创建人';



CREATE TABLE if not EXISTS wf_surrogate(
                                           id VARCHAR(32) NOT NULL,
                                           process_name VARCHAR(100),
                                           operator VARCHAR(50),
                                           surrogate VARCHAR(50),
                                           odate VARCHAR(64),
                                           sdate VARCHAR(64),
                                           edate VARCHAR(64),
                                           state int2,
                                           PRIMARY KEY (id)
);

COMMENT ON TABLE wf_surrogate IS '委托代理表';
COMMENT ON COLUMN wf_surrogate.id IS '主键ID';
COMMENT ON COLUMN wf_surrogate.process_name IS '流程名称';
COMMENT ON COLUMN wf_surrogate.operator IS '授权人';
COMMENT ON COLUMN wf_surrogate.surrogate IS '代理人';
COMMENT ON COLUMN wf_surrogate.odate IS '操作时间';
COMMENT ON COLUMN wf_surrogate.sdate IS '开始时间';
COMMENT ON COLUMN wf_surrogate.edate IS '结束时间';
COMMENT ON COLUMN wf_surrogate.state IS '状态';

CREATE TABLE if not EXISTS wf_task(
                                      id VARCHAR(32) NOT NULL,
                                      inst_id VARCHAR(32) NOT NULL,
                                      task_name VARCHAR(100) NOT NULL,
                                      display_name VARCHAR(200) NOT NULL,
                                      task_type int2 NOT NULL,
                                      perform_type int2,
                                      operator VARCHAR(500),
                                      create_time VARCHAR(50),
                                      finish_time VARCHAR(50),
                                      expire_time VARCHAR(50),
                                      action_url VARCHAR(200),
                                      parent_task_id VARCHAR(32),
                                      variable TEXT,
                                      version int2,
                                      PRIMARY KEY (id)
);

COMMENT ON TABLE wf_task IS '任务表';
COMMENT ON COLUMN wf_task.id IS '主键ID';
COMMENT ON COLUMN wf_task.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_task.task_name IS '任务名称';
COMMENT ON COLUMN wf_task.display_name IS '任务显示名称';
COMMENT ON COLUMN wf_task.task_type IS '任务类型';
COMMENT ON COLUMN wf_task.perform_type IS '参与类型';
COMMENT ON COLUMN wf_task.operator IS '任务处理人';
COMMENT ON COLUMN wf_task.create_time IS '任务创建时间';
COMMENT ON COLUMN wf_task.finish_time IS '任务完成时间';
COMMENT ON COLUMN wf_task.expire_time IS '任务期望完成时间';
COMMENT ON COLUMN wf_task.action_url IS '任务处理的url';
COMMENT ON COLUMN wf_task.parent_task_id IS '父任务ID';
COMMENT ON COLUMN wf_task.variable IS '附属变量json存储';
COMMENT ON COLUMN wf_task.version IS '版本';

CREATE TABLE if not EXISTS wf_task_actor(
                                            task_id VARCHAR(32) NOT NULL,
                                            actor_id VARCHAR(500) NOT NULL
);

COMMENT ON TABLE wf_task_actor IS '任务参与者表';
COMMENT ON COLUMN wf_task_actor.task_id IS '任务ID';
COMMENT ON COLUMN wf_task_actor.actor_id IS '参与者ID';



CREATE TABLE "sys_offline_download"
(
    "id"            varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "file_name"     varchar(90) COLLATE "pg_catalog"."default",
    "task_name"     varchar(255) COLLATE "pg_catalog"."default",
    "file_path"     varchar(255) COLLATE "pg_catalog"."default",
    "end_time"      varchar(255) COLLATE "pg_catalog"."default",
    "script"        varchar(1024) COLLATE "pg_catalog"."default",
    "process" int4,
    "status"        varchar(255) COLLATE "pg_catalog"."default",
    "params"        varchar(255) COLLATE "pg_catalog"."default",
    "error_message" varchar(900) COLLATE "pg_catalog"."default",
    "who_created"   varchar(36) COLLATE "pg_catalog"."default",
    "when_created"  varchar(20) COLLATE "pg_catalog"."default",
    CONSTRAINT "sys_offline_download_pkey" PRIMARY KEY ("id")
)
;


-- 以下是来自 version_43_1.sql 的内容 --


CREATE TABLE if not EXISTS wf_ru_timer_job(
                                              id VARCHAR(98) NOT NULL,
                                              inst_id VARCHAR(32) NOT NULL,
                                              process_id VARCHAR(32) NOT NULL,
                                              task_id VARCHAR(32) NOT NULL,
                                              retries int4,
                                              expiredate VARCHAR(50),
                                              repeats int4
);

COMMENT ON TABLE wf_ru_timer_job IS '运行中任务计时器表';
COMMENT ON COLUMN wf_ru_timer_job.id IS '计时任务ID';
COMMENT ON COLUMN wf_ru_timer_job.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_ru_timer_job.process_id IS '流程定义ID';
COMMENT ON COLUMN wf_ru_timer_job.task_id IS '工作流任务ID';
COMMENT ON COLUMN wf_ru_timer_job.retries IS '重试次数';
COMMENT ON COLUMN wf_ru_timer_job.expiredate IS '到期时间';
COMMENT ON COLUMN wf_ru_timer_job.repeats IS '重复';

CREATE TABLE if not EXISTS wf_ext_node_define(
                                                 id VARCHAR(50),
                                                 who_created VARCHAR(50),
                                                 when_created VARCHAR(50),
                                                 who_modified VARCHAR(50),
                                                 when_modified VARCHAR(50),
                                                 name VARCHAR(200),
                                                 group_name VARCHAR(200),
                                                 type VARCHAR(200),
                                                 start_icon TEXT,
                                                 show_icon TEXT,
                                                 node_json VARCHAR(200),
                                                 is_extend VARCHAR(200),
                                                 status VARCHAR(200)
);

COMMENT ON TABLE wf_ext_node_define IS '青松-节点定义表';
COMMENT ON COLUMN wf_ext_node_define.id IS '主键';
COMMENT ON COLUMN wf_ext_node_define.who_created IS '创建人';
COMMENT ON COLUMN wf_ext_node_define.when_created IS '创建时间';
COMMENT ON COLUMN wf_ext_node_define.who_modified IS '更新人';
COMMENT ON COLUMN wf_ext_node_define.when_modified IS '更新时间';
COMMENT ON COLUMN wf_ext_node_define.name IS '节点名称';
COMMENT ON COLUMN wf_ext_node_define.group_name IS '分类';
COMMENT ON COLUMN wf_ext_node_define.type IS '节点类型';
COMMENT ON COLUMN wf_ext_node_define.start_icon IS '初始图标';
COMMENT ON COLUMN wf_ext_node_define.show_icon IS '流程设计图标';
COMMENT ON COLUMN wf_ext_node_define.node_json IS '节点配置';
COMMENT ON COLUMN wf_ext_node_define.is_extend IS '是否可自定义扩展';
COMMENT ON COLUMN wf_ext_node_define.status IS '是否生效';


alter table wf_ext_node_attribute add column if not EXISTS name  VARCHAR(200) ;
alter table wf_ext_procdef add column if not EXISTS page_form  TEXT ;

alter table wf_ext_procinst add column if not EXISTS task_name  VARCHAR(100) ;
alter table wf_ext_procinst add column if not EXISTS actors  VARCHAR(500) ;
alter table wf_ext_procinst add column if not EXISTS work_inst_ids  TEXT;
alter table wf_ext_procinst add column if not EXISTS audit_user_ids  VARCHAR(500) ;
alter table wf_ext_procinst add column if not EXISTS process_key  VARCHAR(50) ;


-- 以下是来自 version_44_1.sql 的内容 --

CREATE TABLE if not exists dev_plugin_api (
                                              id VARCHAR(36) PRIMARY KEY,
                                              title VARCHAR(255),
                                              group_code VARCHAR(255),
                                              code VARCHAR(255),
                                              tags VARCHAR(255),
                                              notes TEXT,
                                              order_num VARCHAR(10),
                                              who_created VARCHAR(36),
                                              when_created VARCHAR(20),
                                              who_modified VARCHAR(36),
                                              when_modified VARCHAR(20)
);
CREATE TABLE if not exists dev_plugin_group (
                                                id VARCHAR(36) PRIMARY KEY,
                                                name VARCHAR(255),
                                                code VARCHAR(255),
                                                notes TEXT,
                                                order_num INT,
                                                who_created VARCHAR(36),
                                                when_created VARCHAR(20),
                                                who_modified VARCHAR(36),
                                                when_modified VARCHAR(20)
);
CREATE TABLE if not exists dev_plugin_operation (
                                                    id VARCHAR(36) PRIMARY KEY,
                                                    code VARCHAR(255),
                                                    tags VARCHAR(255),
                                                    api_id VARCHAR(36),
                                                    title VARCHAR(255),
                                                    notes TEXT,
                                                    cases TEXT,
                                                    success_resp TEXT,
                                                    error_resp TEXT,
                                                    in_params TEXT,
                                                    who_created VARCHAR(36),
                                                    when_created VARCHAR(20),
                                                    who_modified VARCHAR(36),
                                                    when_modified VARCHAR(20),
                                                    order_num INT DEFAULT 0
);


-- 以下是来自 version_45_1.sql 的内容 --

delete from sys_dict_item where id='1537c3ca5e934e6c846b0415229dbe85';

alter table sys_logic_flow add new_flow_json text null;
alter table sys_logic_history add new_flow_json text null;
alter table sys_logic_template add flow_config text null;
alter table sys_logic_template add type varchar(10) null;
alter table sys_logic_template add new_flow_json text null;

-- 以下是来自 version_46_1.sql 的内容 --

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
























-- 以下是来自 version_47_1.sql 的内容 --

ALTER TABLE sys_online_user ALTER COLUMN login_token TYPE text;


-- 以下是来自 version_48_1.sql 的内容 --

create table if not exists sys_auth_source
(
    id           varchar(36) not null,
    code         varchar(36),
    icon         varchar(255),
    config       text,
    logic_flow_id  varchar(36),
    name         varchar(255),
    note         varchar(255),
    order_num     int4,
    status       int4,
    type       int4,
    when_created  varchar(20),
    when_modified varchar(20),
    who_created   varchar(36),
    who_modified  varchar(36),
    primary key (id)
);


-- 以下是来自 version_49_1.sql 的内容 --

alter table sys_login_log add address VARCHAR(255) null ;


-- 以下是来自 version_50_1.sql 的内容 --

-- 修改 use_time 字段的数据类型为 int
ALTER TABLE open_api_log ALTER COLUMN use_time TYPE integer;

-- 添加对 use_time 字段的注释
COMMENT ON COLUMN open_api_log.use_time IS '响应时间(秒)';

-- 将 error_message 字段的数据类型修改为 text
ALTER TABLE open_api_log ALTER COLUMN error_message TYPE text;

-- 添加对 error_message 字段的注释
COMMENT ON COLUMN open_api_log.error_message IS '错误信息';

CREATE TABLE sys_logic_template_user (
                                         id varchar(36)  NOT NULL,
                                         app_id varchar(36)  DEFAULT NULL,
                                         when_created varchar(50)  DEFAULT NULL,
                                         who_created varchar(36)  DEFAULT NULL ,
                                         template_id varchar(36)  DEFAULT NULL,
                                         PRIMARY KEY (id)
);

alter table sys_logic_template add sys_suggested integer null;
alter table sys_logic_template add publish_status integer null;

ALTER TABLE dev_page_template ADD snapshot_img_id VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD order_num integer NULL  ;
ALTER TABLE dev_page_template ADD bg_colors VARCHAR(64) NULL;
ALTER TABLE dev_page_template ADD page_type VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD use_num integer DEFAULT 0 NULL;
ALTER TABLE dev_page_template ADD extra text NULL;

CREATE TABLE dev_page_template_history (
                                           id varchar(36) NOT NULL,
                                           tpl_id varchar(36) DEFAULT NULL,
                                           page_json text,
                                           when_created varchar(50) NULL DEFAULT NULL,
                                           who_created varchar(36) DEFAULT NULL,
                                           version_tag varchar(50) DEFAULT NULL,
                                           version_tag_time varchar(30) DEFAULT NULL,
                                           app_id varchar(36) DEFAULT NULL,
                                           PRIMARY KEY (id)
);

ALTER TABLE dev_faas_node ALTER COLUMN config type text;
ALTER TABLE dev_faas_node ALTER COLUMN template type varchar(10240);

CREATE TABLE IF NOT EXISTS dev_chat_history (
    id varchar(36)  NOT NULL,
    question text  NOT NULL,
    answer text ,
    args text,
    when_created varchar(50)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id)
    );

-- 以下是来自 version_51_1.sql 的内容 --

CREATE TABLE IF NOT EXISTS dev_curd (
    id varchar(36)  NOT NULL ,
    name varchar(255)  DEFAULT null,
    group_id varchar(36)  DEFAULT NULL,
    source_name varchar(255)  DEFAULT NULL,
    table_name varchar(255)  DEFAULT NULL,
    primary_name varchar(50)  DEFAULT NULL,
    request_prefix varchar(255)  DEFAULT NULL,
    enable_funs varchar(255)  DEFAULT NULL,
    create_funs varchar(255)  DEFAULT NULL,
    column_json text ,
    app_id varchar(36)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    when_created varchar(20)  DEFAULT NULL,
    who_modified varchar(36)  DEFAULT NULL,
    when_modified varchar(255)  DEFAULT NULL ,
    PRIMARY KEY (id)
    );
INSERT INTO dev_team (id, deleted, description, name, owner, when_created, when_modified, who_created, who_modified) VALUES('991718335d57416a9be67d4090538402', 0, NULL, '默认团队', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a48', NULL, 0, '3fc43c9c69f44144bd032d9451ba328b', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a49', NULL, 1, '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');

CREATE TABLE IF NOT EXISTS sys_logic_template_user (
     id varchar(36) NOT NULL,
     app_id varchar(36) DEFAULT NULL,
     when_created varchar(50) DEFAULT NULL,
     who_created varchar(36) DEFAULT NULL,
     template_id varchar(36) DEFAULT NULL,
     PRIMARY KEY (id)
);

create index idx_sys_operate_log_app_id on sys_operate_log (app_id);
create index idx_sys_operate_log_module on sys_operate_log (module);
create index idx_sys_operate_log_operate_time on sys_operate_log (operate_time);
create index sys_operate_log_action_IDX on sys_operate_log (action);
create index dev_team_app_app_id on dev_team_app (app_id);
create index dev_team_app_team_id on dev_team_app (team_id);
create index idx_username_uindex on sys_user (username);

-- 修改 dev_power_tree 表
ALTER TABLE dev_power_tree ADD COLUMN order_num int NULL;
COMMENT ON COLUMN dev_power_tree.order_num IS '排序';

-- 修改 sys_logic_template 表
ALTER TABLE sys_logic_template ADD COLUMN order_num int NULL;
COMMENT ON COLUMN sys_logic_template.order_num IS '排序';
ALTER TABLE sys_logic_template ADD COLUMN enable_status int NULL default 1;
COMMENT ON COLUMN sys_logic_template.enable_status IS '可用状态';

-- 以下是来自 version_52_1.sql 的内容 --


ALTER TABLE sys_menu ADD COLUMN affix int NULL default 0;
COMMENT ON COLUMN sys_menu.affix IS '是否固定页签';
ALTER TABLE sys_api
    ADD COLUMN cache_enable integer,
    ADD COLUMN cache_cron varchar(128),
    ADD COLUMN cache_expire_time integer;


-- 以下是来自 version_53_1.sql 的内容 --

-- 对于sys_unit表添加short_name列
ALTER TABLE sys_unit ADD COLUMN short_name varchar(36);
COMMENT ON COLUMN sys_unit.short_name IS '简称';

-- 对于sys_unit表添加unit_code列
ALTER TABLE sys_unit ADD COLUMN unit_code varchar(100);
COMMENT ON COLUMN sys_unit.unit_code IS '机构编码';

-- 对于sys_user表添加jira_name列
ALTER TABLE sys_user ADD COLUMN jira_name varchar(100);
COMMENT ON COLUMN sys_user.jira_name IS 'jira账号名（项目管理系统）';


-- 以下是来自 version_54_1.sql 的内容 --

CREATE TABLE IF NOT EXISTS sys_password_log (
                                                id VARCHAR(36) PRIMARY KEY,
                                                user_id VARCHAR(36),
                                                when_created VARCHAR(20)
);

COMMENT ON COLUMN sys_password_log.id IS '主键';
COMMENT ON COLUMN sys_password_log.user_id IS '用户id';
COMMENT ON COLUMN sys_password_log.when_created IS '创建时间';

CREATE TABLE IF NOT EXISTS sys_secret_rule (
                                               id VARCHAR(36) PRIMARY KEY,
                                               name VARCHAR(255),
                                               logic_id VARCHAR(36),
                                               secret_type INTEGER DEFAULT 0,
                                               status INTEGER,
                                               order_num INTEGER,
                                               notes TEXT,
                                               when_created VARCHAR(20),
                                               who_created VARCHAR(36),
                                               when_modified VARCHAR(20),
                                               who_modified VARCHAR(36)
);

COMMENT ON COLUMN sys_secret_rule.id IS '主键';
COMMENT ON COLUMN sys_secret_rule.name IS '规则名称';
COMMENT ON COLUMN sys_secret_rule.logic_id IS '逻辑编排ID';
COMMENT ON COLUMN sys_secret_rule.secret_type IS '安全类型';
COMMENT ON COLUMN sys_secret_rule.status IS '是否启用';
COMMENT ON COLUMN sys_secret_rule.order_num IS '排序';
COMMENT ON COLUMN sys_secret_rule.notes IS '说明';
COMMENT ON COLUMN sys_secret_rule.when_created IS '创建时间';
COMMENT ON COLUMN sys_secret_rule.who_created IS '创建人';
COMMENT ON COLUMN sys_secret_rule.when_modified IS '更新时间';
COMMENT ON COLUMN sys_secret_rule.who_modified IS '更新人';


-- 以下是来自 version_55_1.sql 的内容 --

ALTER TABLE sys_menu ADD COLUMN active_icon varchar(255) NULL;

ALTER TABLE sys_config ADD COLUMN is_public int NULL default 0;


-- 以下是来自 version_56_1.sql 的内容 --

alter table sys_logic_flow add i18n_keys text null;
ALTER TABLE dev_team
    ADD COLUMN is_audit int NULL,
    ADD COLUMN image varchar(255) NULL;


-- 以下是来自 version_57_1.sql 的内容 --

ALTER TABLE sys_instance
    ADD COLUMN cluster_no INTEGER;

-- 以下是来自 version_58_1.sql 的内容 --

ALTER TABLE open_account ADD COLUMN app_id VARCHAR(36);


-- 以下是来自 version_59_1.sql 的内容 --

ALTER TABLE dev_application
    ADD COLUMN depend_datasources text NULL,
    ADD COLUMN depend_apps text NULL;


-- 以下是来自 version_60_1.sql 的内容 --

ALTER TABLE dev_application
    ADD COLUMN app_namespace VARCHAR(255);


-- 以下是来自 version_61_1.sql 的内容 --

CREATE TABLE IF NOT EXISTS dev_model_sql (
    id varchar(36) NOT NULL,
    app_id varchar(36),
    title varchar(255),
    source_name varchar(50),
    "content" text,
    status int8 DEFAULT 0,
    sql_version int8,
    messages text,
    ignore_except int8 DEFAULT 1,
    exec_err_line int8 DEFAULT 0,
    exec_time varchar(20),
    exec_user_id varchar(36),
    when_created varchar(20),
    who_created varchar(36),
    when_modified varchar(20),
    who_modified varchar(36),
    PRIMARY KEY (id)
);
COMMENT ON COLUMN dev_model_sql.id IS '主键';
COMMENT ON COLUMN dev_model_sql.app_id IS '应用id';
COMMENT ON COLUMN dev_model_sql.title IS '标题';
COMMENT ON COLUMN dev_model_sql.source_name IS '数据源';
COMMENT ON COLUMN dev_model_sql."content" IS '脚本';
COMMENT ON COLUMN dev_model_sql.status IS '执行状态 0: 未执行 1：已执行 2：执行异常';
COMMENT ON COLUMN dev_model_sql.sql_version IS '版本号';
COMMENT ON COLUMN dev_model_sql.messages IS '执行结果';
COMMENT ON COLUMN dev_model_sql.ignore_except IS '是否忽略错误';
COMMENT ON COLUMN dev_model_sql.exec_err_line IS '错误行号';
COMMENT ON COLUMN dev_model_sql.exec_time IS '执行时间';
COMMENT ON COLUMN dev_model_sql.exec_user_id IS '执行人';
COMMENT ON COLUMN dev_model_sql.when_created IS '创建时间';
COMMENT ON COLUMN dev_model_sql.who_created IS '创建人';
COMMENT ON COLUMN dev_model_sql.when_modified IS '修改时间';
COMMENT ON COLUMN dev_model_sql.who_modified IS '修改人';

-- 数据源业务映射表
CREATE TABLE IF NOT EXISTS dev_data_source (
    id varchar(36) NOT NULL,
    "name" varchar(100),
    who_created varchar(36),
    when_created varchar(30),
    who_modified varchar(36),
    when_modified varchar(30),
    app_id varchar(36),
    kdb_id varchar(40),
    team_id varchar(36),
    deleted int8,
    PRIMARY KEY (id)
);
COMMENT ON COLUMN dev_data_source.id IS 'ID';
COMMENT ON COLUMN dev_data_source."name" IS '数据源名称';
COMMENT ON COLUMN dev_data_source.who_created IS '创建人员';
COMMENT ON COLUMN dev_data_source.when_created IS '创建时间';
COMMENT ON COLUMN dev_data_source.who_modified IS '修改人员';
COMMENT ON COLUMN dev_data_source.when_modified IS '修改时间';
COMMENT ON COLUMN dev_data_source.app_id IS '关联应用';
COMMENT ON COLUMN dev_data_source.kdb_id IS '在kingDB中对应的ID';
COMMENT ON COLUMN dev_data_source.team_id IS '所属团队ID';
COMMENT ON COLUMN dev_data_source.deleted IS '是否已删除';

-- 开发表-页面新建的搜索记录
CREATE TABLE IF NOT EXISTS dev_search_history (
    id varchar(36) NOT NULL,
    keyword varchar(36) NOT NULL,
    use_num int8,
    deleted int DEFAULT 0,
    PRIMARY KEY (id)
    );

COMMENT ON COLUMN dev_search_history.id IS '主键';
COMMENT ON COLUMN dev_search_history.keyword IS '关键字';
COMMENT ON COLUMN dev_search_history.use_num IS '搜索次数';
COMMENT ON COLUMN dev_search_history.deleted IS '是否删除';

CREATE TABLE IF NOT EXISTS dev_git_tag (
    id varchar(36) NOT NULL,
    tag varchar(255),
    repo varchar(255),
    resource varchar(255),
    commit_id varchar(255),
    public_commit_ids text,
    note text,
    when_created varchar(20),
    who_created varchar(255),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_git_tag.id IS '主键';
COMMENT ON COLUMN dev_git_tag.tag IS '标签名称';
COMMENT ON COLUMN dev_git_tag.repo IS '仓库地址';
COMMENT ON COLUMN dev_git_tag.resource IS '资源文件';
COMMENT ON COLUMN dev_git_tag.commit_id IS '提交ID';
COMMENT ON COLUMN dev_git_tag.public_commit_ids IS '公共依赖库';
COMMENT ON COLUMN dev_git_tag.note IS '版本说明';
COMMENT ON COLUMN dev_git_tag.when_created IS '创建时间';
COMMENT ON COLUMN dev_git_tag.who_created IS '创建人';

CREATE TABLE IF NOT EXISTS sys_config_group (
    id varchar(36) NOT NULL,
    app_id varchar(36),
    group_name varchar(255) NOT NULL,
    group_path varchar(255),
    when_created varchar(30),
    when_modified varchar(30),
    who_created varchar(36),
    who_modified varchar(36),
    note varchar(255),
    parent_id varchar(32),
    group_type int8 DEFAULT 1,
    leaf_config text,
    sort int8,
    icon varchar(50),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN sys_config_group.id IS '主键ID';
COMMENT ON COLUMN sys_config_group.app_id IS '关联应用';
COMMENT ON COLUMN sys_config_group.group_name IS '组名称';
COMMENT ON COLUMN sys_config_group.group_path IS '路径';
COMMENT ON COLUMN sys_config_group.when_created IS '创建时间';
COMMENT ON COLUMN sys_config_group.when_modified IS '修改时间';
COMMENT ON COLUMN sys_config_group.who_created IS '创建人员';
COMMENT ON COLUMN sys_config_group.who_modified IS '修改人员';
COMMENT ON COLUMN sys_config_group.note IS '备注';
COMMENT ON COLUMN sys_config_group.parent_id IS '父分组ID';
COMMENT ON COLUMN sys_config_group.group_type IS '分组层级';
COMMENT ON COLUMN sys_config_group.leaf_config IS '配置格式json';
COMMENT ON COLUMN sys_config_group.sort IS '排序';
COMMENT ON COLUMN sys_config_group.icon IS '图标';

CREATE TABLE IF NOT EXISTS dev_model_latest (
    id varchar(36) NOT NULL,
    model_name varchar(50),
    source_name varchar(50),
    version_name varchar(100),
    version_who varchar(100),
    version_time varchar(20),
    description varchar(255),
    diagram text,
    inner_version bigint DEFAULT 0,
    custom_type_mapping text,
    app_id varchar(36),
    who_created varchar(36),
    when_created varchar(20),
    who_modified varchar(36),
    when_modified varchar(20),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_model_latest.id IS '主键ID';
COMMENT ON COLUMN dev_model_latest.model_name IS '模型名称';
COMMENT ON COLUMN dev_model_latest.source_name IS '数据源名称';
COMMENT ON COLUMN dev_model_latest.version_name IS '修订版本';
COMMENT ON COLUMN dev_model_latest.version_who IS '修订人';
COMMENT ON COLUMN dev_model_latest.version_time IS '修订时间';
COMMENT ON COLUMN dev_model_latest.description IS '备注';
COMMENT ON COLUMN dev_model_latest.diagram IS '模型数据';
COMMENT ON COLUMN dev_model_latest.inner_version IS '内部版本号，用于服务端保存时校验';
COMMENT ON COLUMN dev_model_latest.custom_type_mapping IS '用户自定义类型映射，当用户导入表出现系统未适配的字段类型时，提醒用户选择要转成什么类型';
COMMENT ON COLUMN dev_model_latest.app_id IS '所属应用ID';
COMMENT ON COLUMN dev_model_latest.who_created IS '创建人';
COMMENT ON COLUMN dev_model_latest.when_created IS '创建时间';
COMMENT ON COLUMN dev_model_latest.who_modified IS '更新人';
COMMENT ON COLUMN dev_model_latest.when_modified IS '更新时间';

CREATE TABLE IF NOT EXISTS dev_page_template_action_log (
    id varchar(36) NOT NULL,
    action_type int8 NOT NULL,
    is_copy_all int,
    template_id varchar(36) NOT NULL,
    app_id varchar(36),
    team_id varchar(36),
    action_content text,
    who_created varchar(36) NOT NULL,
    when_created varchar(20) NOT NULL,
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_page_template_action_log.id IS '主键';
COMMENT ON COLUMN dev_page_template_action_log.action_type IS '动作类型；1：模板复制；2：模板预览';
COMMENT ON COLUMN dev_page_template_action_log.is_copy_all IS '是否全文复制';
COMMENT ON COLUMN dev_page_template_action_log.template_id IS '数据id，根据action_type指向不同的表';
COMMENT ON COLUMN dev_page_template_action_log.app_id IS '关联应用';
COMMENT ON COLUMN dev_page_template_action_log.team_id IS '关联团队';
COMMENT ON COLUMN dev_page_template_action_log.action_content IS '动作关联的内容，例如action_type为模板复制时，content就是复制的内容';
COMMENT ON COLUMN dev_page_template_action_log.who_created IS '创建人员';
COMMENT ON COLUMN dev_page_template_action_log.when_created IS '创建时间';

CREATE TABLE IF NOT EXISTS dev_file_version (
    id varchar(36) NOT NULL,
    file_name varchar(255),
    path varchar(1000),
    os_type varchar(20),
    "version" varchar(100),
    path_by_package varchar(1000),
    file_size int8,
    description varchar(255),
    parent_path varchar(1000),
    when_modified varchar(30),
    who_modified varchar(36),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_file_version.id IS 'ID';
COMMENT ON COLUMN dev_file_version.file_name IS '文件名';
COMMENT ON COLUMN dev_file_version.path IS '导致安装包的位置';
COMMENT ON COLUMN dev_file_version.os_type IS '操作系统类型';
COMMENT ON COLUMN dev_file_version."version" IS '版本号（vX.X.X结构）';
COMMENT ON COLUMN dev_file_version.path_by_package IS '所在package中的真实位置';
COMMENT ON COLUMN dev_file_version.file_size IS '文件大小';
COMMENT ON COLUMN dev_file_version.description IS '描述';
COMMENT ON COLUMN dev_file_version.parent_path IS '父目录';
COMMENT ON COLUMN dev_file_version.when_modified IS '更新时间';
COMMENT ON COLUMN dev_file_version.who_modified IS '更新人';

CREATE TABLE IF NOT EXISTS sys_base (
    id varchar(36) NOT NULL,
    app_id varchar(36),
    code varchar(50),
    is_test int2,
    "name" varchar(50),
    note text,
    when_created varchar(30),
    when_modified varchar(30),
    who_created varchar(36),
    who_modified varchar(36),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN sys_base.app_id IS '关联应用';
COMMENT ON COLUMN sys_base.code IS '编码';
COMMENT ON COLUMN sys_base."name" IS '名称';
COMMENT ON COLUMN sys_base.note IS '备注';
COMMENT ON COLUMN sys_base.when_created IS '创建时间';
COMMENT ON COLUMN sys_base.when_modified IS '修改时间';
COMMENT ON COLUMN sys_base.who_created IS '创建人员';
COMMENT ON COLUMN sys_base.who_modified IS '修改人员';






















-- 以下是来自 version_62_1.sql 的内容 --

-- dev_page_template
ALTER TABLE dev_page_template ADD COLUMN view_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.view_num IS '预览量';

ALTER TABLE dev_page_template ADD COLUMN copy_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.copy_num IS '被复制的次数；copy_num = copy_all_num+copy_part_num';

ALTER TABLE dev_page_template ADD COLUMN copy_all_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.copy_all_num IS '被全量复制的次数';

ALTER TABLE dev_page_template ADD COLUMN copy_part_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.copy_part_num IS '被部分复制的次数';

-- /* dev_sql_script */
ALTER TABLE dev_sql_script ADD COLUMN id varchar(36) NOT NULL;

-- /* sys_notice */
ALTER TABLE sys_notice ADD COLUMN is_force int2;
COMMENT ON COLUMN sys_notice.is_force IS '是否重要 0: 否 1：是';

ALTER TABLE sys_notice ADD COLUMN config text;
COMMENT ON COLUMN sys_notice.config IS '团队头像';

/* rep_dataset */
ALTER TABLE rep_dataset ADD COLUMN dataset_search_id varchar(32);
COMMENT ON COLUMN rep_dataset.dataset_search_id IS '是否为搜索数据';

ALTER TABLE rep_dataset ADD COLUMN shape int8;
COMMENT ON COLUMN rep_dataset.shape IS '是否为自定义SQL';

ALTER TABLE rep_dataset ADD COLUMN "template" int8;
COMMENT ON COLUMN rep_dataset."template" IS '是否为Excel模板（0：否，1：是）';

ALTER TABLE rep_dataset ADD COLUMN rep_cron varchar(50);
COMMENT ON COLUMN rep_dataset.rep_cron IS '模板报表定时任务Cron表达式';

/* sys_auth_source */
ALTER TABLE sys_auth_source ADD COLUMN whocreated varchar(36);

/* sys_config */
ALTER TABLE sys_config ADD COLUMN group_id varchar(36);
COMMENT ON COLUMN sys_config.group_id IS '关联组';

/* open_api_log */
ALTER TABLE open_api_log ADD COLUMN api_id varchar(36);
COMMENT ON COLUMN open_api_log.api_id IS '接口id';

-- 以下是来自 version_63_1.sql 的内容 --

CREATE TABLE dev_seats (
     id varchar(100) NOT NULL,
     node_id varchar(100) DEFAULT NULL,
     num varchar(50) DEFAULT NULL,
     type varchar(50) DEFAULT NULL,
     status varchar(50) DEFAULT NULL,
     user_id varchar(50) DEFAULT NULL,
     floor_id varchar(50) DEFAULT NULL,
     description varchar(200) DEFAULT NULL,
     when_created varchar(100) DEFAULT NULL,
     when_modified varchar(100) DEFAULT NULL,
     who_created varchar(255) DEFAULT NULL,
     who_modified varchar(255) DEFAULT NULL,
     PRIMARY KEY (id)
);
