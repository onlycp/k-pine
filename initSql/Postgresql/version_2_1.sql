
-- ----------------------------
-- Table structure for dev_api
-- ----------------------------
CREATE TABLE IF NOT EXISTS "dev_api" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "api_name" varchar(50) COLLATE "pg_catalog"."default",
    "application_id" varchar(32) COLLATE "pg_catalog"."default",
    "api_url" varchar(128) COLLATE "pg_catalog"."default",
    "api_note" text COLLATE "pg_catalog"."default",
    "api_tags" varchar(128) COLLATE "pg_catalog"."default",
    "api_method" varchar(32) COLLATE "pg_catalog"."default" DEFAULT 'get'::character varying,
    "api_argv_type" int8,
    "api_req_argv" text COLLATE "pg_catalog"."default",
    "api_rsp_argv" text COLLATE "pg_catalog"."default",
    "api_result_handler" varchar(128) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "view_model_id" varchar(32) COLLATE "pg_catalog"."default",
    "field" varchar(50) COLLATE "pg_catalog"."default",
    "label" varchar(50) COLLATE "pg_catalog"."default",
    "type" varchar(20) COLLATE "pg_catalog"."default",
    "format_type" varchar(20) COLLATE "pg_catalog"."default",
    "format_pattern" varchar(50) COLLATE "pg_catalog"."default",
    "default_text" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "flow_id" varchar(36) COLLATE "pg_catalog"."default",
    "view_model_id" varchar(32) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "api_name" varchar(50) COLLATE "pg_catalog"."default",
    "api_url" varchar(128) COLLATE "pg_catalog"."default",
    "api_note" text COLLATE "pg_catalog"."default",
    "api_tags" varchar(128) COLLATE "pg_catalog"."default",
    "api_method" varchar(32) COLLATE "pg_catalog"."default" DEFAULT 'get'::character varying,
    "api_argv_type" int8,
    "api_req_argv" text COLLATE "pg_catalog"."default",
    "api_rsp_argv" text COLLATE "pg_catalog"."default",
    "api_result_handler" varchar(128) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "value_type" int8 DEFAULT '0'::bigint,
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_access
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_access" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "status" int8,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_access_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_access_resource" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "data_id" varchar(36) COLLATE "pg_catalog"."default",
    "access_id" varchar(36) COLLATE "pg_catalog"."default",
    "table_name" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_access_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_access_user" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "sys_user_id" varchar(32) COLLATE "pg_catalog"."default",
    "sys_data_access_id" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_data_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_data_resource" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "table_name" varchar(50) COLLATE "pg_catalog"."default",
    "label_field" varchar(50) COLLATE "pg_catalog"."default",
    "value_field" varchar(50) COLLATE "pg_catalog"."default",
    "query_sql" text COLLATE "pg_catalog"."default",
    "is_tree" int8,
    "is_only_leaf" int8,
    "status" int8,
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "extra_sql" text COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_dict" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_dict_item" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "group_name" varchar(50) COLLATE "pg_catalog"."default",
    "sys_dict_id" varchar(32) COLLATE "pg_catalog"."default",
    "code" varchar(50) COLLATE "pg_catalog"."default",
    "value" varchar(20) COLLATE "pg_catalog"."default",
    "order_num" int8,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_file" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "file_name" varchar(100) COLLATE "pg_catalog"."default",
    "file_original_name" varchar(100) COLLATE "pg_catalog"."default",
    "file_size" int8,
    "file_ext" text COLLATE "pg_catalog"."default",
    "file_md5" varchar(40) COLLATE "pg_catalog"."default",
    "file_from" varchar(50) COLLATE "pg_catalog"."default",
    "save_type" int8,
    "file_path" varchar(100) COLLATE "pg_catalog"."default",
    "file_content" text COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "application_id" varchar(32) COLLATE "pg_catalog"."default",
    "tags" varchar(255) COLLATE "pg_catalog"."default",
    "in_argv" text COLLATE "pg_catalog"."default",
    "out_argv" text COLLATE "pg_catalog"."default",
    "sub_flow_ids" text COLLATE "pg_catalog"."default",
    "note" varchar(255) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_login_log" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "operate_time" varchar(20) COLLATE "pg_catalog"."default",
    "operator" varchar(32) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "parent_id" varchar(32) COLLATE "pg_catalog"."default",
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
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "read_time" varchar(20) COLLATE "pg_catalog"."default",
    "notice_time" varchar(20) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id" varchar(32) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "module" varchar(100) COLLATE "pg_catalog"."default",
    "action" varchar(255) COLLATE "pg_catalog"."default",
    "url" varchar(255) COLLATE "pg_catalog"."default",
    "operate_time" varchar(20) COLLATE "pg_catalog"."default",
    "operator" varchar(32) COLLATE "pg_catalog"."default",
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
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "note" text COLLATE "pg_catalog"."default",
    "status" int8,
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_role_menu" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "sys_menu_id" varchar(32) COLLATE "pg_catalog"."default",
    "sys_role_id" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_task" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(100) COLLATE "pg_catalog"."default",
    "cron" varchar(50) COLLATE "pg_catalog"."default",
    "distributed" int8,
    "application_id" varchar(32) COLLATE "pg_catalog"."default",
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
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "app_id" varchar(36) COLLATE "pg_catalog"."default"
    )
