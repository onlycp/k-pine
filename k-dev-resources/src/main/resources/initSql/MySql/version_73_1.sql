

-- 以下是来自 version_73_1.sql ,4.1AI库需要的表 的内容 --


CREATE TABLE IF NOT EXISTS `dev_chat_session` (
                                    `session_id` varchar(255) NOT NULL COMMENT '会话唯一标识符（UUID）',
                                    `user_id` varchar(255) NOT NULL COMMENT '用户账户ID',
                                    `title` varchar(255) DEFAULT NULL COMMENT '对话标题',
                                    `is_archived` int(1) DEFAULT '0' COMMENT '是否归档（0: 否，1: 是）',
                                    `when_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
                                    `when_modified` varchar(30) DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`session_id`)
);

CREATE TABLE IF NOT EXISTS `dev_chat_message` (
                                    `message_id` varchar(36) NOT NULL COMMENT '消息唯一标识符（UUID）',
                                    `session_id` varchar(255) NOT NULL COMMENT '会话ID',
                                    `sender` varchar(10) NOT NULL COMMENT '消息发送者（user: 用户，ai: AI）',
                                    `message_content` longtext NOT NULL COMMENT '消息内容',
                                    `message_type` varchar(20) DEFAULT 'text' COMMENT '消息类型（如文本、图片、音频、文件等）',
                                    `args` longtext COMMENT '附加参数信息',
                                    `when_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
                                    `when_modified` varchar(30) DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`message_id`)
);

CREATE TABLE IF NOT EXISTS `dev_online_user` (
                                    `id` varchar(36) NOT NULL COMMENT '规范化唯一ID，主键',
                                    `tab_id` varchar(36) NOT NULL COMMENT '前端生成的唯一 tabId',
                                    `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                    `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
                                    `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
                                    `page_id` varchar(36) NOT NULL COMMENT '页面ID',
                                    `last_heartbeat` varchar(30) DEFAULT NULL COMMENT '最近一次心跳时间',
                                    `when_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
                                    `when_modified` varchar(30) DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`id`)
);
