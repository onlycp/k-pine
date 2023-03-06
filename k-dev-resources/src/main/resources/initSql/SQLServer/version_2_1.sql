-- ----------------------------
-- Table structure for sys_unit
-- ----------------------------
CREATE TABLE  "sys_unit" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  NOT NULL ,  "parent_id" varchar(36)  DEFAULT NULL ,  "path" text  NOT NULL ,  "leader" varchar(255)  DEFAULT NULL ,  "mobile" varchar(20)  DEFAULT NULL ,  "email" varchar(50)  DEFAULT NULL ,  "status" tinyint NOT NULL DEFAULT '1' ,  "note" text  ,  "order_num" int DEFAULT '0' ,  "who_created" varchar(36)  NOT NULL ,  "when_created" varchar(20)  NOT NULL ,  "who_modified" varchar(36)  NOT NULL ,  "when_modified" varchar(20)  NOT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE  "sys_user" (  "id" varchar(50)  NOT NULL ,  "username" varchar(50)  NOT NULL ,  "password" varchar(256)  DEFAULT NULL ,  "real_name" varchar(50)  NOT NULL ,  "mobile" varchar(20)  DEFAULT NULL ,  "email" varchar(50)  DEFAULT NULL ,  "sex" tinyint DEFAULT NULL ,  "sys_unit_id" varchar(36)  DEFAULT NULL ,  "post" varchar(50)  DEFAULT NULL ,  "status" tinyint DEFAULT '1' ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "deleted" tinyint DEFAULT '0' ,  "avatar" varchar(255)  DEFAULT NULL,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ,      CONSTRAINT "fk_sys_unit_id" FOREIGN KEY ("sys_unit_id") REFERENCES "sys_unit" ("id"))  ;
-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE  "sys_role" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  NOT NULL ,  "code" varchar(50)  NOT NULL ,  "note" text  ,  "status" tinyint DEFAULT NULL ,  "who_created" varchar(36)  NOT NULL ,  "when_created" varchar(20)  NOT NULL ,  "who_modified" varchar(36)  NOT NULL ,  "when_modified" varchar(20)  NOT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE  "sys_user_role" (  "id" varchar(36)  NOT NULL ,  "sys_user_id" varchar(50)  NOT NULL ,  "sys_role_id" varchar(36)  NOT NULL ,  "who_created" varchar(36)  NOT NULL ,  "when_created" varchar(20)  NOT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for dev_api
-- ----------------------------
CREATE TABLE  "dev_api" (  "id" varchar(36)  NOT NULL DEFAULT '' ,  "api_name" varchar(50)  DEFAULT NULL ,  "application_id" varchar(36)  DEFAULT NULL ,  "api_url" varchar(128)  DEFAULT NULL ,  "api_note" text  ,  "api_tags" varchar(128)  DEFAULT NULL ,  "api_method" varchar(36)  DEFAULT 'get' ,  "api_argv_type" int DEFAULT NULL ,  "api_req_argv" text  ,  "api_rsp_argv" text  ,  "api_result_handler" varchar(128)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "api_flow_id" varchar(50)  DEFAULT NULL ,  "api_code" varchar(50)  DEFAULT NULL ,  "call_type" int DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for dev_api_copy1
-- ----------------------------
CREATE TABLE  "dev_api_copy1" (  "id" varchar(36)  NOT NULL DEFAULT '' ,  "api_name" varchar(50)  DEFAULT NULL ,  "application_id" varchar(36)  DEFAULT NULL ,  "api_url" varchar(128)  DEFAULT NULL ,  "api_note" text  ,  "api_tags" varchar(128)  DEFAULT NULL ,  "api_method" varchar(36)  DEFAULT 'get' ,  "api_argv_type" int DEFAULT NULL ,  "api_req_argv" text  ,  "api_rsp_argv" text  ,  "api_result_handler" varchar(128)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "api_flow_id" varchar(50)  DEFAULT NULL ,  "api_code" varchar(50)  DEFAULT NULL ,  "call_type" int DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for dev_application
-- ----------------------------
CREATE TABLE  "dev_application" (  "id" varchar(36)  NOT NULL ,  "name" varchar(100)  DEFAULT NULL ,  "short_name" varchar(30)  NOT NULL ,  "description" varchar(255)  DEFAULT NULL ,  "enable_status" tinyint DEFAULT NULL ,  "dev_status" tinyint DEFAULT NULL ,  "version" varchar(50)  DEFAULT NULL ,  "who_in_charge" varchar(255)  DEFAULT NULL ,  "system_logo" varchar(255)  DEFAULT NULL ,  "app_type" varchar(100)  DEFAULT NULL ,  "default_path" varchar(255)  DEFAULT NULL ,  "deleted" tinyint NOT NULL DEFAULT '0' ,  "when_created" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "faas_port" int DEFAULT NULL ,  "pine_port" int DEFAULT NULL ,  "data_source" varchar(1000)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for dev_application_v_history
-- ----------------------------
CREATE TABLE  "dev_application_version_history" (  "id" varchar(36)  NOT NULL ,  "when_created" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  "version" varchar(50)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for dev_document
-- ----------------------------
CREATE TABLE  "dev_document" (  "id" varchar(255)  NOT NULL ,  "name" varchar(255)  DEFAULT NULL ,  "path" varchar(255)  DEFAULT NULL ,  "content" text  ,  "parent_id" varchar(255)  DEFAULT NULL ,  "order" int NOT NULL IDENTITY ,  "when_created" date DEFAULT NULL ,  "who_created" varchar(255)  DEFAULT NULL ,  "deleted" tinyint DEFAULT '0' ,  PRIMARY KEY ("order")) ;
-- ----------------------------
-- Table structure for dev_page
-- ----------------------------
CREATE TABLE  "dev_page" (  "id" varchar(36)  NOT NULL ,  "when_created" datetime ,  "when_modified" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "deleted" tinyint DEFAULT '0' ,  "app_id" varchar(36)  DEFAULT NULL ,  "name" varchar(255)  DEFAULT NULL ,  "description" varchar(255)  DEFAULT NULL ,  "path" varchar(255)  DEFAULT NULL ,  "app_type" varchar(100)  DEFAULT NULL ,  "login_required" tinyint DEFAULT NULL ,  "enable_status" tinyint DEFAULT NULL ,  "dev_status" tinyint DEFAULT NULL ,  "page_json" text  ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for dev_page_history
-- ----------------------------
CREATE TABLE  "dev_page_history" (  "id" varchar(36)  NOT NULL ,  "page_id" varchar(36)  DEFAULT NULL ,  "page_json" text  ,  "when_created" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for dev_sql_script
-- ----------------------------
CREATE TABLE  "dev_sql_script" (  "sql" text  NOT NULL ,  "description" varchar(255)  DEFAULT NULL ,  "version" int NOT NULL IDENTITY ,  "is_once" tinyint NOT NULL DEFAULT '1' ,  PRIMARY KEY ("version")) ;
-- ----------------------------
-- Table structure for dev_team
-- ----------------------------
CREATE TABLE  "dev_team" (  "id" varchar(36)  NOT NULL ,  "name" varchar(100)  NOT NULL ,  "owner" varchar(36)  NOT NULL ,  "description" varchar(255)  DEFAULT NULL ,  "deleted" tinyint NOT NULL DEFAULT '0' ,  "when_created" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id"))  ;
-- ----------------------------
-- Table structure for dev_team_app
-- ----------------------------
CREATE TABLE  "dev_team_app" (  "id" varchar(36)  NOT NULL ,  "team_id" varchar(36)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  "team_type" tinyint NOT NULL ,  "when_created" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id")) ;
-- ----------------------------
-- Table structure for dev_team_member
-- ----------------------------
CREATE TABLE  "dev_team_member" (  "id" varchar(36)  NOT NULL ,  "team_id" varchar(36)  NOT NULL ,  "user_id" varchar(36)  NOT NULL ,  "when_join" datetime ,  "who_invite" varchar(36)  DEFAULT NULL ,  "team_role_id" varchar(36)  DEFAULT NULL ,  "is_owner" tinyint DEFAULT NULL ,  "app_id" varchar(255)  DEFAULT NULL ,  PRIMARY KEY ("id"),   )  ;
-- ----------------------------
-- Table structure for dev_topological
-- ----------------------------
CREATE TABLE  "dev_topological" (  "id" varchar(36)  NOT NULL ,  "when_created" datetime ,  "when_modified" datetime ,  "who_created" varchar(36)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "deleted" tinyint DEFAULT '0' ,  "app_id" varchar(36)  DEFAULT NULL ,  "name" varchar(255)  DEFAULT NULL ,  "description" varchar(255)  DEFAULT NULL ,  "page_json" text  ,  "enable_status" tinyint DEFAULT NULL )  ;
-- ----------------------------
-- Table structure for dev_view_model
-- ----------------------------
CREATE TABLE  "dev_view_model" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "deleted" int DEFAULT '0' ,  "tag" varchar(100)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for dev_view_model_field
-- ----------------------------
CREATE TABLE  "dev_view_model_field" (  "id" varchar(36)  NOT NULL ,  "view_model_id" varchar(36)  DEFAULT NULL ,  "field" varchar(50)  DEFAULT NULL ,  "label" varchar(50)  DEFAULT NULL ,  "type" varchar(20)  DEFAULT NULL ,  "format_type" varchar(20)  DEFAULT NULL ,  "format_pattern" varchar(50)  DEFAULT NULL ,  "default_text" varchar(50)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "hidden" int DEFAULT '0' ,  "order_num" int DEFAULT '0' ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id"))  ;
-- ----------------------------
-- Table structure for dev_view_model_flow
-- ----------------------------
CREATE TABLE  "dev_view_model_flow" (  "id" varchar(36)  NOT NULL,  "flow_id" varchar(36)  DEFAULT NULL ,  "view_model_id" varchar(36)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
CREATE TABLE  "sys_api" (  "id" varchar(36)  NOT NULL DEFAULT '' ,  "api_name" varchar(50)  DEFAULT NULL ,  "api_url" varchar(128)  DEFAULT NULL ,  "api_note" text  ,  "api_tags" varchar(128)  DEFAULT NULL ,  "api_method" varchar(36)  DEFAULT 'get' ,  "api_argv_type" int DEFAULT NULL ,  "api_req_argv" text  ,  "api_rsp_argv" text  ,  "api_result_handler" varchar(128)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "api_flow_id" varchar(50)  DEFAULT NULL ,  "api_code" varchar(50)  DEFAULT NULL ,  "call_type" int DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for sys_base
-- ----------------------------
CREATE TABLE  "sys_base" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "code" varchar(50)  DEFAULT NULL ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "is_test" tinyint DEFAULT NULL,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
CREATE TABLE  "sys_config" (  "id" varchar(36)  NOT NULL ,  "name" varchar(255)  DEFAULT NULL ,  "code" varchar(255)  DEFAULT NULL ,  "value" varchar(255)  DEFAULT NULL ,  "is_sys" tinyint DEFAULT NULL ,  "note" varchar(255)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "value_type" tinyint DEFAULT '0',  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id")) ;
-- ----------------------------
-- Table structure for sys_data_access
-- ----------------------------
CREATE TABLE  "sys_data_access" (  "id" varchar(50)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "status" tinyint DEFAULT NULL ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_data_resource
-- ----------------------------
CREATE TABLE  "sys_data_resource" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "table_name" varchar(50)  DEFAULT NULL ,  "label_field" varchar(50)  DEFAULT NULL ,  "value_field" varchar(50)  DEFAULT NULL ,  "query_sql" text  ,  "is_tree" tinyint DEFAULT NULL ,  "is_only_leaf" tinyint DEFAULT NULL ,  "status" tinyint DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "extra_sql" text  ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_data_access_resource
-- ----------------------------
CREATE TABLE  "sys_data_access_resource" (  "id" varchar(36)  NOT NULL ,  "data_id" varchar(36)  DEFAULT NULL ,  "access_id" varchar(50)  DEFAULT NULL ,  "table_name" varchar(50)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ,    CONSTRAINT "fk_resource_access" FOREIGN KEY ("access_id") REFERENCES "sys_data_access" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION)  ;
-- ----------------------------
-- Table structure for sys_data_access_user
-- ----------------------------
CREATE TABLE  "sys_data_access_user" (  "id" varchar(36)  NOT NULL ,  "sys_user_id" varchar(50)  DEFAULT NULL ,  "sys_data_access_id" varchar(50)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ,      CONSTRAINT "fk_user_access_id" FOREIGN KEY ("sys_data_access_id") REFERENCES "sys_data_access" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,  CONSTRAINT "fk_user_access" FOREIGN KEY ("sys_user_id") REFERENCES "sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION)  ;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
CREATE TABLE  "sys_dict" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "code" varchar(50)  DEFAULT NULL ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
CREATE TABLE  "sys_dict_item" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "group_name" varchar(50)  DEFAULT NULL ,  "sys_dict_id" varchar(36)  DEFAULT NULL ,  "code" varchar(50)  DEFAULT NULL ,  "value" varchar(20)  DEFAULT NULL ,  "order_num" int DEFAULT NULL ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
CREATE TABLE  "sys_file" (  "id" varchar(36)  NOT NULL ,  "file_name" varchar(100)  DEFAULT NULL ,  "file_original_name" varchar(100)  DEFAULT NULL ,  "file_size" int DEFAULT NULL ,  "file_ext" text  ,  "file_md5" varchar(40)  DEFAULT NULL ,  "file_from" varchar(50)  DEFAULT NULL ,  "save_type" int DEFAULT NULL ,  "file_path" varchar(100)  DEFAULT NULL ,  "file_content" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_logic_flow
-- ----------------------------
CREATE TABLE  "sys_logic_flow" (  "id" varchar(36)  NOT NULL ,  "name" varchar(255)  DEFAULT NULL ,  "flow_id" varchar(36)  DEFAULT NULL ,  "application_id" varchar(36)  DEFAULT NULL ,  "tags" varchar(255)  DEFAULT NULL ,  "in_argv" text  ,  "out_argv" text  ,  "sub_flow_ids" text  ,  "note" varchar(255)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
CREATE TABLE  "sys_login_log" (  "id" varchar(36)  NOT NULL ,  "operate_time" datetime ,  "operator" varchar(36)  DEFAULT NULL ,  "ip" varchar(20)  DEFAULT NULL ,  "times" int DEFAULT NULL ,  "response_code" int DEFAULT NULL ,  "response_message" varchar(100)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE  "sys_menu" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  NOT NULL ,  "parent_id" varchar(36)  DEFAULT NULL ,  "icon" varchar(50)  DEFAULT NULL ,  "code" varchar(50)  DEFAULT NULL ,  "router_path" varchar(255)  DEFAULT NULL ,  "component_path" varchar(255)  DEFAULT NULL ,  "is_hidden" tinyint NOT NULL DEFAULT '0' ,  "menu_type" char(1)  NOT NULL DEFAULT '0' ,  "api_codes" varchar(255)  DEFAULT NULL ,  "open_mode" tinyint NOT NULL ,  "keep_alive" tinyint DEFAULT NULL ,  "path" text  NOT NULL ,  "order_num" int NOT NULL DEFAULT '0' ,  "status" tinyint NOT NULL DEFAULT '1' ,  "who_created" varchar(36)  NOT NULL ,  "when_created" varchar(20)  NOT NULL ,  "who_modified" varchar(36)  NOT NULL ,  "when_modified" varchar(20)  NOT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  "data_type" tinyint DEFAULT NULL ,  "theme" varchar(50)  DEFAULT NULL ,  "page_type" tinyint DEFAULT NULL ,  "sidebar_nav_mode" tinyint DEFAULT NULL ,  "top_nav_mode" tinyint DEFAULT NULL ,  "main_mode" tinyint DEFAULT NULL ,  "page_id" varchar(36)  DEFAULT NULL ,  "full_path" varchar(255)  DEFAULT NULL ,  "is_dev" tinyint DEFAULT '0' ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
CREATE TABLE  "sys_notice" (  "id" varchar(36)  NOT NULL ,  "title" varchar(255)  DEFAULT NULL ,  "content" text  ,  "type" tinyint DEFAULT NULL ,  "status" tinyint NOT NULL DEFAULT '1' ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "deleted" tinyint DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for sys_notice_record
-- ----------------------------
CREATE TABLE  "sys_notice_record" (  "id" varchar(36)  NOT NULL ,  "from_who" varchar(36)  DEFAULT NULL ,  "to_who" varchar(36)  NOT NULL ,  "notice_id" varchar(36)  DEFAULT NULL ,  "is_read" tinyint NOT NULL DEFAULT '0' ,  "read_time" datetime ,  "notice_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,  "title" varchar(255)  DEFAULT NULL ,  "content" text  ,  "to_who_name" varchar(255)  DEFAULT NULL ,  "from_who_name" varchar(255)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") ) ;
-- ----------------------------
-- Table structure for sys_online_user
-- ----------------------------
CREATE TABLE  "sys_online_user" (  "id" varchar(36)  NOT NULL ,  "user_id" varchar(36)  DEFAULT NULL ,  "login_time" datetime ,  "login_ip" varchar(20)  DEFAULT NULL ,  "login_token" varchar(1024)  DEFAULT NULL ,  "expire_time" datetime ,  "when_created" datetime ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
CREATE TABLE   "sys_operate_log" (  "id" varchar(36)  NOT NULL ,  "module" varchar(100)  DEFAULT NULL ,  "action" varchar(255)  DEFAULT NULL ,  "url" varchar(255)  DEFAULT NULL ,  "operate_time" datetime ,  "operator" varchar(36)  DEFAULT NULL ,  "ip" varchar(20)  DEFAULT NULL ,  "times" int DEFAULT NULL ,  "request_body" text  ,  "response_code" int DEFAULT NULL ,  "response_message" varchar(100)  DEFAULT NULL ,  "when_created" varchar(20)  DEFAULT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE  "sys_role_menu" (  "id" varchar(36)  NOT NULL ,  "sys_menu_id" varchar(36)  NOT NULL ,  "sys_role_id" varchar(50)  NOT NULL ,  "who_created" varchar(36)  NOT NULL ,  "when_created" varchar(20)  NOT NULL ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
CREATE TABLE  "sys_task" (  "id" varchar(36)  NOT NULL ,  "name" varchar(100)  DEFAULT NULL ,  "cron" varchar(50)  DEFAULT NULL ,  "distributed" tinyint DEFAULT NULL ,  "application_id" varchar(36)  DEFAULT NULL ,  "task_type" tinyint DEFAULT '1' ,  "task_resource_id" varchar(36)  DEFAULT NULL ,  "class_name" varchar(255)  DEFAULT NULL ,  "enable" tinyint DEFAULT '1' ,  "last_execute_status" tinyint DEFAULT NULL ,  "last_execute_msg" text  ,  "last_execute_time" datetime ,  "last_execute_take" int DEFAULT NULL ,  "lock_status" tinyint DEFAULT '0' ,  "lock_for_most" int DEFAULT '30' ,  "lock_for_least" int DEFAULT '1' ,  "lock_for_time" datetime ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "app_id" varchar(36)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_view_model
-- ----------------------------
CREATE TABLE  "sys_view_model" (  "id" varchar(36)  NOT NULL ,  "name" varchar(50)  DEFAULT NULL ,  "note" text  ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "deleted" int DEFAULT '0' ,  "tag" varchar(100)  DEFAULT NULL ,  PRIMARY KEY ("id") )  ;
-- ----------------------------
-- Table structure for sys_view_model_field
-- ----------------------------
CREATE TABLE  "sys_view_model_field" (  "id" varchar(36)  NOT NULL ,  "view_model_id" varchar(36)  DEFAULT NULL ,  "field" varchar(50)  DEFAULT NULL ,  "label" varchar(50)  DEFAULT NULL ,  "type" varchar(20)  DEFAULT NULL ,  "format_type" varchar(20)  DEFAULT NULL ,  "format_pattern" varchar(50)  DEFAULT NULL ,  "default_text" varchar(50)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  "hidden" int DEFAULT '0' ,  "order_num" int DEFAULT '0' )  ;
-- ----------------------------
-- Table structure for sys_view_model_flow
-- ----------------------------
CREATE TABLE  "sys_view_model_flow" (  "id" varchar(36)  NOT NULL,  "flow_id" varchar(36)  DEFAULT NULL ,  "view_model_id" varchar(36)  DEFAULT NULL ,  "who_created" varchar(36)  DEFAULT NULL ,  "when_created" datetime ,  "who_modified" varchar(36)  DEFAULT NULL ,  "when_modified" datetime ,  PRIMARY KEY ("id") )  ;

INSERT INTO "sys_user" ("id", "username", "password", "real_name", "mobile", "email", "sex", "sys_unit_id", "post", "status", "note", "who_created", "when_created", "who_modified", "when_modified", "deleted", "avatar", "app_id") VALUES ('056fb0eeb9a44cb0953534b4c0ca01fa', 'admin', 'MTIzNDU2', '超级管理员', NULL, NULL, 1, NULL, NULL, 1, NULL, '', '2021-12-29 16:36:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:58:32', 0, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL);
INSERT INTO "sys_role" ("id", "name", "code", "note", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id") VALUES ('10d26189026a4dba86a8e63a4c717ed6', '超级管理员', 'admin', '应用超级管理员', 1, '', '2021-12-28 15:22:56', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:41', NULL);
INSERT INTO "sys_user_role" ("id", "sys_user_id", "sys_role_id", "who_created", "when_created", "app_id") VALUES ('b5f79a7cc794423e843a2b1fd9a27007', '056fb0eeb9a44cb0953534b4c0ca01fa', '10d26189026a4dba86a8e63a4c717ed6', '', '2022-03-10 06:31:39', NULL);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('c2348bbf343a47b5852f12ee32869b13', '基础信息', NULL, 'documentation', 'system-info', 'sys/info', NULL, 0, 'M', '', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-01-10 19:02:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('1a246429b14e4db2be0e1847a3939e98', '菜单管理', '843af15ab7694d54af793e4a5e6fb76e', 'nested', 'menu', 'menu', '/sys/menu/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/1a246429b14e4db2be0e1847a3939e98/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-02-23 00:29:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/menu', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('843af15ab7694d54af793e4a5e6fb76e', '系统配置', NULL, 'system', 'system-config', 'sys/conf', NULL, 0, 'M', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-01-10 19:02:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('58f5f98c57c74a91b6c2ca24c5df0ba9', '用户管理', 'c2348bbf343a47b5852f12ee32869b13', 'user', 'user', 'user/index', '/sys/user/index', 0, 'C', 'sys:user:list', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/58f5f98c57c74a91b6c2ca24c5df0ba9/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-02-23 00:28:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/user/index', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('611b9195b7ce4b3fb37f41023a907bda', '角色管理', 'c2348bbf343a47b5852f12ee32869b13', 'peoples', 'role', 'role/index', '/sys/role/index', 0, 'C', NULL, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/611b9195b7ce4b3fb37f41023a907bda/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-02-23 00:29:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/role/index', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('49d3319c02e542db9db32a6491193348', '字典管理', '843af15ab7694d54af793e4a5e6fb76e', 'dict', 'dictionary', 'dict/index', '/sys/dict/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193348/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-02-23 00:38:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict/index', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('49d3319c02e542db9db32a6491193349', '字典数据', '843af15ab7694d54af793e4a5e6fb76e', '', 'dictionary-item', 'dict-item/index', '/sys/dictItem/index', 1, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193349/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 15:32:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict-item/index', 0);
INSERT INTO "sys_menu" ("id", "name", "parent_id", "icon", "code", "router_path", "component_path", "is_hidden", "menu_type", "api_codes", "open_mode", "keep_alive", "path", "order_num", "status", "who_created", "when_created", "who_modified", "when_modified", "app_id", "data_type", "theme", "page_type", "sidebar_nav_mode", "top_nav_mode", "main_mode", "page_id", "full_path", "is_dev") VALUES ('a17e9c809f1049668633d8fe6103e740', '系统配置管理', '843af15ab7694d54af793e4a5e6fb76e', 'swagger', 'config', 'config', '/sys/config/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/a17e9c809f1049668633d8fe6103e740/', 7, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-02-23 00:41:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/config', 0);

