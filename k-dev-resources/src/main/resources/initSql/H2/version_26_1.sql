
alter table sys_api add module_id VARCHAR(36) null COMMENT '关联模块';
alter table sys_logic_flow add module_id VARCHAR(36) null COMMENT '关联模块';
alter table dev_page add module_id VARCHAR(36) null COMMENT '关联模块';
alter table dev_page add tags VARCHAR(36) null COMMENT '标签';