;
-- ----------------------------
-- Table structure for sys_unit
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_unit" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name" varchar(50) COLLATE "pg_catalog"."default",
    "parent_id" varchar(32) COLLATE "pg_catalog"."default",
    "path" text COLLATE "pg_catalog"."default",
    "leader" varchar(255) COLLATE "pg_catalog"."default",
    "mobile" varchar(20) COLLATE "pg_catalog"."default",
    "email" varchar(50) COLLATE "pg_catalog"."default",
    "status" int8 DEFAULT '1'::bigint,
    "note" text COLLATE "pg_catalog"."default",
    "order_num" int8 DEFAULT '0'::bigint,
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "sys_unit_id" varchar(32) COLLATE "pg_catalog"."default",
    "post" varchar(50) COLLATE "pg_catalog"."default",
    "status" int8 DEFAULT '1'::bigint,
    "note" text COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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
    "sys_user_id" varchar(32) COLLATE "pg_catalog"."default",
    "sys_role_id" varchar(32) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
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
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "deleted" int8 DEFAULT '0'::bigint,
    "tag" varchar(100) COLLATE "pg_catalog"."default"
    )
;

-- ----------------------------
-- Table structure for sys_view_model_field
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_view_model_field" (
    "id" varchar(32) COLLATE "pg_catalog"."default",
    "view_model_id" varchar(32) COLLATE "pg_catalog"."default",
    "field" varchar(50) COLLATE "pg_catalog"."default",
    "label" varchar(50) COLLATE "pg_catalog"."default",
    "type" varchar(20) COLLATE "pg_catalog"."default",
    "format_type" varchar(20) COLLATE "pg_catalog"."default",
    "format_pattern" varchar(50) COLLATE "pg_catalog"."default",
    "default_text" varchar(50) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
    "when_modified" varchar(20) COLLATE "pg_catalog"."default",
    "hidden" int8 DEFAULT '0'::bigint,
    "order_num" int8 DEFAULT '0'::bigint
    )
;

-- ----------------------------
-- Table structure for sys_view_model_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS "sys_view_model_flow" (
    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "flow_id" varchar(36) COLLATE "pg_catalog"."default",
    "view_model_id" varchar(32) COLLATE "pg_catalog"."default",
    "who_created" varchar(32) COLLATE "pg_catalog"."default",
    "when_created" varchar(20) COLLATE "pg_catalog"."default",
    "who_modified" varchar(32) COLLATE "pg_catalog"."default",
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



INSERT INTO sys_user (id, username, password, real_name, mobile, email, sex, sys_unit_id, post, status, note, who_created, when_created, who_modified, when_modified, deleted, avatar, app_id) VALUES ('056fb0eeb9a44cb0953534b4c0ca01fa', 'admin', 'MTIzNDU2', '超级管理员', NULL, NULL, 1, NULL, NULL, 1, NULL, '', '2021-12-29 16:36:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:58:32', 0, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL);
INSERT INTO sys_role (id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id) VALUES ('10d26189026a4dba86a8e63a4c717ed6', '超级管理员', 'admin', '应用超级管理员', 1, '', '2021-12-28 15:22:56', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:41', NULL);
INSERT INTO sys_user_role (id, sys_user_id, sys_role_id, who_created, when_created, app_id) VALUES ('b5f79a7cc794423e843a2b1fd9a27007', '056fb0eeb9a44cb0953534b4c0ca01fa', '10d26189026a4dba86a8e63a4c717ed6', '', '2022-03-10 06:31:39', NULL);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('c2348bbf343a47b5852f12ee32869b13', '基础信息', NULL, 'documentation', 'system-info', 'sys/info', NULL, 0, 'M', '', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info', 0);INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('1a246429b14e4db2be0e1847a3939e98', '菜单管理', '843af15ab7694d54af793e4a5e6fb76e', 'nested', 'menu', 'menu', '/sys/menu/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/1a246429b14e4db2be0e1847a3939e98/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/menu', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('843af15ab7694d54af793e4a5e6fb76e', '系统配置', NULL, 'system', 'system-config', 'sys/conf', NULL, 0, 'M', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('58f5f98c57c74a91b6c2ca24c5df0ba9', '用户管理', 'c2348bbf343a47b5852f12ee32869b13', 'user', 'user', 'user/index', '/sys/user/index', 0, 'C', 'sys:user:list', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/58f5f98c57c74a91b6c2ca24c5df0ba9/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:28:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/user/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('611b9195b7ce4b3fb37f41023a907bda', '角色管理', 'c2348bbf343a47b5852f12ee32869b13', 'peoples', 'role', 'role/index', '/sys/role/index', 0, 'C', NULL, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/611b9195b7ce4b3fb37f41023a907bda/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/role/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('49d3319c02e542db9db32a6491193348', '字典管理', '843af15ab7694d54af793e4a5e6fb76e', 'dict', 'dictionary', 'dict/index', '/sys/dict/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193348/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:38:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('49d3319c02e542db9db32a6491193349', '字典数据', '843af15ab7694d54af793e4a5e6fb76e', '', 'dictionary-item', 'dict-item/index', '/sys/dictItem/index', 1, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193349/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2021-12-30 15:32:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict-item/index', 0);
INSERT INTO sys_menu (id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, path, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev) VALUES ('a17e9c809f1049668633d8fe6103e740', '系统配置管理', '843af15ab7694d54af793e4a5e6fb76e', 'swagger', 'config', 'config', '/sys/config/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/a17e9c809f1049668633d8fe6103e740/', 7, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:41:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/config', 0);

