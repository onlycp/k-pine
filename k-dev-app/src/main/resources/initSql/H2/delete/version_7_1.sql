

CREATE TABLE IF NOT EXISTS `dev_module`
(
    `id`            varchar(36) NOT NULL,
    `name`          varchar(100) NULL DEFAULT NULL,
    `path`          varchar(255) NULL DEFAULT NULL,
    `has_path`      int NULL DEFAULT NULL,
    `parent_id`     varchar(36) NULL DEFAULT NULL,
    `sort`          int NULL DEFAULT NULL,
    `when_created`  varchar(50) NULL DEFAULT NULL,
    `who_created`   varchar(36) NULL DEFAULT NULL,
    `when_modified` varchar(50) NULL DEFAULT NULL,
    `who_modified`  varchar(36) NULL DEFAULT NULL,
    `is_sys`        int NULL DEFAULT NULL,
    `app_id`        varchar(36) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ext_plugin_interface`
(
    `id`          varchar(255) NOT NULL,
    `name`        varchar(255) NULL DEFAULT NULL,
    `resp_type`   varchar(255) NULL DEFAULT '''',
    `content`     text NULL,
    `description` varchar(1024) NULL DEFAULT NULL,
    `pluginId`    varchar(255) NULL DEFAULT NULL,
    `createTime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `createUser`  varchar(255) NULL DEFAULT NULL,
    `updateTime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updateuser`  varchar(255) NULL DEFAULT NULL,
    `deleted`     int          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ext_plugin_tree`
(
    `id`          varchar(255) NOT NULL,
    `extName`     varchar(255) NULL DEFAULT NULL,
    `jarName`     varchar(255) NULL DEFAULT NULL,
    `type`        int NULL DEFAULT NULL,
    `createTime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `createUser`  varchar(255) NULL DEFAULT NULL,
    `updateUser`  varchar(255) NULL DEFAULT NULL,
    `status`      int NULL DEFAULT 0,
    `name`        varchar(255) NULL DEFAULT NULL,
    `clazzName`   varchar(255) NULL DEFAULT NULL,
    `description` varchar(255) NULL DEFAULT NULL,
    `checkTime`   timestamp NULL DEFAULT NULL ,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS `kfaas_lib`
(
    `jarName`    varchar(255) NOT NULL,
    `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `createUser` varchar(255) NULL DEFAULT NULL,
    `updateUser` varchar(255) NULL DEFAULT NULL,
    `status`     int NULL DEFAULT 0,
    PRIMARY KEY (`jarName`)
);

CREATE TABLE IF NOT EXISTS `sys_i18n`
(
    `id`            varchar(36)  NOT NULL,
    `i18n_key`      varchar(255) NOT NULL,
    `message`       text NULL,
    `app_id`        varchar(36) NULL DEFAULT NULL,
    `when_created`  varchar(20) NULL DEFAULT NULL,
    `who_created`   varchar(36) NULL DEFAULT NULL,
    `when_modified` varchar(20) NULL DEFAULT NULL,
    `who_modified`  varchar(36) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `sys_logic_template`
(
    `id`            varchar(36) NOT NULL,
    `name`          varchar(255) NULL DEFAULT NULL,
    `module_id`     varchar(36) NULL DEFAULT NULL,
    `description`   text NULL,
    `nodes`         longtext NULL,
    `links`         longtext NULL,
    `app_id`        varchar(36) NULL DEFAULT NULL,
    `when_created`  varchar(50) NULL DEFAULT NULL,
    `who_created`   varchar(36) NULL DEFAULT NULL,
    `when_modified` varchar(50) NULL DEFAULT NULL,
    `who_modified`  varchar(36) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `dev_application`
    ADD COLUMN `app_public_type` int NULL DEFAULT 0 AFTER `data_source`;
