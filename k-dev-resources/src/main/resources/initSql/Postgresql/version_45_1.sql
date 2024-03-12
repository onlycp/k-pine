delete from sys_dict_item where id='1537c3ca5e934e6c846b0415229dbe85';

alter table sys_logic_flow add new_flow_json text null;
alter table sys_logic_history add new_flow_json text null;
alter table sys_logic_template add flow_config text null;
alter table sys_logic_template add type varchar(10) null;
alter table sys_logic_template add new_flow_json text null;