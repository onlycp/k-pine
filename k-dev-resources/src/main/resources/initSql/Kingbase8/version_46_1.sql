-- sys_unit
ALTER TABLE  sys_unit add short_name varchar(36) null;
ALTER TABLE  sys_unit add short_code varchar(100)  null;

-- sys_user
ALTER TABLE public.sys_user add jira_name varchar(100)  null;