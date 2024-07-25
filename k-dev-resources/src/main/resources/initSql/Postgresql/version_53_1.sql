-- 对于sys_unit表添加short_name列
ALTER TABLE sys_unit ADD COLUMN short_name varchar(36);
COMMENT ON COLUMN sys_unit.short_name IS '简称';

-- 对于sys_unit表添加unit_code列
ALTER TABLE sys_unit ADD COLUMN unit_code varchar(100);
COMMENT ON COLUMN sys_unit.unit_code IS '机构编码';

-- 对于sys_user表添加jira_name列
ALTER TABLE sys_user ADD COLUMN jira_name varchar(100);
COMMENT ON COLUMN sys_user.jira_name IS 'jira账号名（项目管理系统）';
