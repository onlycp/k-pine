CREATE TABLE IF NOT EXISTS  `sys_i18n`
(
    `id`            varchar(36)  not null,
    `i18n_key`      VARCHAR(255) not null,
    `message`       TEXT         null ,
    `app_id`        varchar(36)  null  ,
    `when_created`  VARCHAR(20)  null ,
    `who_created`   varchar(36)  null  ,
    `when_modified` VARCHAR(20)  null,
    `who_modified`  varchar(36)  null ,
    primary key (`id`)
);


alter table `sys_api`
    add `module_id` VARCHAR(36) null ;