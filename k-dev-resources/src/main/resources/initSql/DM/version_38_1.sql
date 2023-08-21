
alter table sys_logic_flow add new_flow_json CLOB null;
alter table sys_logic_history add new_flow_json CLOB null;
alter table sys_logic_template add flow_config CLOB null;
alter table sys_logic_template add type INT null;
alter table sys_logic_template add new_flow_json CLOB null;
