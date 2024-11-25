
alter table sys_api modify column api_tags VARCHAR(255) null COMMENT '标签';
alter table sys_logic_flow modify column tags VARCHAR(255) null COMMENT '标签';
alter table dev_page modify column tags VARCHAR(255) null COMMENT '标签';
