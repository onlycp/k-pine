CREATE TABLE sys_logic_template_user (
    id varchar(36)  NOT NULL COMMENT 'ID',
    app_id varchar(36)  DEFAULT NULL COMMENT '关联应用',
    when_created varchar(50)  DEFAULT NULL COMMENT '创建时间',
    who_created varchar(36)  DEFAULT NULL COMMENT '创建人',
    template_id varchar(36)  DEFAULT NULL COMMENT ' 组件（模板）ID',
    PRIMARY KEY (id)
) COMMENT='流程模板关联用户表';

alter table sys_logic_template add sys_suggested int(11) null COMMENT '默认常用';
alter table sys_logic_template add publish_status int(11) null COMMENT '默认null/0为公共，1：个人私有，2：应用私有，3：团队私有';


ALTER TABLE dev_page_template ADD snapshot_img_id VARCHAR(32) NULL COMMENT '模板快照图片id，模板快照图片在保存模板时前端保存';
ALTER TABLE dev_page_template ADD order_num INT NULL COMMENT '排序';
ALTER TABLE dev_page_template ADD bg_colors VARCHAR(64) NULL COMMENT '模板展示的背景色';
ALTER TABLE dev_page_template ADD page_type VARCHAR(32) NULL COMMENT '模板页面类型：page;form;flow;report;';
ALTER TABLE dev_page_template ADD use_num INT DEFAULT 0 NULL COMMENT '模板使用人数';
ALTER TABLE dev_page_template ADD extra LONGTEXT NULL COMMENT '用于配置页面参数示例数据JSON';

CREATE TABLE dev_page_template_history (
    id varchar(36) NOT NULL COMMENT 'ID',
    tpl_id varchar(36) DEFAULT NULL COMMENT '页面模板ID',
    page_json longtext COMMENT '页面模板JSON',
    when_created timestamp NULL DEFAULT NULL COMMENT '创建时间',
    who_created varchar(36) DEFAULT NULL COMMENT '创建人',
    version_tag varchar(50) DEFAULT NULL COMMENT '版本标签',
    version_tag_time varchar(30) DEFAULT NULL COMMENT '版本时间',
    app_id varchar(36) DEFAULT NULL COMMENT '应用ID',
    PRIMARY KEY (id),
    KEY dev_page_tpl_history_tpl_id_IDX (tpl_id)
) COMMENT='开发表-页面模板修改历史记录表';

ALTER TABLE dev_faas_node MODIFY COLUMN config text NULL COMMENT '配置文件';
ALTER TABLE dev_faas_node MODIFY COLUMN template varchar(10240) NULL COMMENT '脚本模板';
