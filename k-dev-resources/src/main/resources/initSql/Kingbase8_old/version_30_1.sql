
CREATE TABLE sys_instance(
                             id VARCHAR(32) NOT NULL PRIMARY KEY,
                             host_name VARCHAR(32)  ,
                             port INT  ,
                             heart_beat_time VARCHAR(20)   ,
                             reg_time VARCHAR(20),
                             online INT

);

alter table sys_role_menu alter column id type varchar(36);
alter table sys_role_menu alter column   sys_menu_id type varchar(36) ;
alter table sys_role_menu alter column   who_created type varchar(36) ;
