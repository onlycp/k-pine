CREATE TABLE if not exists `sys_password_log` (
                                    `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                    `user_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户id',
                                    `when_created` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE if not exists `sys_secret_rule` (
                                   `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                   `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '规则名称',
                                   `logic_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '逻辑编排ID',
                                   `secret_type` int(8) DEFAULT '0' COMMENT '安全类型',
                                   `status` int(8) DEFAULT NULL COMMENT '是否启用',
                                   `order_num` int(11) DEFAULT NULL COMMENT '排序',
                                   `notes` text COLLATE utf8mb4_bin COMMENT '说明',
                                   `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                   `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                   `when_modified` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
                                   `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='密码校验规则';
