
create table `sys_i18n` (`id` VARCHAR(32) not null,
                                  `i18n_key` VARCHAR(255) not null COMMENT '键名',
                                  `message` TEXT null COMMENT '国际化配置信息，JSON保存',
                                  `app_id` VARCHAR(32) null COMMENT '归属应用ID',
                                  `when_created` VARCHAR(20) null COMMENT '创建时间',
                                  `who_created` VARCHAR(32) null COMMENT '创建人员',
                                  `when_modified` VARCHAR(20) null COMMENT '修改时间',
                                  `who_modified` VARCHAR(32) null COMMENT '修改人员',
                                  constraint `PK_SYS_I18N` primary key (`id`));

create table `sys_logic_history` (`id` VARCHAR(36) not null,
                                           `flow_id` VARCHAR(36) null COMMENT '流程ID',
                                           `flow_json` longtext null COMMENT '流程JSON',
                                           `when_created` timestamp null COMMENT '创建时间',
                                           `who_created` VARCHAR(36) null COMMENT '创建人',
                                           constraint `PK_SYS_LOGIC_HISTORY` primary key (`id`));

create table `sys_logic_template` (`id` VARCHAR(36) not null,
                                            `name` VARCHAR(255) null COMMENT '名称',
                                            `module_id` VARCHAR(36) null COMMENT '关联模块',
                                            `description` TEXT null COMMENT '简介',
                                            `nodes` longtext null COMMENT '节点列表',
                                            `links` longtext null COMMENT '连接列表',
                                            `app_id` VARCHAR(36) null COMMENT '关联应用',
                                            `when_created` VARCHAR(50) null COMMENT '创建时间',
                                            `who_created` VARCHAR(36) null COMMENT '创建人',
                                            `when_modified` VARCHAR(50) null COMMENT '修改时间',
                                            `who_modified` VARCHAR(36) null COMMENT '修改人',
                                            constraint `PK_SYS_LOGIC_TEMPLATE` primary key (`id`));

alter table `dev_application_version_history` add `file_name` VARCHAR(255) null COMMENT '文件名';

alter table `dev_application_version_history` add `note` VARCHAR(255) null COMMENT '备注';

alter table `dev_application_version_history` add `export_data` TEXT null COMMENT '导出数据参数';

alter table `sys_logic_flow` add `default_source_name` VARCHAR(100) null COMMENT '默认数据源';

alter table `sys_api` add `module_id` VARCHAR(36) null COMMENT '关联模块';

alter table `dev_application` add `app_public_type` INT default 0 null COMMENT '应用开启类型： 0：普通应用，1：公共库应用，2：系统库应用';

