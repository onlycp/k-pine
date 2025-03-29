CREATE TABLE IF NOT EXISTS sys_logic_template_user
(
    id           VARCHAR(36) NOT NULL COMMENT 'ID',
    app_id       VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    when_created VARCHAR(50) DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    template_id  VARCHAR(36) DEFAULT NULL COMMENT '组件（模板）ID',
    PRIMARY KEY (id)
);

ALTER TABLE sys_logic_template
    ADD COLUMN sys_suggested INT DEFAULT NULL COMMENT '默认常用';

ALTER TABLE sys_logic_template
    ADD COLUMN publish_status INT DEFAULT NULL COMMENT '默认null/0为公共，1：个人私有，2：应用私有，3：团队私有';



ALTER TABLE dev_page_template
    ADD COLUMN snapshot_img_id VARCHAR(32) DEFAULT NULL COMMENT '模板快照图片id，模板快照图片在保存模板时前端保存';

ALTER TABLE dev_page_template
    ADD COLUMN order_num INT DEFAULT NULL COMMENT '排序';

ALTER TABLE dev_page_template
    ADD COLUMN bg_colors VARCHAR(64) DEFAULT NULL COMMENT '模板展示的背景色';

ALTER TABLE dev_page_template
    ADD COLUMN page_type VARCHAR(32) DEFAULT NULL COMMENT '模板页面类型：page;form;flow;report;';

ALTER TABLE dev_page_template
    ADD COLUMN use_num INT DEFAULT 0 COMMENT '模板使用人数';

ALTER TABLE dev_page_template
    ADD COLUMN extra TEXT DEFAULT NULL COMMENT '用于配置页面参数示例数据JSON';


CREATE TABLE IF NOT EXISTS dev_page_template_history
(
    id               VARCHAR(36) NOT NULL COMMENT 'ID',
    tpl_id           VARCHAR(36) DEFAULT NULL COMMENT '页面模板ID',
    page_json        TEXT COMMENT '页面模板JSON',
    when_created     TIMESTAMP   DEFAULT NULL COMMENT '创建时间',
    who_created      VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    version_tag      VARCHAR(50) DEFAULT NULL COMMENT '版本标签',
    version_tag_time VARCHAR(30) DEFAULT NULL COMMENT '版本时间',
    app_id           VARCHAR(36) DEFAULT NULL COMMENT '应用ID',
    PRIMARY KEY (id)
);

CREATE INDEX dev_page_tpl_history_tpl_id_IDX ON dev_page_template_history (tpl_id);



ALTER TABLE dev_faas_node
    MODIFY COLUMN config TEXT DEFAULT NULL COMMENT '配置文件';

ALTER TABLE dev_faas_node
    MODIFY COLUMN template TEXT DEFAULT NULL COMMENT '脚本模板';

