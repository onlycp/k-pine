
alter table sys_api alter column api_tags VARCHAR(255) null COMMENT '标签';
alter table sys_logic_flow alter column tags VARCHAR(255) null COMMENT '标签';
alter table dev_page alter column tags VARCHAR(255) null COMMENT '标签';