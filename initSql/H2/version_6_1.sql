CREATE TABLE IF NOT EXISTS `sys_logic_history`
(
    `id`           varchar(36) NOT NULL,
    `flow_id`      varchar(36) DEFAULT NULL,
    `flow_json`    longtext,
    `when_created` timestamp NULL DEFAULT NULL,
    `who_created`  varchar(36) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ;