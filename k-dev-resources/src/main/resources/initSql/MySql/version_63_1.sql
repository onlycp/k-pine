
ALTER TABLE `dev_page_template` ADD COLUMN `view_num` int DEFAULT '0' COMMENT '预览量' ;
ALTER TABLE `dev_page_template` ADD COLUMN `copy_num` int DEFAULT '0' COMMENT '被复制的次数；copy_num = copy_all_num+copy_part_num' ;
ALTER TABLE `dev_page_template` ADD COLUMN `copy_all_num` int DEFAULT '0' COMMENT '被全量复制的次数' ;
ALTER TABLE `dev_page_template` ADD COLUMN `copy_part_num` int DEFAULT '0' COMMENT '被部分复制的次数' ;

-- `k-pine`.dev_search_history definition

CREATE TABLE `dev_search_history` (
  `id` varchar(36) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键',
  `keyword` varchar(36) CHARACTER SET utf8mb4 NOT NULL COMMENT '关键字',
  `use_num` int DEFAULT NULL COMMENT '搜索次数',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
)  COMMENT='开发表-页面新建的搜索记录';