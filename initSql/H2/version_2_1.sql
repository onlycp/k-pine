-- ----------------------------
-- Table structure for sys_unit
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_unit` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  NOT NULL ,  `parent_id` varchar(32)  DEFAULT NULL ,  `path` text  NOT NULL,  `leader` varchar(255)  DEFAULT NULL ,  `mobile` varchar(20)  DEFAULT NULL,  `email` varchar(50)  DEFAULT NULL,  `status` tinyint(4) NOT NULL DEFAULT '1' ,  `note` text ,  `order_num` int(10) DEFAULT '0' ,  `who_created` varchar(32)  NOT NULL ,  `when_created` varchar(20)  NOT NULL ,  `who_modified` varchar(32)  NOT NULL ,  `when_modified` varchar(20)  NOT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_user` (  `id` varchar(36)  NOT NULL ,  `username` varchar(50)  NOT NULL ,  `password` varchar(256)  DEFAULT NULL,  `real_name` varchar(50)  NOT NULL ,  `mobile` varchar(20)  DEFAULT NULL ,  `email` varchar(50)  DEFAULT NULL ,  `sex` tinyint(4) DEFAULT NULL ,  `sys_unit_id` varchar(32)  DEFAULT NULL ,  `post` varchar(50)  DEFAULT NULL,  `status` tinyint(4) DEFAULT '1' ,  `note` text ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `deleted` tinyint(4) DEFAULT '0' ,  `avatar` varchar(255)  DEFAULT NULL,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_role` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  NOT NULL ,  `code` varchar(50)  NOT NULL ,  `note` text ,  `status` tinyint(4) DEFAULT NULL ,  `who_created` varchar(32)  NOT NULL ,  `when_created` varchar(20)  NOT NULL ,  `who_modified` varchar(32)  NOT NULL ,  `when_modified` varchar(20)  NOT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)) ;
-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_user_role` (  `id` varchar(36)  NOT NULL ,  `sys_user_id` varchar(32)  NOT NULL ,  `sys_role_id` varchar(32)  NOT NULL ,  `who_created` varchar(32)  NOT NULL ,  `when_created` varchar(20)  NOT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`))  ;
-- ----------------------------
-- Table structure for dev_api
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_api` (  `id` varchar(32)  NOT NULL DEFAULT '' ,  `api_name` varchar(50)  DEFAULT NULL ,  `application_id` varchar(32)  DEFAULT NULL ,  `api_url` varchar(128)  DEFAULT NULL ,  `api_note` text ,  `api_tags` varchar(128)  DEFAULT NULL ,  `api_method` varchar(32)  DEFAULT 'get' ,  `api_argv_type` int(4) DEFAULT NULL ,  `api_req_argv` text  ,  `api_rsp_argv` text  ,  `api_result_handler` varchar(128)  DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL, `when_modified` varchar(20)  DEFAULT NULL ,  `api_flow_id` varchar(50)  DEFAULT NULL ,  `api_code` varchar(50)  DEFAULT NULL,  `call_type` int(4) DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for dev_application
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_application` (  `id` varchar(36)  NOT NULL ,  `name` varchar(100)  DEFAULT NULL ,  `short_name` varchar(30)  NOT NULL ,  `description` varchar(255)  DEFAULT NULL ,  `enable_status` tinyint(1) DEFAULT NULL ,  `dev_status` tinyint(1) DEFAULT NULL,  `version` varchar(50)  DEFAULT NULL,  `who_in_charge` varchar(255)  DEFAULT NULL,  `system_logo` varchar(255)  DEFAULT NULL ,  `app_type` varchar(100)  DEFAULT NULL ,  `default_path` varchar(255)  DEFAULT NULL ,  `deleted` tinyint(1) NOT NULL DEFAULT '0' ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(36)  DEFAULT NULL ,  `faas_port` int(11) DEFAULT NULL ,  `pine_port` int(11) DEFAULT NULL ,  `data_source` varchar(1000)  DEFAULT NULL ,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for dev_application_version_history
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_application_version_history` (  `id` varchar(36)  NOT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  `version` varchar(50)  DEFAULT NULL,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for dev_document
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_document` (  `id` varchar(255)  NOT NULL ,  `name` varchar(255)  DEFAULT NULL ,  `path` varchar(255)  DEFAULT NULL ,  `content` mediumtext  ,  `parent_id` varchar(255)  DEFAULT NULL ,  `order` int(4) NOT NULL AUTO_INCREMENT ,  `when_created` date DEFAULT NULL ,  `who_created` varchar(255) DEFAULT NULL ,  `deleted` tinyint(4) DEFAULT '0',  PRIMARY KEY (`order`)) ;
-- ----------------------------
-- Table structure for dev_page
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_page` (  `id` varchar(36)  NOT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  `who_modified` varchar(36)  DEFAULT NULL ,  `deleted` tinyint(4) DEFAULT '0' ,  `app_id` varchar(36)  DEFAULT NULL ,  `name` varchar(255)  DEFAULT NULL ,  `description` varchar(255)  DEFAULT NULL ,  `path` varchar(255)  DEFAULT NULL ,  `app_type` varchar(100)  DEFAULT NULL ,  `login_required` tinyint(1) DEFAULT NULL ,  `enable_status` tinyint(1) DEFAULT NULL ,  `dev_status` tinyint(1) DEFAULT NULL,  `page_json` longtext,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for dev_page_history
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_page_history` (  `id` varchar(36)  NOT NULL ,  `page_id` varchar(36)  DEFAULT NULL ,  `page_json` longtext ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for dev_sql_run
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_sql_run` (  `id` varchar(36)  NOT NULL ,  `version` int(11) NOT NULL ,  `md5` varchar(100)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `execution_time` int(11) DEFAULT NULL ,  `success` tinyint(4) NOT NULL ,  PRIMARY KEY (`id`)) ;
-- ----------------------------
-- Table structure for dev_sql_script
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_sql_script` (  `sql` longtext  NOT NULL ,  `description` varchar(255)  DEFAULT NULL ,  `version` int(11) NOT NULL AUTO_INCREMENT ,  `is_once` tinyint(4) NOT NULL DEFAULT '1' ,  PRIMARY KEY (`version`));
-- ----------------------------
-- Table structure for dev_team
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_team` (  `id` varchar(36)  NOT NULL ,  `name` varchar(100)  NOT NULL ,  `owner` varchar(36)  NOT NULL ,  `description` varchar(255)  DEFAULT NULL ,  `deleted` tinyint(1) NOT NULL DEFAULT '0' ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)) ;
-- ----------------------------
-- Table structure for dev_team_app
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_team_app` (  `id` varchar(36)  NOT NULL ,  `team_id` varchar(36)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  `team_type` tinyint(1) NOT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
-- ----------------------------
-- Table structure for dev_team_member
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_team_member` (  `id` varchar(36)  NOT NULL ,  `team_id` varchar(36)  NOT NULL ,  `user_id` varchar(36)  NOT NULL ,  `when_join` timestamp NULL DEFAULT NULL ,  `who_invite` varchar(36)  DEFAULT NULL ,  `team_role_id` varchar(36)  DEFAULT NULL ,  `is_owner` tinyint(1) DEFAULT NULL ,  `app_id` varchar(255)  DEFAULT NULL ,  PRIMARY KEY (`id`));
-- ----------------------------
-- Table structure for dev_topological
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_topological` (  `id` varchar(36)  NOT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `who_created` varchar(36)  DEFAULT NULL ,  `who_modified` varchar(36)  DEFAULT NULL ,  `deleted` tinyint(4) DEFAULT '0' ,  `app_id` varchar(36)  DEFAULT NULL ,  `name` varchar(255)  DEFAULT NULL ,  `description` varchar(255)  DEFAULT NULL,  `page_json` longtext  ,  `enable_status` tinyint(1) DEFAULT NULL ) ;
-- ----------------------------
-- Table structure for dev_view_model
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_view_model` (  `id` varchar(36)  NOT NULL ,  `name` varchar(50)  DEFAULT NULL ,  `note` text  ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `deleted` int(11) DEFAULT '0' ,  `tag` varchar(100)  DEFAULT NULL,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for dev_view_model_field
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_view_model_field` (  `id` varchar(32)  NOT NULL ,  `view_model_id` varchar(32)  DEFAULT NULL ,  `field` varchar(50)  DEFAULT NULL ,  `label` varchar(50)  DEFAULT NULL ,  `type` varchar(20)  DEFAULT NULL ,  `format_type` varchar(20)  DEFAULT NULL,  `format_pattern` varchar(50)  DEFAULT NULL ,  `default_text` varchar(50)  DEFAULT NULL,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `hidden` int(11) DEFAULT '0' ,  `order_num` int(11) DEFAULT '0' ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)) ;
-- ----------------------------
-- Table structure for dev_view_model_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS`dev_view_model_flow` (  `id` varchar(32)  NOT NULL,  `flow_id` varchar(36)  DEFAULT NULL,  `view_model_id` varchar(32)  DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_api` (  `id` varchar(32)  NOT NULL DEFAULT '' ,  `api_name` varchar(50)  DEFAULT NULL ,  `api_url` varchar(128)  DEFAULT NULL ,  `api_note` text ,  `api_tags` varchar(128)  DEFAULT NULL ,  `api_method` varchar(32)  DEFAULT 'get' ,  `api_argv_type` int(4) DEFAULT NULL ,  `api_req_argv` text ,  `api_rsp_argv` text  ,  `api_result_handler` varchar(128)  DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `api_flow_id` varchar(50)  DEFAULT NULL ,  `api_code` varchar(50)  DEFAULT NULL ,  `call_type` int(4) DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_config` (  `id` varchar(36)  NOT NULL ,  `name` varchar(255)  DEFAULT NULL ,  `code` varchar(255)  DEFAULT NULL ,  `value` varchar(255)  DEFAULT NULL ,  `is_sys` tinyint(4) DEFAULT NULL ,  `note` varchar(255)  DEFAULT NULL,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `value_type` tinyint(4) DEFAULT '0',  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)) ;
-- ----------------------------
-- Table structure for sys_data_access
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_data_access` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  DEFAULT NULL ,  `status` tinyint(4) DEFAULT NULL ,  `note` text ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_data_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_data_resource` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  DEFAULT NULL ,  `table_name` varchar(50)  DEFAULT NULL ,  `label_field` varchar(50)  DEFAULT NULL ,  `value_field` varchar(50)  DEFAULT NULL ,  `query_sql` text  ,  `is_tree` tinyint(4) DEFAULT NULL ,  `is_only_leaf` tinyint(4) DEFAULT NULL ,  `status` tinyint(4) DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `extra_sql` text  ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_data_access_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_data_access_resource` (  `id` varchar(32)  NOT NULL ,  `data_id` varchar(36)  DEFAULT NULL ,  `access_id` varchar(36)  DEFAULT NULL ,  `table_name` varchar(50)  DEFAULT NULL,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_data_access_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_data_access_user` (  `id` varchar(32)  NOT NULL ,  `sys_user_id` varchar(32)  DEFAULT NULL ,  `sys_data_access_id` varchar(50)  DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) );

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_dict` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  DEFAULT NULL ,  `code` varchar(50)  DEFAULT NULL ,  `note` text ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)  ) ;
-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_dict_item` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  DEFAULT NULL ,  `group_name` varchar(50)  DEFAULT NULL ,  `sys_dict_id` varchar(32)  DEFAULT NULL ,  `code` varchar(50)  DEFAULT NULL ,  `value` varchar(20)  DEFAULT NULL ,  `order_num` int(8) DEFAULT NULL,  `note` text ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`)  ) ;
-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_file` (  `id` varchar(32)  NOT NULL ,  `file_name` varchar(100)  DEFAULT NULL ,  `file_original_name` varchar(100)  DEFAULT NULL ,  `file_size` int(11) DEFAULT NULL,  `file_ext` text  ,  `file_md5` varchar(40)  DEFAULT NULL ,  `file_from` varchar(50)  DEFAULT NULL ,  `save_type` int(4) DEFAULT NULL ,`file_path` varchar(100)  DEFAULT NULL ,  `file_content` longtext ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for sys_logic_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_logic_flow` (  `id` varchar(36)  NOT NULL ,  `name` varchar(255)  DEFAULT NULL ,  `flow_id` varchar(36)  DEFAULT NULL ,  `application_id` varchar(32)  DEFAULT NULL ,  `tags` varchar(255)  DEFAULT NULL ,  `in_argv` text  ,  `out_argv` text ,  `sub_flow_ids` text,    `note` varchar(255)  DEFAULT NULL,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_login_log` (  `id` varchar(32)  NOT NULL ,  `operate_time` varchar(20) DEFAULT NULL ,  `operator` varchar(32)  DEFAULT NULL,  `ip` varchar(20)  DEFAULT NULL ,  `times` int(10) DEFAULT NULL ,  `response_code` int(11) DEFAULT NULL ,  `response_message` varchar(100)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL);
-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_menu` (  `id` varchar(32)  NOT NULL ,  `name` varchar(50)  NOT NULL ,  `parent_id` varchar(32)  DEFAULT NULL ,  `icon` varchar(50)  DEFAULT NULL,  `code` varchar(50)  DEFAULT NULL ,  `router_path` varchar(255)  DEFAULT NULL ,  `component_path` varchar(255)  DEFAULT NULL ,  `is_hidden` tinyint(4) NOT NULL DEFAULT '0' ,  `menu_type` char(1)  NOT NULL DEFAULT '0' ,  `api_codes` varchar(255)  DEFAULT NULL ,  `open_mode` tinyint(4) NOT NULL ,  `keep_alive` tinyint(4) DEFAULT NULL ,  `path` text  NOT NULL ,  `order_num` int(4) NOT NULL DEFAULT '0',  `status` tinyint(4) NOT NULL DEFAULT '1' ,  `who_created` varchar(32)  NOT NULL ,  `when_created` varchar(20)  NOT NULL ,  `who_modified` varchar(32)  NOT NULL ,  `when_modified` varchar(20)  NOT NULL ,  `app_id` varchar(36)  DEFAULT NULL,  `data_type` tinyint(2) DEFAULT NULL,  `theme` varchar(50)  DEFAULT NULL ,  `page_type` tinyint(2) DEFAULT NULL,  `sidebar_nav_mode` tinyint(2) DEFAULT NULL ,  `top_nav_mode` tinyint(2) DEFAULT NULL ,  `main_mode` tinyint(2) DEFAULT NULL ,  `page_id` varchar(36)  DEFAULT NULL ,  `full_path` varchar(255)  DEFAULT NULL ,  `is_dev` tinyint(1) DEFAULT '0',  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_notice` (  `id` varchar(36)  NOT NULL ,  `title` varchar(255)  DEFAULT NULL ,  `content` text ,  `type` tinyint(2) DEFAULT NULL ,  `status` tinyint(2) NOT NULL DEFAULT '1' ,  `who_created` varchar(36)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(36)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `deleted` tinyint(1) DEFAULT NULL ,`app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for sys_notice_record
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_notice_record` (  `id` varchar(36)  NOT NULL ,  `from_who` varchar(36)  DEFAULT NULL ,  `to_who` varchar(36)  NOT NULL ,  `notice_id` varchar(36)  DEFAULT NULL ,  `is_read` tinyint(2) NOT NULL DEFAULT '0' ,  `read_time` timestamp NULL DEFAULT NULL ,  `notice_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,  `title` varchar(255)  DEFAULT NULL ,  `content` text ,  `to_who_name` varchar(255)  DEFAULT NULL ,  `from_who_name` varchar(255)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) );
-- ----------------------------
-- Table structure for sys_online_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_online_user` (  `id` varchar(32)  NOT NULL ,  `user_id` varchar(32)  DEFAULT NULL ,  `login_time` timestamp NULL DEFAULT NULL ,  `login_ip` varchar(20)  DEFAULT NULL,  `login_token` varchar(1024)  DEFAULT NULL ,  `expire_time` timestamp NULL DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_operate_log` (  `id` varchar(32)  NOT NULL ,  `module` varchar(100)  DEFAULT NULL ,  `action` varchar(255)  DEFAULT NULL ,  `url` varchar(255)  DEFAULT NULL ,  `operate_time` varchar(20) DEFAULT NULL ,  `operator` varchar(32)  DEFAULT NULL,  `ip` varchar(20)  DEFAULT NULL ,  `times` int(10) DEFAULT NULL ,  `request_body` text  ,  `response_code` int(11) DEFAULT NULL ,  `response_message` varchar(100)  DEFAULT NULL ,  `when_created` varchar(20)  DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`));
-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_role_menu` (  `id` varchar(32)  NOT NULL ,  `sys_menu_id` varchar(32)  NOT NULL ,  `sys_role_id` varchar(50)  NOT NULL ,  `who_created` varchar(32)  NOT NULL ,  `when_created` varchar(20)  NOT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_task` (  `id` varchar(32)  NOT NULL ,  `name` varchar(100)  DEFAULT NULL  ,  `cron` varchar(50)  DEFAULT NULL ,  `distributed` tinyint(4) DEFAULT NULL ,  `application_id` varchar(32)  DEFAULT NULL ,  `task_type` tinyint(4) DEFAULT '1' ,  `task_resource_id` varchar(36)  DEFAULT NULL ,  `class_name` varchar(255)  DEFAULT NULL ,  `enable` tinyint(4) DEFAULT '1' ,  `last_execute_status` tinyint(4) DEFAULT NULL ,  `last_execute_msg` text ,  `last_execute_time` timestamp NULL DEFAULT NULL,  `last_execute_take` int(11) DEFAULT NULL ,  `lock_status` tinyint(4) DEFAULT '0' ,  `lock_for_most` int(8) DEFAULT '30',  `lock_for_least` int(8) DEFAULT '1' ,  `lock_for_time` timestamp NULL DEFAULT NULL ,  `note` text ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `app_id` varchar(36)  DEFAULT NULL ,  PRIMARY KEY (`id`) ) ;
-- ----------------------------
-- Table structure for sys_view_model
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_view_model` (  `id` varchar(36)  NOT NULL ,  `name` varchar(50)  DEFAULT NULL ,  `note` text  ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `deleted` int(11) DEFAULT '0' ,  `tag` varchar(100)  DEFAULT NULL ,  PRIMARY KEY (`id`)) ;
-- ----------------------------
-- Table structure for sys_view_model_field
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_view_model_field` (  `id` varchar(32)  NOT NULL ,  `view_model_id` varchar(32)  DEFAULT NULL ,  `field` varchar(50)  DEFAULT NULL ,  `label` varchar(50)  DEFAULT NULL ,  `type` varchar(20)  DEFAULT NULL ,  `format_type` varchar(20)  DEFAULT NULL ,  `format_pattern` varchar(50)  DEFAULT NULL ,  `default_text` varchar(50)  DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  `hidden` int(11) DEFAULT '0' ,  `order_num` int(11) DEFAULT '0' );
-- ----------------------------
-- Table structure for sys_view_model_flow
-- ----------------------------
CREATE TABLE IF NOT EXISTS`sys_view_model_flow` (  `id` varchar(32)  NOT NULL,  `flow_id` varchar(36)  DEFAULT NULL,  `view_model_id` varchar(32)  DEFAULT NULL ,  `who_created` varchar(32)  DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `who_modified` varchar(32)  DEFAULT NULL ,  `when_modified` timestamp NULL DEFAULT NULL ,  PRIMARY KEY (`id`)) ;
delete from sys_user;
delete from sys_role;
delete from sys_user_role;
delete from sys_menu;
delete from sys_dict;
delete from sys_dict_item;
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `mobile`, `email`, `sex`, `sys_unit_id`, `post`, `status`, `note`, `who_created`, `when_created`, `who_modified`, `when_modified`, `deleted`, `avatar`, `app_id`) VALUES ('056fb0eeb9a44cb0953534b4c0ca01fa', 'admin', 'MTIzNDU2', '超级管理员', NULL, NULL, 1, NULL, NULL, 1, NULL, '', '2021-12-29 16:36:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:58:32', 0, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL);
INSERT INTO `sys_role` (`id`, `name`, `code`, `note`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`) VALUES ('10d26189026a4dba86a8e63a4c717ed6', '超级管理员', 'admin', '应用超级管理员', 1, '', '2021-12-28 15:22:56', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:41', NULL);
INSERT INTO `sys_user_role` (`id`, `sys_user_id`, `sys_role_id`, `who_created`, `when_created`, `app_id`) VALUES ('b5f79a7cc794423e843a2b1fd9a27007', '056fb0eeb9a44cb0953534b4c0ca01fa', '10d26189026a4dba86a8e63a4c717ed6', '', '2022-03-10 06:31:39', NULL);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('c2348bbf343a47b5852f12ee32869b13', '基础信息', NULL, 'documentation', 'system-info', 'sys/info', NULL, 0, 'M', '', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('1a246429b14e4db2be0e1847a3939e98', '菜单管理', '843af15ab7694d54af793e4a5e6fb76e', 'nested', 'menu', 'menu', '/sys/menu/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/1a246429b14e4db2be0e1847a3939e98/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/menu', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('843af15ab7694d54af793e4a5e6fb76e', '系统配置', NULL, 'system', 'system-config', 'sys/conf', NULL, 0, 'M', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('58f5f98c57c74a91b6c2ca24c5df0ba9', '用户管理', 'c2348bbf343a47b5852f12ee32869b13', 'user', 'user', 'user/index', '/sys/user/index', 0, 'C', 'sys:user:list', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/58f5f98c57c74a91b6c2ca24c5df0ba9/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:28:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/user/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('611b9195b7ce4b3fb37f41023a907bda', '角色管理', 'c2348bbf343a47b5852f12ee32869b13', 'peoples', 'role', 'role/index', '/sys/role/index', 0, 'C', NULL, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/611b9195b7ce4b3fb37f41023a907bda/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/role/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('49d3319c02e542db9db32a6491193348', '字典管理', '843af15ab7694d54af793e4a5e6fb76e', 'dict', 'dictionary', 'dict/index', '/sys/dict/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193348/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:38:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('49d3319c02e542db9db32a6491193349', '字典数据', '843af15ab7694d54af793e4a5e6fb76e', '', 'dictionary-item', 'dict-item/index', '/sys/dictItem/index', 1, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193349/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2021-12-30 15:32:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict-item/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('a17e9c809f1049668633d8fe6103e740', '系统配置管理', '843af15ab7694d54af793e4a5e6fb76e', 'swagger', 'config', 'config', '/sys/config/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/a17e9c809f1049668633d8fe6103e740/', 7, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:41:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/config', 0);
INSERT INTO sys_dict (id, name, code, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('27906b13c5c0484396e284a4368b24b3', '参数类型', 'sys_config_value_type', NULL, '', '2022-01-17 17:36:40', '', '2022-01-17 17:36:40', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, `value`, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('53fa72c48f4c4324bd36733fe871b242', '文本', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '0', 1, NULL, '', '2022-01-17 17:36:59', '', '2022-01-17 17:36:59', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, `value`, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('98dba411055b460c913ab5454a6718c9', '图片', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '1', 2, NULL, '', '2022-01-17 17:37:07', '', '2022-01-17 17:37:07', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, `value`, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('a988ca46b6b445fc8f36d8b9d1243cb8', '颜色', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '2', 3, NULL, '', '2022-01-17 17:37:15', '', '2022-01-17 17:37:15', NULL);

