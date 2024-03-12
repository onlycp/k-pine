-- 修改 use_time 字段的数据类型为 int
ALTER TABLE open_api_log
ALTER COLUMN use_time TYPE integer;

-- 添加对 use_time 字段的注释
COMMENT ON COLUMN open_api_log.use_time IS '响应时间(秒)';

-- 将 error_message 字段的数据类型修改为 text
ALTER TABLE open_api_log
ALTER COLUMN error_message TYPE text;

-- 添加对 error_message 字段的注释
COMMENT ON COLUMN open_api_log.error_message IS '错误信息';

CREATE TABLE sys_logic_template_user (
                                         id varchar(36)  NOT NULL,
                                         app_id varchar(36)  DEFAULT NULL,
                                         when_created varchar(50)  DEFAULT NULL,
                                         who_created varchar(36)  DEFAULT NULL ,
                                         template_id varchar(36)  DEFAULT NULL,
                                         PRIMARY KEY (id)
);

alter table sys_logic_template add sys_suggested integer null;
alter table sys_logic_template add publish_status integer null;

ALTER TABLE dev_page_template ADD snapshot_img_id VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD order_num integer NULL  ;
ALTER TABLE dev_page_template ADD bg_colors VARCHAR(64) NULL;
ALTER TABLE dev_page_template ADD page_type VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD use_num integer DEFAULT 0 NULL;
ALTER TABLE dev_page_template ADD extra text NULL;

CREATE TABLE dev_page_template_history (
                                           id varchar(36) NOT NULL,
                                           tpl_id varchar(36) DEFAULT NULL,
                                           page_json text,
                                           when_created varchar(50) NULL DEFAULT NULL,
                                           who_created varchar(36) DEFAULT NULL,
                                           version_tag varchar(50) DEFAULT NULL,
                                           version_tag_time varchar(30) DEFAULT NULL,
                                           app_id varchar(36) DEFAULT NULL,
                                           PRIMARY KEY (id)
);

ALTER TABLE dev_faas_node ALTER COLUMN config type text;
ALTER TABLE dev_faas_node ALTER COLUMN template type varchar(10240);

