CREATE TABLE IF NOT EXISTS `open_api_log` (
                                `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                `access_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接口入商名称',
                                `api_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接口名称',
                                `request_params` longtext COLLATE utf8mb4_bin COMMENT '请求参数',
                                `request_time` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求时间',
                                `request_ip` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求IP',
                                `use_time` tinyint DEFAULT NULL COMMENT '响应时间(秒)',
                                `success` tinyint DEFAULT NULL COMMENT '是否成功',
                                `error_message` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '错误信息',
                                PRIMARY KEY (`id`)
);