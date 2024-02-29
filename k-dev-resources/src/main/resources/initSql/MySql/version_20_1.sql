CREATE TABLE IF NOT EXISTS `dev_sql_run`  (
                                              `id` varchar(36) NOT NULL COMMENT 'id',
                                              `version` int NOT NULL COMMENT '关联版本号',
                                              `md5` varchar(100) NULL DEFAULT NULL COMMENT 'md5',
                                              `when_created` varchar(30) NULL DEFAULT NULL COMMENT '执行时间',
                                              `execution_time` int NULL DEFAULT NULL COMMENT '执行时长（毫秒）',
                                              `success` tinyint NOT NULL COMMENT '是否成功',
                                              PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_api`  (
                                          `id` varchar(36) NOT NULL,
                                          `api_argv_type` int NULL DEFAULT NULL COMMENT '参数组装方式',
                                          `api_code` varchar(50) NULL DEFAULT NULL COMMENT '接口编码',
                                          `api_flow_id` varchar(50) NULL DEFAULT NULL COMMENT '流程ID',
                                          `api_method` varchar(36) NULL DEFAULT 'get' COMMENT '请求方式',
                                          `api_name` varchar(50) NULL DEFAULT NULL COMMENT '接口名称',
                                          `api_note` text NULL COMMENT '接口描述',
                                          `api_req_argv` text NULL COMMENT '请求参数',
                                          `api_result_handler` varchar(128) NULL DEFAULT NULL COMMENT '结果处理类',
                                          `api_rsp_argv` text NULL COMMENT '响应结果',
                                          `api_tags` varchar(128) NULL DEFAULT NULL COMMENT '标签',
                                          `api_url` varchar(128) NULL DEFAULT NULL COMMENT '接口路径',
                                          `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                          `application_id` varchar(36) NULL DEFAULT NULL COMMENT '归属应用ID',
                                          `call_type` int NULL DEFAULT NULL COMMENT '调试方式',
                                          `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                          `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                          `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                          PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_application`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_public_type` int NULL DEFAULT 0 COMMENT '应用开启类型： 0：普通应用，1：公共库应用，2：系统库应用',
                                                  `app_type` varchar(100) NULL DEFAULT NULL COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
                                                  `data_source` varchar(1000) NULL DEFAULT NULL COMMENT '数据源配置',
                                                  `default_path` varchar(255) NULL DEFAULT NULL COMMENT '默认路径',
                                                  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否已删除',
                                                  `description` varchar(255) NULL DEFAULT NULL COMMENT '应用介绍',
                                                  `dev_status` tinyint(1) NULL DEFAULT NULL COMMENT '开发状态，0: 新建1: 确认版本2: 有更新',
                                                  `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
                                                  `faas_port` int NULL DEFAULT NULL COMMENT 'faas端口号',
                                                  `name` varchar(100) NULL DEFAULT NULL COMMENT '应用名',
                                                  `pine_port` int NULL DEFAULT NULL COMMENT '青松端口号',
                                                  `short_name` varchar(30) NULL DEFAULT NULL COMMENT '应用短英文名（用于数据库等前缀命名），- 必须唯一，- 必须为纯英文、小写字母命名- 用于创建业务目录、数据库，',
                                                  `system_logo` varchar(255) NULL DEFAULT NULL COMMENT '应用图标',
                                                  `version` varchar(50) NULL DEFAULT NULL COMMENT '当前发布版本',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_in_charge` varchar(255) NULL DEFAULT NULL COMMENT '负责人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_application_version_history`  (
                                                                  `id` varchar(36) NOT NULL,
                                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                                                  `export_data` text NULL COMMENT '导出数据参数',
                                                                  `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名',
                                                                  `note` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                                                  `version` varchar(50) NULL DEFAULT NULL COMMENT '版本',
                                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_document`  (
                                               `id` varchar(36) NOT NULL,
                                               `content` mediumtext NULL COMMENT '正文',
                                               `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0:正常 1：删除',
                                               `name` varchar(255) NULL DEFAULT NULL COMMENT '标题',
                                               `order` int NULL DEFAULT NULL COMMENT '目录排序',
                                               `parent_id` varchar(255) NULL DEFAULT NULL COMMENT '父节点id',
                                               `path` varchar(255) NULL DEFAULT NULL COMMENT '文件路径',
                                               `when_created` date NULL DEFAULT NULL COMMENT '创建时间',
                                               `who_created` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_module`  (
                                             `id` varchar(36) NOT NULL,
                                             `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                             `has_path` int NULL DEFAULT NULL COMMENT '是否有路径',
                                             `is_sys` int NULL DEFAULT NULL COMMENT '是否系统',
                                             `name` varchar(100) NULL DEFAULT NULL COMMENT '名称',
                                             `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '父节点',
                                             `path` varchar(255) NULL DEFAULT NULL COMMENT '路径',
                                             `sort` int NULL DEFAULT NULL COMMENT '排序',
                                             `when_created` varchar(50) NULL DEFAULT NULL COMMENT '创建时间',
                                             `when_modified` varchar(50) NULL DEFAULT NULL COMMENT '修改时间',
                                             `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                             `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                             PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_ota_channel`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `auth_token` varchar(50) NULL DEFAULT NULL COMMENT '安全令牌',
                                                  `channel_name` varchar(50) NULL DEFAULT NULL COMMENT '通道名称',
                                                  `channel_url` varchar(100) NULL DEFAULT NULL COMMENT '服务器地址',
                                                  `master` int NULL DEFAULT 0 COMMENT '是否主通道',
                                                  `note` varchar(255) NULL DEFAULT NULL COMMENT '备注信息',
                                                  `sign_secret` varchar(50) NULL DEFAULT NULL COMMENT '签名密钥',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_page`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                           `app_type` varchar(100) NULL DEFAULT NULL COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
                                           `deleted` tinyint NULL DEFAULT 0 COMMENT '删除标识',
                                           `description` varchar(255) NULL DEFAULT NULL COMMENT '页面介绍',
                                           `dev_status` tinyint(1) NULL DEFAULT NULL COMMENT '开发状态',
                                           `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
                                           `login_required` tinyint(1) NULL DEFAULT NULL COMMENT '是否需要登录才可以访问',
                                           `name` varchar(255) NULL DEFAULT NULL COMMENT '页面名称',
                                           `page_json` longtext NULL COMMENT '页面数据化JSON',
                                           `path` varchar(255) NULL DEFAULT NULL COMMENT '访问路径 ',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_page_history`  (
                                                   `id` varchar(36) NOT NULL,
                                                   `page_id` varchar(36) NULL DEFAULT NULL COMMENT '页面ID',
                                                   `page_json` longtext NULL COMMENT '页面JSON',
                                                   `version_tag` varchar(50) NULL DEFAULT NULL COMMENT '版本标签',
                                                   `version_tag_time` varchar(30) NULL DEFAULT NULL COMMENT '版本时间',
                                                   `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                   `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                   PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_pine_plugin`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '归属应用id',
                                                  `author` varchar(50) NULL DEFAULT NULL COMMENT '插件作者',
                                                  `enable_status` int NULL DEFAULT 0 COMMENT '是否启动',
                                                  `file_id` varchar(36) NULL DEFAULT NULL COMMENT '文件id',
                                                  `note` text NULL COMMENT '说明',
                                                  `plugin_name` varchar(50) NULL DEFAULT NULL COMMENT '插件名称',
                                                  `plugin_version` varchar(10) NULL DEFAULT NULL COMMENT '插件版本号',
                                                  `when_created` varchar(36) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_power_link`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `power_id` varchar(36) NULL DEFAULT NULL COMMENT '能力id',
                                                 `power_type` int NULL DEFAULT NULL COMMENT '能力类型 1: 逻辑编排 2:函数 3:kutils 4:逻辑编排模板',
                                                 `tree_id` varchar(36) NULL DEFAULT NULL COMMENT '能力树id',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_power_tree`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `name` varchar(128) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` varchar(255) NULL DEFAULT NULL COMMENT '说明',
                                                 `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '父级id',
                                                 `path` text NULL COMMENT '树路径',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                 PRIMARY KEY (`id`) 
);




CREATE TABLE IF NOT EXISTS `dev_sql_script`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `description` varchar(255) NULL DEFAULT NULL COMMENT '描述',
                                                 `is_once` tinyint NULL DEFAULT 1 COMMENT '是否只执行1次',
                                                 `sql` longtext NULL COMMENT '执行脚本',
                                                 `version` int NULL DEFAULT NULL COMMENT '版本号',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_team`  (
                                           `id` varchar(36) NOT NULL,
                                           `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否已删除',
                                           `description` varchar(255) NULL DEFAULT NULL COMMENT '团队简介',
                                           `name` varchar(100) NULL DEFAULT NULL COMMENT '团队名称',
                                           `owner` varchar(36) NULL DEFAULT NULL COMMENT '团队负责人',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_team_app`  (
                                               `id` varchar(36) NOT NULL,
                                               `app_id` varchar(36) NULL DEFAULT NULL COMMENT '当类型为应用团队时，必须有应用APP_ID',
                                               `team_id` varchar(36) NULL DEFAULT NULL COMMENT '团队ID',
                                               `team_type` tinyint(1) NULL DEFAULT NULL COMMENT '团队类型（1：平台团队，2：应用团队）',
                                               `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                               `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_team_member`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(255) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `is_owner` tinyint(1) NULL DEFAULT NULL COMMENT '是否为团队负责人',
                                                  `team_id` varchar(36) NULL DEFAULT NULL COMMENT '团队ID',
                                                  `team_role_id` varchar(36) NULL DEFAULT NULL COMMENT '团队角色',
                                                  `user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户ID',
                                                  `when_join` varchar(20) NULL DEFAULT NULL COMMENT '加入时间',
                                                  `who_invite` varchar(36) NULL DEFAULT NULL COMMENT '邀请人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_topological`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                                  `deleted` tinyint NULL DEFAULT 0 COMMENT '删除标识',
                                                  `description` varchar(255) NULL DEFAULT NULL COMMENT '页面介绍',
                                                  `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
                                                  `name` varchar(255) NULL DEFAULT NULL COMMENT '页面名称',
                                                  `page_json` longtext NULL COMMENT '页面数据化JSON',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_view_model`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                 `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除',
                                                 `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` text NULL COMMENT '描述',
                                                 `tag` varchar(100) NULL DEFAULT NULL COMMENT '标签，多个用逗号分隔',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_view_model_field`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                       `default_text` varchar(50) NULL DEFAULT NULL COMMENT '默认显示',
                                                       `field` varchar(50) NULL DEFAULT NULL COMMENT '键名',
                                                       `format_pattern` varchar(50) NULL DEFAULT NULL COMMENT '格式规则',
                                                       `format_type` varchar(20) NULL DEFAULT NULL COMMENT '格式类型',
                                                       `hidden` int NULL DEFAULT 0 COMMENT '是否隐藏',
                                                       `label` varchar(50) NULL DEFAULT NULL COMMENT '标签',
                                                       `order_num` int NULL DEFAULT 0 COMMENT '排序',
                                                       `type` varchar(20) NULL DEFAULT NULL COMMENT '数据类型',
                                                       `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                       `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                       `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                       `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                       `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_view_model_flow`  (
                                                      `id` varchar(36) NOT NULL,
                                                      `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                      `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流水ID',
                                                      `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                      `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                      `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                      `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                      `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                      PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `ext_plugin_interface`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `content` text NULL COMMENT '接口使用说明Demo',
                                                       `create_time` varchar(20) NULL DEFAULT NULL,
                                                       `create_user` varchar(255) NULL DEFAULT NULL,
                                                       `deleted` int NULL DEFAULT 0 COMMENT '1:逻辑删',
                                                       `description` varchar(1024) NULL DEFAULT NULL COMMENT '接口描述',
                                                       `name` varchar(255) NULL DEFAULT NULL COMMENT '接口名称',
                                                       `plugin_id` varchar(255) NULL DEFAULT NULL COMMENT '插件id',
                                                       `resp_type` varchar(255) NULL DEFAULT '' COMMENT '返回值类型',
                                                       `update_time` varchar(20) NULL DEFAULT NULL,
                                                       `update_user` varchar(255) NULL DEFAULT NULL,
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `ext_plugin_tree`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `check_time` varchar(20) NULL DEFAULT NULL COMMENT '插件检测时间',
                                                  `clazz_name` varchar(255) NULL DEFAULT NULL COMMENT '调用插件方法名',
                                                  `create_time` varchar(20) NULL DEFAULT NULL,
                                                  `create_user` varchar(255) NULL DEFAULT NULL,
                                                  `description` text NULL COMMENT '描述',
                                                  `ext_name` varchar(255) NULL DEFAULT NULL COMMENT '插件名称',
                                                  `jar_name` varchar(255) NULL DEFAULT NULL COMMENT 'jar包名称',
                                                  `name` varchar(255) NULL DEFAULT NULL COMMENT '中文名',
                                                  `status` int NULL DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
                                                  `type` int NULL DEFAULT NULL COMMENT '1:一级节点；2:二级节点',
                                                  `update_time` varchar(20) NULL DEFAULT NULL,
                                                  `update_user` varchar(255) NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `kfaas_lib`  (
                                            `id` varchar(36) NOT NULL,
                                            `create_time` varchar(20) NULL DEFAULT NULL,
                                            `create_user` varchar(255) NULL DEFAULT NULL,
                                            `jar_name` varchar(255) NULL DEFAULT NULL COMMENT 'jar包名称',
                                            `status` int NULL DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
                                            `update_time` varchar(20) NULL DEFAULT NULL,
                                            `update_user` varchar(255) NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `number_regulation`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `sort` varchar(255) NULL DEFAULT NULL COMMENT '排序',
                                                    `type` varchar(255) NULL DEFAULT NULL COMMENT '规则类型',
                                                    `value` varchar(255) NULL DEFAULT NULL COMMENT '规则值',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `number_serial`  (
                                                `id` varchar(36) NOT NULL,
                                                `create_time` varchar(255) NULL DEFAULT NULL COMMENT '创建时间',
                                                `name` varchar(255) NULL DEFAULT NULL COMMENT '流水号名称',
                                                `number` varchar(255) NULL DEFAULT NULL COMMENT '流水号编号',
                                                `regulationId` varchar(64) NULL DEFAULT NULL COMMENT '规则ID',
                                                `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                                `start_value` varchar(255) NULL DEFAULT NULL COMMENT '开始值',
                                                `step_value` varchar(255) NULL DEFAULT NULL COMMENT '步长值',
                                                `update_time` varchar(255) NULL DEFAULT NULL COMMENT '更新时间',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `open_account`  (
                                               `id` varchar(36) NOT NULL,
                                               `access_id` varchar(36) NULL DEFAULT NULL COMMENT '接入者ID',
                                               `access_name` varchar(128) NULL DEFAULT NULL COMMENT '接入者名称',
                                               `auth_params` text NULL COMMENT '参数配置',
                                               `auth_type` int NULL DEFAULT NULL COMMENT '授权类型1：简单模式，即access_id为access_token, 此时token是固定的',
                                               `invalid_date` varchar(20) NULL DEFAULT NULL COMMENT '失效日期',
                                               `sign_key` varchar(50) NULL DEFAULT NULL COMMENT '签名密钥',
                                               `status` int NULL DEFAULT 1 COMMENT '是否启用',
                                               `valid_date` varchar(20) NULL DEFAULT NULL COMMENT '生效日期',
                                               `validate_sign` int NULL DEFAULT 0 COMMENT '是否验签',
                                               `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                               `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                               `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                               `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `open_account_api`  (
                                                   `id` varchar(36) NOT NULL,
                                                   `account_id` varchar(36) NULL DEFAULT NULL COMMENT '账号id',
                                                   `api_id` varchar(36) NULL DEFAULT NULL COMMENT '接口id',
                                                   `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                   `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                   PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `open_api_log`  (
                                               `id` varchar(36) NOT NULL,
                                               `access_id` varchar(100) NULL DEFAULT NULL COMMENT '接口入商名称',
                                               `api_name` varchar(100) NULL DEFAULT NULL COMMENT '接口名称',
                                               `error_message` varchar(255) NULL DEFAULT NULL COMMENT '错误信息',
                                               `request_ip` varchar(20) NULL DEFAULT NULL COMMENT '请求IP',
                                               `request_params` longtext NULL COMMENT '请求参数',
                                               `request_time` varchar(20) NULL DEFAULT NULL COMMENT '请求时间',
                                               `success` tinyint NULL DEFAULT NULL COMMENT '是否成功',
                                               `use_time` tinyint NULL DEFAULT NULL COMMENT '响应时间(秒)',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `rep_app`  (
                                          `id` varchar(36) NOT NULL,
                                          `app_name` varchar(50) NULL DEFAULT NULL COMMENT '报表名称',
                                          `app_note` varchar(256) NULL DEFAULT NULL COMMENT '报表描述',
                                          `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                          `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                          `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '更新人',
                                          PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `rep_dataset`  (
                                              `id` varchar(36) NOT NULL,
                                              `column_def` text NULL COMMENT '列定义',
                                              `ds_meta` text NULL COMMENT '配置信息',
                                              `ds_name` varchar(50) NULL DEFAULT NULL COMMENT '数据集名称',
                                              `ds_note` varchar(255) NULL DEFAULT NULL COMMENT '描述信息',
                                              `ds_type` int NULL DEFAULT NULL COMMENT '数据集类型 1：sql 2:json 3:excel',
                                              `rep_app_id` varchar(36) NULL DEFAULT NULL COMMENT '报表应用id',
                                              `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                              `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                              `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                              `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                              PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_api`  (
                                          `id` varchar(36) NOT NULL,
                                          `api_argv_type` int NULL DEFAULT NULL COMMENT '参数组装方式',
                                          `api_code` varchar(50) NULL DEFAULT NULL COMMENT '接口编码',
                                          `api_flow_id` varchar(50) NULL DEFAULT NULL COMMENT '流程ID',
                                          `api_method` varchar(36) NULL DEFAULT 'get' COMMENT '请求方式',
                                          `api_name` varchar(50) NULL DEFAULT NULL COMMENT '接口名称',
                                          `api_note` text NULL COMMENT '接口描述',
                                          `api_req_argv` text NULL COMMENT '请求参数',
                                          `api_result_handler` varchar(128) NULL DEFAULT NULL COMMENT '结果处理类',
                                          `api_rsp_argv` text NULL COMMENT '响应结果',
                                          `api_tags` varchar(128) NULL DEFAULT NULL COMMENT '标签',
                                          `api_url` varchar(128) NULL DEFAULT NULL COMMENT '接口路径',
                                          `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                          `call_type` int NULL DEFAULT NULL COMMENT '调试方式',
                                          `module_id` varchar(36) NULL DEFAULT NULL COMMENT '关联模块',
                                          `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                          `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                          `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                          PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_auto_serial`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `auto_num` int NULL DEFAULT NULL COMMENT '当前编号',
                                                  `category` varchar(100) NULL DEFAULT NULL COMMENT '分类',
                                                  `create_time` varchar(20) NULL DEFAULT NULL,
                                                  `create_user` varchar(50) NULL DEFAULT NULL,
                                                  `key` varchar(50) NULL DEFAULT NULL COMMENT '计算方式key',
                                                  `locked` int NULL DEFAULT 0 COMMENT '是否被锁 1：已锁 0：未锁',
                                                  `num_length` int NULL DEFAULT NULL COMMENT '编号长度，不够前面补0',
                                                  `start_num` int NULL DEFAULT NULL COMMENT '初始值',
                                                  `step` int NULL DEFAULT NULL COMMENT '步长',
                                                  `tpl` varchar(100) NULL DEFAULT NULL COMMENT '模板',
                                                  `type` int NULL DEFAULT NULL COMMENT '计算方式1: 按日 2：按月 3:按年',
                                                  `update_time` varchar(20) NULL DEFAULT NULL,
                                                  `update_user` varchar(50) NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_base`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `is_test` tinyint NULL DEFAULT NULL,
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_config`  (
                                             `id` varchar(36) NOT NULL,
                                             `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                             `code` varchar(255) NULL DEFAULT NULL COMMENT '参数键名',
                                             `is_sys` tinyint NULL DEFAULT NULL COMMENT '是否系统内置',
                                             `name` varchar(255) NULL DEFAULT NULL COMMENT '参数名称',
                                             `note` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                             `value` varchar(255) NULL DEFAULT NULL COMMENT '参数键值',
                                             `value_type` tinyint NULL DEFAULT 0,
                                             `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                             `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                             `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                             `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                             PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_access`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                  `note` text NULL COMMENT '备注',
                                                  `status` tinyint NULL DEFAULT NULL COMMENT '启用状态',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_access_resource`  (
                                                           `id` varchar(36) NOT NULL,
                                                           `access_id` varchar(36) NULL DEFAULT NULL COMMENT '编码',
                                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                           `data_id` varchar(36) NULL DEFAULT NULL COMMENT '名称',
                                                           `table_name` varchar(50) NULL DEFAULT NULL COMMENT '备注',
                                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_access_user`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                       `sys_data_access_id` varchar(50) NULL DEFAULT NULL COMMENT '数据访问组id',
                                                       `sys_user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户id',
                                                       `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                       `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_resource`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                    `extra_sql` text NULL COMMENT '权限附加SQL',
                                                    `is_only_leaf` tinyint NULL DEFAULT NULL COMMENT '是否只选树叶',
                                                    `is_tree` tinyint NULL DEFAULT NULL COMMENT '是否树形结构',
                                                    `label_field` varchar(50) NULL DEFAULT NULL COMMENT '标签列',
                                                    `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                    `query_sql` text NULL COMMENT '查询SQL',
                                                    `status` tinyint NULL DEFAULT NULL COMMENT '启用状态',
                                                    `table_name` varchar(50) NULL DEFAULT NULL COMMENT '表名',
                                                    `value_field` varchar(50) NULL DEFAULT NULL COMMENT '值列',
                                                    `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                    `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                    `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                    `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_dict`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_dict_item`  (
                                                `id` varchar(36) NOT NULL,
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                                `group_name` varchar(50) NULL DEFAULT NULL COMMENT '组名',
                                                `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                `note` text NULL COMMENT '备注',
                                                `order_num` int NULL DEFAULT NULL COMMENT '排序',
                                                `sys_dict_id` varchar(36) NULL DEFAULT NULL COMMENT '字典id',
                                                `value` varchar(20) NULL DEFAULT NULL COMMENT '值',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                PRIMARY KEY (`id`) 
);



CREATE TABLE IF NOT EXISTS `sys_excel`  (
                                            `id` varchar(36) NOT NULL,
                                            `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                            `data_from` int NULL DEFAULT NULL COMMENT '1. 普通 2. Excel文件 3. 数据源',
                                            `data_from_id` varchar(36) NULL DEFAULT NULL COMMENT '数据来源ID：如果data_from为2，则为fileId；如果为3，则为datasetId',
                                            `data_json` longtext NULL COMMENT '数据配置',
                                            `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
                                            `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                            `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                            `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                            `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                            PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_file`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `file_content` longtext NULL COMMENT '文件内容，只有存数据库时才有值',
                                           `file_ext` text NULL COMMENT '文件扩展名',
                                           `file_from` varchar(50) NULL DEFAULT NULL COMMENT '文件来源',
                                           `file_md5` varchar(40) NULL DEFAULT NULL COMMENT '文件MD5值',
                                           `file_name` varchar(100) NULL DEFAULT NULL COMMENT '文件名称',
                                           `file_original_name` varchar(100) NULL DEFAULT NULL COMMENT '原始文件名',
                                           `file_path` varchar(100) NULL DEFAULT NULL COMMENT '存储路径',
                                           `file_size` int NULL DEFAULT NULL COMMENT '文件大小(Byte)',
                                           `save_type` int NULL DEFAULT NULL COMMENT '存储方式 0：数据库 1：本地磁盘，2：FAAS储存',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_hint_select`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `code` varchar(100) NULL DEFAULT NULL COMMENT '简称',
                                                  `db_id` varchar(100) NULL DEFAULT NULL COMMENT '数据源',
                                                  `flow_id` varchar(50) NULL DEFAULT NULL COMMENT '逻辑编排,优化级最高',
                                                  `remark` text NULL COMMENT '备注说明',
                                                  `select_fields` text NULL COMMENT '字段说明',
                                                  `select_sql` text NULL COMMENT '下拉SQL',
                                                  `type` varchar(50) NULL DEFAULT NULL COMMENT '类型:0=普通下拉,1=树下拉',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_i18n`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '归属应用ID',
                                           `i18n_key` varchar(255) NULL DEFAULT NULL COMMENT '键名',
                                           `message` text NULL COMMENT '国际化配置信息，JSON保存',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_logic_flow`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                 `application_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                                 `default_source_name` varchar(100) NULL DEFAULT NULL COMMENT '默认数据源',
                                                 `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流程ID',
                                                 `in_argv` text NULL COMMENT '输入参数',
                                                 `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                                 `out_argv` text NULL COMMENT '输出参数',
                                                 `sub_flow_ids` text NULL COMMENT '子流程ID列表',
                                                 `tags` varchar(255) NULL DEFAULT NULL COMMENT '标签',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_logic_history`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流程ID',
                                                    `flow_json` longtext NULL COMMENT '流程JSON',
                                                    `version_tag` varchar(50) NULL DEFAULT NULL COMMENT '版本标签',
                                                    `version_tag_time` varchar(30) NULL DEFAULT NULL COMMENT '版本时间',
                                                    `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                    `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_logic_template`  (
                                                     `id` varchar(36) NOT NULL,
                                                     `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                     `description` text NULL COMMENT '简介',
                                                     `links` longtext NULL COMMENT '连接列表',
                                                     `module_id` varchar(36) NULL DEFAULT NULL COMMENT '关联模块',
                                                     `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
                                                     `nodes` longtext NULL COMMENT '节点列表',
                                                     `when_created` varchar(50) NULL DEFAULT NULL COMMENT '创建时间',
                                                     `when_modified` varchar(50) NULL DEFAULT NULL COMMENT '修改时间',
                                                     `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                     `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                     PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_login_log`  (
                                                `id` varchar(36) NOT NULL,
                                                `ip` varchar(20) NULL DEFAULT NULL COMMENT '来访IP',
                                                `operate_time` varchar(20) NULL DEFAULT NULL COMMENT '访问时间',
                                                `operator` varchar(36) NULL DEFAULT NULL COMMENT '访问人员',
                                                `response_code` int NULL DEFAULT NULL COMMENT '响应码',
                                                `response_message` varchar(100) NULL DEFAULT NULL COMMENT '响应消息',
                                                `times` int NULL DEFAULT NULL COMMENT '耗时(ms)',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_menu`  (
                                           `id` varchar(36) NOT NULL,
                                           `api_codes` varchar(255) NULL DEFAULT NULL COMMENT '接口编码，多个用逗号分隔',
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '所属应用ID',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `component_path` varchar(255) NULL DEFAULT NULL COMMENT '组件路径',
                                           `data_type` tinyint NULL DEFAULT NULL COMMENT '数据类型：0系统，1业务应用, 2开发平台',
                                           `full_path` varchar(255) NULL DEFAULT NULL COMMENT '完整路径（根据层级结构、router_path拼接的完整路径）',
                                           `icon` varchar(50) NULL DEFAULT NULL COMMENT '图标',
                                           `is_dev` tinyint(1) NULL DEFAULT 0 COMMENT '菜单是否为开发者模式菜单',
                                           `is_hidden` tinyint NULL DEFAULT 0 COMMENT '是否隐藏',
                                           `keep_alive` tinyint NULL DEFAULT NULL COMMENT '是否刷新 0: 否 1：是',
                                           `main_mode` tinyint NULL DEFAULT NULL COMMENT '内容区显示样式：0 自动撑开，1 居中',
                                           `menu_type` char(1) NULL DEFAULT '0' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `open_mode` tinyint NULL DEFAULT NULL COMMENT '打开方式 0:页签 1:新窗口',
                                           `order_num` int NULL DEFAULT 0 COMMENT '排序',
                                           `page_id` varchar(36) NULL DEFAULT NULL COMMENT '关联页面',
                                           `page_type` tinyint NULL DEFAULT NULL COMMENT '页面渲染方式：0 Vue， 1 JSON',
                                           `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '上级菜单ID',
                                           `path` text NULL COMMENT '菜单层级关系，自动生成',
                                           `router_path` varchar(255) NULL DEFAULT NULL COMMENT '路由路径',
                                           `sidebar_nav_mode` tinyint NULL DEFAULT NULL COMMENT '侧边菜单显示模式：0 不显示，1 从一级菜单开始显示，2 从二级菜单开始显示, 3 显示二级以下菜单',
                                           `status` tinyint NULL DEFAULT 1 COMMENT '可用状态',
                                           `theme` varchar(50) NULL DEFAULT NULL COMMENT 'layout主题',
                                           `top_nav_mode` tinyint NULL DEFAULT NULL COMMENT '顶部菜单显示模式：0 完全不显示, top_nav_mode1 不显示nav（有header，没有菜单），2 从一级菜单开始显示，3 从二级菜单开始显示',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_mq_channel`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `batch_consumer` int NULL DEFAULT NULL COMMENT '批量消费数',
                                                 `channel_name` varchar(50) NULL DEFAULT NULL COMMENT '通道名称',
                                                 `consumer_group` varchar(50) NULL DEFAULT NULL COMMENT '消费组名',
                                                 `consumer_thread` int NULL DEFAULT NULL COMMENT '消费线程数',
                                                 `enable` int NULL DEFAULT NULL COMMENT '是否启用 0-否 1-是',
                                                 `message_name` varchar(50) NULL DEFAULT NULL COMMENT '消息处理逻辑编排',
                                                 `topic` varchar(50) NULL DEFAULT NULL COMMENT '主题',
                                                 `zk_address` varchar(50) NULL DEFAULT NULL COMMENT 'zk地址',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_notice`  (
                                             `id` varchar(36) NOT NULL,
                                             `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                             `content` text NULL COMMENT '通知内容',
                                             `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
                                             `status` tinyint NULL DEFAULT 1 COMMENT '启用状态：0：待启用，1：已启用',
                                             `title` varchar(255) NULL DEFAULT NULL COMMENT '通知标题',
                                             `type` tinyint NULL DEFAULT NULL COMMENT '通知类型，1：系统维护通知，2：公告',
                                             `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                             `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '更新时间',
                                             `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                             `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '更新人',
                                             PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_notice_record`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                    `content` text NULL COMMENT '通知内容',
                                                    `from_who` varchar(36) NULL DEFAULT NULL COMMENT '发送人',
                                                    `from_who_name` varchar(255) NULL DEFAULT NULL COMMENT '发送人名称',
                                                    `is_read` tinyint NULL DEFAULT 0 COMMENT '是否已读，0：未读，1：已读',
                                                    `notice_id` varchar(36) NULL DEFAULT NULL COMMENT '关联通知',
                                                    `notice_time` varchar(20) NULL DEFAULT NULL COMMENT '接收通知时间',
                                                    `read_time` varchar(20) NULL DEFAULT NULL COMMENT '阅读通知时间',
                                                    `title` varchar(255) NULL DEFAULT NULL COMMENT '通知标题',
                                                    `to_who` varchar(36) NULL DEFAULT NULL COMMENT '接收人',
                                                    `to_who_name` varchar(255) NULL DEFAULT NULL COMMENT '接收人名称',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_online_user`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `expire_time` varchar(20) NULL DEFAULT NULL COMMENT '失效时间',
                                                  `login_ip` varchar(20) NULL DEFAULT NULL COMMENT '登录ip',
                                                  `login_time` varchar(20) NULL DEFAULT NULL COMMENT '登录时间',
                                                  `login_token` varchar(1024) NULL DEFAULT NULL COMMENT '令牌',
                                                  `user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户id',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_operate_log`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `action` varchar(255) NULL DEFAULT NULL COMMENT '动作',
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `ip` varchar(20) NULL DEFAULT NULL COMMENT '来访IP',
                                                  `module` varchar(100) NULL DEFAULT NULL COMMENT '模块',
                                                  `operate_time` varchar(20) NULL DEFAULT NULL COMMENT '访问时间',
                                                  `operator` varchar(36) NULL DEFAULT NULL COMMENT '访问人员',
                                                  `request_body` longtext NULL COMMENT '请求内容体',
                                                  `response_code` int NULL DEFAULT NULL COMMENT '响应码',
                                                  `response_message` varchar(100) NULL DEFAULT NULL COMMENT '响应消息',
                                                  `times` int NULL DEFAULT NULL COMMENT '耗时(ms)',
                                                  `url` varchar(255) NULL DEFAULT NULL COMMENT '路径',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `method` varchar(255) NULL DEFAULT NULL COMMENT '方法名称',
                                                  `request_method` varchar(20) NULL DEFAULT NULL COMMENT '请求方式',
                                                  `response_body` text NULL COMMENT '响应内容体',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_role`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `status` tinyint NULL DEFAULT NULL COMMENT '是否有效 0:否 1：是',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_role_menu`  (
                                                `id` varchar(36) NOT NULL,
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                `sys_menu_id` varchar(36) NULL DEFAULT NULL COMMENT '名称',
                                                `sys_role_id` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_task`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `application_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                           `class_name` varchar(255) NULL DEFAULT NULL COMMENT '类名',
                                           `cron` varchar(50) NULL DEFAULT NULL COMMENT '定时表达式',
                                           `distributed` tinyint NULL DEFAULT NULL COMMENT '是否分布式',
                                           `enable` tinyint NULL DEFAULT 1 COMMENT '启用状态',
                                           `last_execute_msg` text NULL COMMENT '最后执行错误信息',
                                           `last_execute_status` tinyint NULL DEFAULT NULL COMMENT '最后执行状态 1：成功 0：失败',
                                           `last_execute_take` int NULL DEFAULT NULL COMMENT '上次执行消耗',
                                           `last_execute_time` varchar(20) NULL DEFAULT NULL COMMENT '上次执行时间',
                                           `lock_for_least` int NULL DEFAULT 1 COMMENT '最少锁定时长(秒)',
                                           `lock_for_most` int NULL DEFAULT 30 COMMENT '最多锁定时长(秒)',
                                           `lock_for_time` varchar(20) NULL DEFAULT NULL COMMENT '锁定时间',
                                           `lock_status` tinyint NULL DEFAULT 0 COMMENT '锁定状态',
                                           `name` varchar(100) NULL DEFAULT NULL COMMENT '任务名称',
                                           `note` text NULL COMMENT '备注',
                                           `task_resource_id` varchar(36) NULL DEFAULT NULL COMMENT '任务资源ID',
                                           `task_type` tinyint NULL DEFAULT 1 COMMENT '任务类型',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_unit`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `email` varchar(50) NULL DEFAULT NULL COMMENT '电子邮箱',
                                           `leader` varchar(255) NULL DEFAULT NULL COMMENT '负责人',
                                           `mobile` varchar(20) NULL DEFAULT NULL COMMENT '联系电话',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `order_num` int NULL DEFAULT 0 COMMENT '排序 ',
                                           `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '上级单位ID',
                                           `path` text NULL COMMENT '层次关系，自动生成',
                                           `status` tinyint NULL DEFAULT 1 COMMENT '部门状态， 0：停用 1：正常',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_user`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `avatar` varchar(255) NULL DEFAULT NULL,
                                           `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0:正常 1：删除',
                                           `email` varchar(50) NULL DEFAULT NULL COMMENT '电子邮箱',
                                           `mobile` varchar(20) NULL DEFAULT NULL COMMENT '手机号码',
                                           `note` text NULL COMMENT '备注',
                                           `password` varchar(256) NULL DEFAULT NULL COMMENT '密码',
                                           `post` varchar(50) NULL DEFAULT NULL COMMENT '岗位',
                                           `real_name` varchar(50) NULL DEFAULT NULL COMMENT '用户姓名',
                                           `sex` tinyint NULL DEFAULT NULL COMMENT '性别 0:男 1:女',
                                           `status` tinyint NULL DEFAULT 1 COMMENT '状态 1：正常 0：禁用',
                                           `sys_unit_id` varchar(36) NULL DEFAULT NULL COMMENT '归属部门',
                                           `username` varchar(50) NULL DEFAULT NULL COMMENT '用户名',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_user_role`  (
                                                `id` varchar(36) NOT NULL,
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                `sys_role_id` varchar(36) NULL DEFAULT NULL COMMENT '编码',
                                                `sys_user_id` varchar(36) NULL DEFAULT NULL COMMENT '名称',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_user_unit`  (
                                                `id` varchar(36) NOT NULL COMMENT '主键',
                                                `sys_user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                `sys_unit_id` varchar(36) NOT NULL COMMENT '部门ID',
                                                `who_created` varchar(36) NOT NULL COMMENT '创建人员',
                                                `when_created` varchar(20) NOT NULL COMMENT '创建时间',
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_view_model`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除',
                                                 `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` text NULL COMMENT '描述',
                                                 `tag` varchar(100) NULL DEFAULT NULL COMMENT '标签，多个用逗号分隔',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_view_model_field`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `default_text` varchar(50) NULL DEFAULT NULL COMMENT '默认显示',
                                                       `field` varchar(50) NULL DEFAULT NULL COMMENT '键名',
                                                       `format_pattern` varchar(50) NULL DEFAULT NULL COMMENT '格式规则',
                                                       `format_type` varchar(20) NULL DEFAULT NULL COMMENT '格式类型',
                                                       `hidden` int NULL DEFAULT 0 COMMENT '是否隐藏',
                                                       `label` varchar(50) NULL DEFAULT NULL COMMENT '标签',
                                                       `order_num` int NULL DEFAULT 0 COMMENT '排序',
                                                       `type` varchar(20) NULL DEFAULT NULL COMMENT '数据类型',
                                                       `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                       `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                       `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                       `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                       `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_view_model_flow`  (
                                                      `id` varchar(36) NOT NULL,
                                                      `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流水ID',
                                                      `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                      `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                      `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                      `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                      `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                      PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_demo_leave`  (
                                                `id` varchar(36) NOT NULL,
                                                `day` int NULL DEFAULT NULL COMMENT '请假天数',
                                                `proc_inst_id` varchar(36) NULL DEFAULT NULL COMMENT '实例id',
                                                `reason` varchar(255) NULL DEFAULT NULL COMMENT '请假原因',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_category`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `category_name` varchar(50) NULL DEFAULT NULL COMMENT '模块名称',
                                                  `order_num` int NULL DEFAULT NULL COMMENT '排序',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_comment`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `message` varchar(255) NULL DEFAULT NULL COMMENT '意见',
                                                 `proc_inst_id` varchar(36) NULL DEFAULT NULL COMMENT '流程实例ID',
                                                 `task_id` varchar(36) NULL DEFAULT NULL COMMENT '任务ID',
                                                 `task_name` varchar(50) NULL DEFAULT NULL COMMENT '任务名称',
                                                 `type` varchar(20) NULL DEFAULT 'comment' COMMENT '类型',
                                                 `user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户ID',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_node_attribute`  (
                                                        `id` varchar(36) NOT NULL,
                                                        `actions` varchar(100) NULL DEFAULT NULL COMMENT '处理动作权限',
                                                        `exec_mode` varchar(50) NULL DEFAULT NULL COMMENT '执行方式',
                                                        `flow_id` varchar(100) NULL DEFAULT NULL COMMENT '流程ID',
                                                        `form_attribute` text NULL COMMENT '表单属性',
                                                        `msg_send_rule` varchar(100) NULL DEFAULT NULL COMMENT '消息发送策略',
                                                        `next_user` varchar(500) NULL DEFAULT NULL COMMENT '下一步处理人',
                                                        `node_id` varchar(100) NULL DEFAULT NULL COMMENT '节点ID',
                                                        `node_type` varchar(50) NULL DEFAULT NULL COMMENT '节点类型',
                                                        `pass_ok` varchar(50) NULL DEFAULT NULL COMMENT '审核通过要求',
                                                        `person` varchar(50) NULL DEFAULT NULL COMMENT '审核通过要求-任几人',
                                                        `time_out` varchar(50) NULL DEFAULT NULL COMMENT '超时时间(分)',
                                                        `when_created` varchar(50) NULL DEFAULT NULL COMMENT '创建时间',
                                                        `when_modified` varchar(50) NULL DEFAULT NULL COMMENT '更新时间',
                                                        `who_created` varchar(100) NULL DEFAULT NULL COMMENT '创建人',
                                                        `who_modified` varchar(100) NULL DEFAULT NULL COMMENT '更新人',
                                                        PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_procdef`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `category_id` varchar(36) NULL DEFAULT NULL COMMENT '分类ID',
                                                 `content` longtext NULL COMMENT '内容',
                                                 `deploy_md5` varchar(255) NULL DEFAULT NULL COMMENT '已发布版本MD5',
                                                 `deploy_status` tinyint NULL DEFAULT NULL COMMENT '部署状态 0:待发布 1:已发布',
                                                 `deploy_time` varchar(20) NULL DEFAULT NULL COMMENT '最新发布时间',
                                                 `form_key` varchar(50) NULL DEFAULT NULL COMMENT '表单key，一般是表名',
                                                 `form_page_id` varchar(36) NULL DEFAULT NULL COMMENT '表单页面ID',
                                                 `icon` varchar(50) NULL DEFAULT NULL COMMENT '图标',
                                                 `inst_desc` varchar(255) NULL DEFAULT NULL COMMENT '实例描述',
                                                 `order_num` int NULL DEFAULT NULL COMMENT '排序',
                                                 `proc_definition_id` varchar(36) NULL DEFAULT NULL COMMENT '流程定义ID',
                                                 `proc_definition_key` varchar(100) NULL DEFAULT NULL COMMENT '流程定义KEY',
                                                 `proc_name` varchar(100) NULL DEFAULT NULL COMMENT '流程定义名称',
                                                 `proc_version` int NULL DEFAULT NULL COMMENT '版本号',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                 `work_num` varchar(100) NULL DEFAULT NULL COMMENT '工单编号',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_procinst`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `bill_code` varchar(50) NULL DEFAULT NULL COMMENT '工单号',
                                                  `bill_title` varchar(100) NULL DEFAULT NULL COMMENT '工单标题',
                                                  `form_data` text NULL COMMENT '表单数据',
                                                  `main_inst_id` varchar(50) NULL DEFAULT NULL COMMENT '主流程实例ID',
                                                  `main_task_id` varchar(50) NULL DEFAULT NULL COMMENT '主流程任务ID',
                                                  `proc_inst_id` varchar(36) NULL DEFAULT NULL COMMENT '流程实例ID',
                                                  `starter` varchar(36) NULL DEFAULT NULL COMMENT '发起人ID',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  PRIMARY KEY (`id`) 
);


