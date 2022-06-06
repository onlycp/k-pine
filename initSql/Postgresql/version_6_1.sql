CREATE TABLE IF NOT EXISTS `sys_logic_history` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'ID',
    `flow_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '流程ID',
    `flow_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '流程JSON',
    `when_created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `who_created` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
    );