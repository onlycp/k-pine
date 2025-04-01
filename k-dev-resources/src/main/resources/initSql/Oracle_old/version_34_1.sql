
ALTER TABLE sys_unit ADD unit_level int NULL ;
ALTER TABLE sys_search_config ADD search_columns varchar(1000) NULL ;
ALTER TABLE sys_search_config MODIFY  columns varchar(1000) ;

alter table sys_logic_flow add new_flow_json CLOB null;
alter table sys_logic_history add new_flow_json CLOB null;
alter table sys_logic_template add flow_config CLOB null;
alter table sys_logic_template add type INT null;
alter table sys_logic_template add new_flow_json CLOB null;