CREATE TABLE IF NOT EXISTS  `sys_i18n`
(
    `id`            VARCHAR(32)  not null,
    `i18n_key`      VARCHAR(255) not null,
    `message`       TEXT         null ,
    `app_id`        VARCHAR(32)  null  ,
    `when_created`  VARCHAR(20)  null ,
    `who_created`   VARCHAR(32)  null  ,
    `when_modified` VARCHAR(20)  null,
    `who_modified`  VARCHAR(32)  null ,
    primary key (`id`)
);


alter table `sys_api`
    add `module_id` VARCHAR(36) null ;