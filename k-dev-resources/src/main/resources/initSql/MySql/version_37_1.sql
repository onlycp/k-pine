CREATE INDEX idx_sys_operate_log_app_id USING BTREE ON sys_operate_log (app_id);
CREATE INDEX idx_sys_operate_log_module USING BTREE ON sys_operate_log (`module`);
CREATE INDEX idx_sys_operate_log_operate_time USING BTREE ON sys_operate_log (operate_time);
CREATE INDEX idx_dev_page_deleted USING BTREE ON dev_page (deleted,`path`);

