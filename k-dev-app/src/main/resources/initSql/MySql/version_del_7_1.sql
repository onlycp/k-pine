
ALTER TABLE `dev_application` ADD COLUMN `app_public_type` int NULL DEFAULT 0 COMMENT '应用开启类型： 0：普通应用，1：公共库应用，2：系统库应用' AFTER `data_source`;

CREATE TABLE `dev_module`  (
                                            `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'ID',
                                            `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
                                            `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路径',
                                            `has_path` int NULL DEFAULT NULL COMMENT '是否有路径',
                                            `parent_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父节点',
                                            `sort` int NULL DEFAULT NULL COMMENT '排序',
                                            `when_created` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建时间',
                                            `who_created` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
                                            `when_modified` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改时间',
                                            `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
                                            `is_sys` int NULL DEFAULT NULL COMMENT '是否系统',
                                            `app_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关联应用',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

CREATE TABLE `ext_plugin_interface`  (
                                                      `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                                      `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接口名称',
                                                      `resp_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '返回值类型',
                                                      `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '接口使用说明Demo',
                                                      `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接口描述',
                                                      `pluginId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '插件id',
                                                      `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                                      `createUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                                      `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                      `updateuser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                                      `deleted` int NOT NULL DEFAULT 0 COMMENT '1:逻辑删',
                                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

CREATE TABLE `ext_plugin_tree`  (
                                                 `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                                 `extName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '插件名称',
                                                 `jarName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'jar包名称',
                                                 `type` int NULL DEFAULT NULL COMMENT '1:一级节点；2:二级节点',
                                                 `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                                 `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                 `createUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                                 `updateUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                                 `status` int NULL DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
                                                 `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '中文名',
                                                 `clazzName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '调用插件方法名',
                                                 `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
                                                 `checkTime` timestamp NULL DEFAULT NULL COMMENT '插件检测时间',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

CREATE TABLE `kfaas_lib`  (
                                           `jarName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'jar包名称',
                                           `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                           `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           `createUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                           `updateUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                           `status` int NULL DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
                                           PRIMARY KEY (`jarName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

CREATE TABLE `sys_i18n`  (
                                          `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                          `i18n_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '键名',
                                          `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '国际化配置信息，JSON保存',
                                          `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '归属应用ID',
                                          `when_created` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建时间',
                                          `who_created` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人员',
                                          `when_modified` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_modified` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人员',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统表-国际化配置' ROW_FORMAT = Dynamic;

CREATE TABLE `sys_logic_template`  (
                                                    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
                                                    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
                                                    `module_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关联模块',
                                                    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '简介',
                                                    `nodes` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '节点列表',
                                                    `links` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '连接列表',
                                                    `app_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关联应用',
                                                    `when_created` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建时间',
                                                    `who_created` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
                                                    `when_modified` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改时间',
                                                    `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
                                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;
