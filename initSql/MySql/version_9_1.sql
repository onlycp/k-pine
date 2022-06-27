
create table `sys_i18n` (`id` VARCHAR(32) not null,
                         `i18n_key` VARCHAR(255) not null COMMENT '键名',
                         `message` TEXT null COMMENT '国际化配置信息，JSON保存',
                         `app_id` VARCHAR(32) null COMMENT '归属应用ID',
                         `when_created` VARCHAR(20) null COMMENT '创建时间',
                         `who_created` VARCHAR(32) null COMMENT '创建人员',
                         `when_modified` VARCHAR(20) null COMMENT '修改时间',
                         `who_modified` VARCHAR(32) null COMMENT '修改人员',
                         constraint `PK_SYS_I18N` primary key (`id`));


alter table `sys_api` add `module_id` VARCHAR(36) null COMMENT '关联模块';

