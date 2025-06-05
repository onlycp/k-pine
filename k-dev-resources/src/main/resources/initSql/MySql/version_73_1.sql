

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
