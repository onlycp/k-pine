-- dev_page_template
ALTER TABLE dev_page_template ADD COLUMN view_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.view_num IS '预览量';

ALTER TABLE dev_page_template ADD COLUMN copy_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.copy_num IS '被复制的次数；copy_num = copy_all_num+copy_part_num';

ALTER TABLE dev_page_template ADD COLUMN copy_all_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.copy_all_num IS '被全量复制的次数';

ALTER TABLE dev_page_template ADD COLUMN copy_part_num int8 DEFAULT 0;
COMMENT ON COLUMN dev_page_template.copy_part_num IS '被部分复制的次数';

-- /* dev_sql_script */
ALTER TABLE dev_sql_script ADD COLUMN id varchar(36) NOT NULL;

-- /* sys_notice */
ALTER TABLE sys_notice ADD COLUMN is_force int2;
COMMENT ON COLUMN sys_notice.is_force IS '是否重要 0: 否 1：是';

ALTER TABLE sys_notice ADD COLUMN config text;
COMMENT ON COLUMN sys_notice.config IS '团队头像';

/* rep_dataset */
ALTER TABLE rep_dataset ADD COLUMN dataset_search_id varchar(32);
COMMENT ON COLUMN rep_dataset.dataset_search_id IS '是否为搜索数据';

ALTER TABLE rep_dataset ADD COLUMN shape int8;
COMMENT ON COLUMN rep_dataset.shape IS '是否为自定义SQL';

ALTER TABLE rep_dataset ADD COLUMN "template" int8;
COMMENT ON COLUMN rep_dataset."template" IS '是否为Excel模板（0：否，1：是）';

ALTER TABLE rep_dataset ADD COLUMN rep_cron varchar(50);
COMMENT ON COLUMN rep_dataset.rep_cron IS '模板报表定时任务Cron表达式';

/* sys_auth_source */
ALTER TABLE sys_auth_source ADD COLUMN whocreated varchar(36);

/* sys_config */
ALTER TABLE sys_config ADD COLUMN group_id varchar(36);
COMMENT ON COLUMN sys_config.group_id IS '关联组';

/* open_api_log */
ALTER TABLE open_api_log ADD COLUMN api_id varchar(36);
COMMENT ON COLUMN open_api_log.api_id IS '接口id';