CREATE TABLE if not exists dev_plugin_api (
                                  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
                                  `group_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组编码',
                                  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
                                  `tags` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
                                  `notes` text COLLATE utf8mb4_bin COMMENT '描述',
                                  `order_num` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '位置',
                                  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                  `when_created` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                  `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
                                  `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
                                  PRIMARY KEY (`id`)
) COMMENT='FAAS接口组';

CREATE TABLE if not exists  dev_plugin_group (
                                    `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                    `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
                                    `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
                                    `notes` text COLLATE utf8mb4_bin COMMENT '描述',
                                    `order_num` int(11) DEFAULT NULL COMMENT '排序',
                                    `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                    `when_created` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                    `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
                                    `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`id`)
);

CREATE TABLE if not exists `dev_plugin_operation` (
                                        `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                        `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
                                        `tags` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
                                        `api_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'API',
                                        `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
                                        `notes` text COLLATE utf8mb4_bin COMMENT '描述',
                                        `cases` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '示例',
                                        `success_resp` text COLLATE utf8mb4_bin COMMENT '成功示例',
                                        `error_resp` text COLLATE utf8mb4_bin COMMENT '失败示例',
                                        `in_params` text COLLATE utf8mb4_bin COMMENT '请求参数 ',
                                        `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                        `when_created` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                        `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
                                        `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
                                        `order_num` int(8) DEFAULT '0' COMMENT '序号',
                                        PRIMARY KEY (`id`)
) ;

INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('a7ce7fa1e3ef4d0080cf06789d43a27b', 'Elasticsearch', 'elasticsearch', 'Elasticsearch操作相关', 9, 'bab66508eefe49ada701257548bbe54a', '2023-06-05 16:07:33', 'bab66508eefe49ada701257548bbe54a', '2023-06-05 16:07:33');
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('b2d366679e774dda9b41aaabca3cf2d7', '流程引擎', 'workflow', '自研的流程引擎', 8, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-05-26 09:30:32', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-05-26 09:30:32');
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g1', 'Office文档操作', 'office', 'Office文件操作', 1, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g2', '接口示例', 'demo', '接口示例', 22, NULL, NULL, '8c0fd6ed3c9f4d48b5a5a7a814b66bac', '2023-07-26 14:20:54');
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g3', '常用操作', 'utils', '常用操作', 3, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g4', '内容存储', 'data', '内容存储', 4, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g5', '数据库', 'db', '数据库', 5, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g6', '网络通信', 'network', '网络通信', 6, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g7', '图像处理', 'image', '图像处理', 7, NULL, NULL, NULL, NULL);

alter table sys_logic_flow add new_flow_json longtext null COMMENT 'v3.1+的流程显示JSON';
alter table sys_logic_history add new_flow_json longtext null COMMENT '流程图JSON';
alter table sys_logic_template add flow_config longtext null COMMENT '配置：图标、颜色、继承、参数等';
alter table sys_logic_template add type int(11) null COMMENT '默认为0，0为旧版，只保存源码数据，需要自行修改，1为新版（自定义节点），加入到流程后不可2次修改';
alter table sys_logic_template add new_flow_json longtext null COMMENT 'v3.1+的流程显示JSON';
