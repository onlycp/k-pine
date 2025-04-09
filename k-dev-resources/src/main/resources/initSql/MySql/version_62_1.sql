
CREATE TABLE `dev_git_tag` (
  `id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键',
  `tag` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '标签名称',
  `repo` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '仓库地址',
  `resource` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '资源文件',
  `commit_id` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '提交ID',
  `public_commit_ids` text CHARACTER SET utf8mb4  COMMENT '公共依赖库',
  `note` text CHARACTER SET utf8mb4  COMMENT '版本说明',
  `when_created` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建时间',
  `who_created` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
);
-- `k-pine`.dev_model_latest definition

CREATE TABLE `dev_model_latest` (
  `id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键ID',
  `model_name` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '模型名称',
  `source_name` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '数据源名称',
  `version_name` varchar(100) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '修订版本',
  `version_who` varchar(100) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '修订人',
  `version_time` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '修订时间',
  `description` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '备注',
  `diagram` longtext CHARACTER SET utf8mb4  COMMENT '模型数据',
  `inner_version` bigint DEFAULT '0' COMMENT '内部版本号，用于服务端保存时校验',
  `custom_type_mapping` text CHARACTER SET utf8mb4  COMMENT '用户自定义类型映射，当用户导入表出现系统未适配的字段类型时，提醒用户选择要转成什么类型',
  `app_id` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '所属应用ID',
  `who_created` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '更新人',
  `when_modified` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT='开发-模型数据表';

CREATE TABLE `dev_page_template_action_log` (
  `id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键',
  `action_type` int NOT NULL COMMENT '动作类型；1：模板复制；2：模板预览',
  `is_copy_all` tinyint(1) DEFAULT NULL COMMENT '是否全文复制',
  `template_id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '数据id，根据action_type指向不同的表',
  `app_id` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '关联应用',
  `team_id` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '关联团队',
  `action_content` longtext CHARACTER SET utf8mb4  COMMENT '动作关联的内容，例如action_type为模板复制时，content就是复制的内容',
  `who_created` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '创建人员',
  `when_created` varchar(20) CHARACTER SET utf8mb4  NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT='开发表-页面模板操作记录表';


