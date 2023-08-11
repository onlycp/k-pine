alter table sys_logic_flow add new_flow_json longtext null COMMENT 'v3.1+的流程显示JSON';
alter table sys_logic_history add new_flow_json longtext null COMMENT '流程图JSON';
alter table sys_logic_template add flow_config longtext null COMMENT '配置：图标、颜色、继承、参数等';
alter table sys_logic_template add type int(11) null COMMENT '默认为0，0为旧版，只保存源码数据，需要自行修改，1为新版（自定义节点），加入到流程后不可2次修改';
alter table sys_logic_template add new_flow_json longtext null COMMENT 'v3.1+的流程显示JSON';
