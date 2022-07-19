-- 角色
INSERT INTO sys_role
(id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id)
VALUES('3fc43c9c69f44144bd032d9451ba328b', '团队成员', 'team_member', '青松开发者平台-团队成员', 1, '', '2022-03-10 06:13:01', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:59', NULL);
INSERT INTO sys_role
(id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id)
VALUES('4a30f4d346074b4ba8363944f004c1d9', '团队负责人', 'team_owner', '青松开发者平台-团队负责人', 1, '', '2022-03-10 06:12:31', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:44:06', NULL);

-- 菜单
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('08db605cec0f4cd980514b1e82c8c435', '字典管理', 'f8bb5559a95d4b4e81b4aa7901fc4914', '', NULL, 'dict', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/08db605cec0f4cd980514b1e82c8c435/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/', 3, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 02:04:03', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 02:04:53', NULL, 2, 'dev', 1, 3, 3, 0, NULL, '/dev/config/dict', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('b44a46ad1c9344bc9c1aca99f12bd60e', '系统配置管理', 'f8bb5559a95d4b4e81b4aa7901fc4914', '', NULL, 'config', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/b44a46ad1c9344bc9c1aca99f12bd60e/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 02:31:30', '0b77a45e06984e5097a11a0896818d55', '2022-04-15 04:29:53', NULL, 2, 'dev', 1, 3, 3, 0, NULL, '/dev/config/config', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('9987fabee289440aa7a81553afed67b3', '逻辑编排模板管理', 'f8bb5559a95d4b4e81b4aa7901fc4914', NULL, NULL, 'flow-template', NULL, 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/9987fabee289440aa7a81553afed67b3/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-31 15:40:22', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-31 15:40:22', '', 2, 'biz', 1, 1, 3, 0, '2f75360509f8422582a2d6169863f247', '/dev/config/flow-template', 0);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('95477ce0e2f64601b9067cdf632710a2', '用户管理', 'f8bb5559a95d4b4e81b4aa7901fc4914', '', NULL, 'user', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/95477ce0e2f64601b9067cdf632710a2/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 01:04:41', '0b77a45e06984e5097a11a0896818d55', '2022-04-11 18:03:04', NULL, 2, 'dev', 1, 3, 3, 0, NULL, '/dev/config/user', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('6f01a4a233004d8a90645ae3c5ac5984', '应用导出', 'f8bb5559a95d4b4e81b4aa7901fc4914', '', NULL, 'export', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/6f01a4a233004d8a90645ae3c5ac5984/', 99, 1, '0b77a45e06984e5097a11a0896818d55', '2022-04-08 16:41:21', '0b77a45e06984e5097a11a0896818d55', '2022-04-08 16:41:21', NULL, 2, 'dev', 1, 3, 3, 0, NULL, '/dev/config/export', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('4aa68f78855d42c3b8b44ca08e8adef8', '菜单管理', 'f8bb5559a95d4b4e81b4aa7901fc4914', '', NULL, 'menu', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/4aa68f78855d42c3b8b44ca08e8adef8/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 01:03:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 01:03:46', NULL, 2, 'dev', 1, 3, 3, 0, NULL, '/dev/config/menu', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('f53d26abee314351a327c84f9b475237', '模块管理', 'f8bb5559a95d4b4e81b4aa7901fc4914', NULL, NULL, 'module', NULL, 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/f53d26abee314351a327c84f9b475237/', 6, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-31 15:41:08', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-31 15:41:08', '', 2, 'biz', 1, NULL, NULL, NULL, NULL, '/dev/config/module', 0);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('ee00aa994e6842bf933bfd964b9844b0', '页面管理', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', 'page-mgt', 'page-mgt', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/ee00aa994e6842bf933bfd964b9844b0/', 5, 1, '', '2022-02-15 11:23:32', '', '2022-03-10 11:43:52', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/page-mgt', 0);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('e238560484c84c168cf91a64be27c876', '应用主页', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', NULL, 'app', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/e238560484c84c168cf91a64be27c876/', 2, 1, '', '2022-03-04 17:17:03', '', '2022-03-10 08:36:20', NULL, 2, 'dev', 1, 0, 1, 1, NULL, '/dev/app', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('f1c93a367d7541c6ad895b9c168ea945', 'preview6', '15c109ba424342948333deba7e0e3a76', '', NULL, 'preview6', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f1c93a367d7541c6ad895b9c168ea945/', 99, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:53:16', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:53:28', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('ddf6db4486584655b3aa227e50aeb697', '插件管理', '15c109ba424342948333deba7e0e3a76', '<svg-icon icon-class="list" />', NULL, 'plugin', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76%/ddf6db4486584655b3aa227e50aeb697/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:32:58', '63454c98827e4a0384abf30e0e6eef54', '2022-04-25 15:10:16', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '83fee357f5db43faba47c90d963cc821', '/dev/plugin', 0);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('d72ba047ce4c4a088a53c0c0085e1a15', '逻辑流程编辑', '15c109ba424342948333deba7e0e3a76', '', 'logic-design', 'logic-design', '/sys/kdbFlow/design', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/d72ba047ce4c4a088a53c0c0085e1a15/', 12, 1, '', '2022-01-27 02:00:50', '', '2022-02-14 18:10:30', NULL, 2, 'dev', 1, 0, 3, 0, NULL, '/dev/logic-design', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('d65657e653a14758bed6b518f30bffc0', 'preview2', '15c109ba424342948333deba7e0e3a76', '', NULL, 'preview2', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/d65657e653a14758bed6b518f30bffc0/', 99, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:31:49', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:33:20', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('d512adf9690a4bc884c502995f0210b7', 'preview4', '15c109ba424342948333deba7e0e3a76', '', NULL, 'preview4', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/d512adf9690a4bc884c502995f0210b7/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:32:13', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:33:10', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('f8bb5559a95d4b4e81b4aa7901fc4914', '应用配置', '15c109ba424342948333deba7e0e3a76', '', NULL, 'config', '', 0, 'M', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/', 13, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:10:43', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 02:03:38', NULL, 2, 'dev', 1, 3, 3, 0, NULL, '/dev/config', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('d0c39e53a5b34747abd4c40922dfc929', '逻辑编排v2', '15c109ba424342948333deba7e0e3a76', '', NULL, 'logicflow/design', '/dev/logicflow/design', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/d0c39e53a5b34747abd4c40922dfc929/', 20, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-09 10:39:01', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-09 10:39:01', NULL, 1, 'dev', 2, 0, 0, 0, NULL, '/dev/logicflow/design', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('fe2c56bb98cd4fc796c32b46082b336b', '团队管理', '15c109ba424342948333deba7e0e3a76', '', NULL, 'team', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/fe2c56bb98cd4fc796c32b46082b336b/', 20, 1, '0b77a45e06984e5097a11a0896818d55', '2022-04-12 13:57:39', '0b77a45e06984e5097a11a0896818d55', '2022-04-12 13:57:39', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/team', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('bd896854b0764aa1a506e2bfc829205a', '旧版逻辑编排', '15c109ba424342948333deba7e0e3a76', '', NULL, 'sys-kdb-flow-old', '/sys/kdbFlow/index', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/bd896854b0764aa1a506e2bfc829205a/', 99, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-06 10:37:25', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-06 10:37:37', NULL, NULL, NULL, NULL, 0, 0, 0, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('ff50d725626644c78d4085fede6aa74f', 'preview3', '15c109ba424342948333deba7e0e3a76', '', NULL, 'preview3', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/ff50d725626644c78d4085fede6aa74f/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:32:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:33:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('b1bef2f8e2f442a3927696d1ce218b06', '页面设计器', '15c109ba424342948333deba7e0e3a76', '', 'pageDesigner', 'page-designer', '/dev/pageDesigner/index', 1, 'C', '', 0, 1, '/15c109ba424342948333deba7e0e3a76/b1bef2f8e2f442a3927696d1ce218b06/', 1, 1, '', '2022-02-14 06:08:23', '', '2022-02-15 17:04:26', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/page-designer', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('9bb44b43cf184446b04f6992403023f5', '拓扑图-编辑', '15c109ba424342948333deba7e0e3a76', '', NULL, 'topological-edit', '/dev/topological/design', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/9bb44b43cf184446b04f6992403023f5/', 12, 1, '', '2022-03-07 16:35:50', '', '2022-03-09 02:44:36', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/topological-edit', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('96326f8a472f49e483aacf0b2413f96b', '数据源管理', '15c109ba424342948333deba7e0e3a76', 'data-query', 'dev-kdb-data-source', 'dev-kdb-data-source/index', '/sys/kdbDataSource/index', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/96326f8a472f49e483aacf0b2413f96b/', 10, 1, '', '2022-01-14 09:55:25', '', '2022-02-23 00:44:04', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/dev-kdb-data-source/index', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('7462f2e0b4c545a6841cfa59e9736aa4', '函数库', '15c109ba424342948333deba7e0e3a76', 'code', 'kdb-fun', 'kdb-funs/index', '/sys/kdbFun/index', 0, 'C', 'kdb-fun', 0, 1, '/15c109ba424342948333deba7e0e3a76/7462f2e0b4c545a6841cfa59e9736aa4/', 8, 1, '', '2022-02-10 18:57:30', '', '2022-02-23 00:44:18', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/kdb-funs/index', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('692314da0a4d4c8dbc601b0b2776e0cd', 'preview5', '15c109ba424342948333deba7e0e3a76', '', NULL, 'preview5', '', 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/692314da0a4d4c8dbc601b0b2776e0cd/', 99, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:52:57', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:52:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('5ab9613b08f541e9a0c68e3bef0ea007', '拓扑图', '15c109ba424342948333deba7e0e3a76', NULL, NULL, 'topological', NULL, 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/5ab9613b08f541e9a0c68e3bef0ea007/', 8, 1, '', '2022-03-07 16:32:06', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-27 14:05:37', NULL, 2, 'dev', 1, 0, 3, 1, NULL, '/dev/topological', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('5a46b9de1e6347089a2b96228fc9c2bc', '数据模型', '15c109ba424342948333deba7e0e3a76', 'build', 'view-models', 'dev-view-model/index', '/sys/viewModel/index', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/5a46b9de1e6347089a2b96228fc9c2bc/', 10, 1, '', '2022-01-29 16:27:17', '', '2022-02-23 00:45:39', NULL, NULL, NULL, NULL, 0, 3, 1, NULL, '/dev/dev-view-model/index', 0);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('3a5dc5dd217a4dc8811a007496e56716', '接口管理', '15c109ba424342948333deba7e0e3a76', 'swagger', 'api-codes', 'dev-api/index', '/sys/api/index', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/3a5dc5dd217a4dc8811a007496e56716/', 6, 1, '', '2022-01-18 18:07:07', '', '2022-02-23 00:45:29', NULL, 2, 'dev', 1, 0, 3, 0, NULL, '/dev/dev-api/index', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('372c4bd4da39495e8fabe7aa25993ca8', '能力树', '15c109ba424342948333deba7e0e3a76', 'tree-table', NULL, 'dev-power/index', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/372c4bd4da39495e8fabe7aa25993ca8/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-01 15:32:41', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-01 16:04:43', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('25639b53d2cb434086677657bf37a974', '逻辑编排管理', '15c109ba424342948333deba7e0e3a76', 'dev', 'sys-kdb-flow', 'sys-kdb-flow', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/25639b53d2cb434086677657bf37a974/', 7, 1, '', '2022-01-14 09:52:41', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-06 10:38:27', NULL, 2, 'dev', 1, 0, 3, 0, NULL, '/dev/sys-kdb-flow', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('1e66802db992442680c27b8e9107156b', 'preview', '15c109ba424342948333deba7e0e3a76', NULL, NULL, 'preview', NULL, 1, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/1e66802db992442680c27b8e9107156b/', 0, 1, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-29 14:21:15', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-21 16:04:50', NULL, 2, 'dev', 1, 0, 3, 0, NULL, '/dev/preview', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('12463e4270ea43a3bd773879cceb0f4d', '应用管理', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', 'appMgt', 'appMgt', '', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/12463e4270ea43a3bd773879cceb0f4d/', 0, 1, '', '2022-03-10 08:36:51', '', '2022-03-10 08:37:06', NULL, NULL, NULL, NULL, 0, 3, 1, NULL, '/dev/appMgt', 0);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('102fd651dbe640069362865778e4f80d', '页面配置', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', 'page', 'page/index', '/dev/page/index', 0, 'C', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/102fd651dbe640069362865778e4f80d/', 4, 1, '', '2022-03-10 11:49:22', '', '2022-03-10 11:52:43', NULL, 2, 'dev', 1, 0, 3, 0, NULL, '/dev/page/index', 1);
INSERT INTO sys_menu
(id, name, parent_id, icon, code, router_path, component_path, is_hidden, menu_type, api_codes, open_mode, keep_alive, `path`, order_num, status, who_created, when_created, who_modified, when_modified, app_id, data_type, theme, page_type, sidebar_nav_mode, top_nav_mode, main_mode, page_id, full_path, is_dev)
VALUES('15c109ba424342948333deba7e0e3a76', '开发者平台', NULL, 'dev', NULL, 'dev', '', 0, 'M', NULL, 0, 1, '/15c109ba424342948333deba7e0e3a76/', 8, 1, '', '2022-02-14 06:07:27', '', '2022-02-14 06:07:27', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/dev', 1);

-- 角色菜单
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('04d551c17b8b4aa59c90b44ca1d531ed', 'd512adf9690a4bc884c502995f0210b7', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('057fbd05ef334b9b88cb3adb6b8a944f', '692314da0a4d4c8dbc601b0b2776e0cd', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('1028739639124b98ae56eb3d67b90cb2', '95477ce0e2f64601b9067cdf632710a2', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('19a2863bc653442eacfe6946371bc926', '4aa68f78855d42c3b8b44ca08e8adef8', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('1d619fc0299f4028b85819ea40ca54ac', 'e238560484c84c168cf91a64be27c876', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('1f5505178f2e41fb9a431b68eaf7ea9a', 'd0c39e53a5b34747abd4c40922dfc929', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('24e2a48182314fb895ca6730082676db', 'bd896854b0764aa1a506e2bfc829205a', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('25467c4a06bd41a3b43abebd7de42ca1', 'd65657e653a14758bed6b518f30bffc0', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('3cb31e7943f24563baab2671c4037618', '15c109ba424342948333deba7e0e3a76', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('424e10e357b24af99944c7f17d6069c5', 'b1bef2f8e2f442a3927696d1ce218b06', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('426edbf346b14f4ab53b2968cc833f57', 'f8bb5559a95d4b4e81b4aa7901fc4914', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('4345653c90184c75b856d64ea666e664', '6f01a4a233004d8a90645ae3c5ac5984', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('4cce17d6ad394e5fbde6bbf868a9fbdf', '9987fabee289440aa7a81553afed67b3', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('562a0a8be5944ba98a463ffe872b130c', 'd72ba047ce4c4a088a53c0c0085e1a15', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('5f491150c7ac485c9dfbd8dccd3e844e', 'ff50d725626644c78d4085fede6aa74f', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('6741b9588b61428184ebc8a23f5b94cb', '6b76a5249255430a9101b2892fa0edbe', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('7de0da07c68c452a9806cba934d7ab91', '25639b53d2cb434086677657bf37a974', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('8e221b6ac8b548a6bacca093fcb34206', 'f1c93a367d7541c6ad895b9c168ea945', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('90b70ac6d8894ac8b381c3fbd51d0a43', '08db605cec0f4cd980514b1e82c8c435', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('9b5e66d023ad4efbb0d16e7e48d7f5e5', '7462f2e0b4c545a6841cfa59e9736aa4', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('a0190e392d084ca5868b6e22d305d310', '102fd651dbe640069362865778e4f80d', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('a05d014fa0114746be0f3c26890476fe', '96326f8a472f49e483aacf0b2413f96b', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('ad1e3813a5c34e3d8d25aabacd217a27', '1e66802db992442680c27b8e9107156b', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('b04272f3fc724ca3acfb0da3069a19d9', '9bb44b43cf184446b04f6992403023f5', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('b0804d76c78b447496e9bf37017dc640', 'b44a46ad1c9344bc9c1aca99f12bd60e', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('fb09122fcde54f8ebc098cd93b6caf2a', '3a5dc5dd217a4dc8811a007496e56716', '3fc43c9c69f44144bd032d9451ba328b', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:42', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('0053d3ab80264678b4893590dba6ad73', '102fd651dbe640069362865778e4f80d', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('0f63ce6abcd04e0abee1830cae234ec2', '3a5dc5dd217a4dc8811a007496e56716', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('14b4341cbba34b50af6f89ef3f17de63', 'ff50d725626644c78d4085fede6aa74f', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('1cc3d36844bb423b9eb8e8b2cfa6c6bc', '9bb44b43cf184446b04f6992403023f5', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('269be13a26904b398537a044ee958627', 'd65657e653a14758bed6b518f30bffc0', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('35423606f56c4a1580b5d3050855e044', 'e238560484c84c168cf91a64be27c876', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('45a0f31670c44328ab7f33b424589a91', 'f8bb5559a95d4b4e81b4aa7901fc4914', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('4781ac09341b4b86a7d545dabf461a60', '15c109ba424342948333deba7e0e3a76', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('4b87a456a386406fa108eb29c5288813', 'b44a46ad1c9344bc9c1aca99f12bd60e', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('4d561e00c6ad4b5ea2e6d21f418fcab5', 'd72ba047ce4c4a088a53c0c0085e1a15', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('5953e2427e6a4fe9a6e9dd39262f5dce', 'f1c93a367d7541c6ad895b9c168ea945', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('5d95a5715e154885adc8d8437c9b0d53', '95477ce0e2f64601b9067cdf632710a2', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('6e87f5009f3443b28bf1eb1a103c16b7', '6b76a5249255430a9101b2892fa0edbe', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('7b27a0e01a0642ec951a1cfe04b88a63', '9987fabee289440aa7a81553afed67b3', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('8b68ca776f324ab6ad1abce2c4ed08dd', '1e66802db992442680c27b8e9107156b', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('90a8c7fda1264a599fe87917677aaf30', '08db605cec0f4cd980514b1e82c8c435', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('91aae7c485fd4784ad1387f3e131fa23', 'b1bef2f8e2f442a3927696d1ce218b06', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('9ca5fc9401c248f39243c2f231e48b54', 'bd896854b0764aa1a506e2bfc829205a', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('9ebf09e9ec64461f9610dafe95c401ac', '692314da0a4d4c8dbc601b0b2776e0cd', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('a991aa3cfc164f288796be4c3d365198', '6f01a4a233004d8a90645ae3c5ac5984', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('b30a088930ab46f89a096d2f60d31786', '7462f2e0b4c545a6841cfa59e9736aa4', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('bc85a3879e694cd7ad2d55e607bda8ba', 'd512adf9690a4bc884c502995f0210b7', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('c41f2b8770274c669697513f7d4df020', 'fe2c56bb98cd4fc796c32b46082b336b', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('c9524763c09e4f1db8928ac1cc3a67fc', '96326f8a472f49e483aacf0b2413f96b', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('d3a92c309f9d4659b7b34017a7ea2601', '4aa68f78855d42c3b8b44ca08e8adef8', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);
INSERT INTO sys_role_menu
(id, sys_menu_id, sys_role_id, who_created, when_created, app_id)
VALUES('fbace4d8e1fc4d2692ee23e47c25f4ac', '25639b53d2cb434086677657bf37a974', '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-08 16:21:49', NULL);

-- 能力树
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('111', '自动化', NULL, '自动化', NULL, NULL, NULL, NULL, '/111/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('13f20d29c2634f51856b821ef3d60f46', '阿里云', '3c8ac8fb2fdf4f6eb388652dd86553c8', '阿里云', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:15:27', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:15:27', '/9b9c2f0fcede493d82afbfda23e45825/3c8ac8fb2fdf4f6eb388652dd86553c8/13f20d29c2634f51856b821ef3d60f46/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('36ab4e2464aa48a1896d8de17e6df0e2', '数学函数', 'a9ff6afebb464d0fb06495fec2623353', '数学函数', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:55', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:55', '/a9ff6afebb464d0fb06495fec2623353/36ab4e2464aa48a1896d8de17e6df0e2/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('3c8ac8fb2fdf4f6eb388652dd86553c8', 'HTTP', '9b9c2f0fcede493d82afbfda23e45825', 'HTTP', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:15:06', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:15:06', '/9b9c2f0fcede493d82afbfda23e45825/3c8ac8fb2fdf4f6eb388652dd86553c8/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('41804268bde5404683719c0e60b87c0a', 'List集合', 'a7c2b955156f40b88eec46dc3214da73', 'List集合', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:18', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:18', '/a7c2b955156f40b88eec46dc3214da73/41804268bde5404683719c0e60b87c0a/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('4e2d4f2e71804139aab21a4d9e2db23e', '字符串', 'a9ff6afebb464d0fb06495fec2623353', '字符串', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:35', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:35', '/a9ff6afebb464d0fb06495fec2623353/4e2d4f2e71804139aab21a4d9e2db23e/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('53030b65dd88469489cd6e803ef40abd', 'RPA', '111', 'RPA', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:09:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:09:11', '/111/53030b65dd88469489cd6e803ef40abd/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('5526949e421f4ef28489283fd8362daf', 'UNIOPS', 'a7c2b955156f40b88eec46dc3214da73', 'UNIOPS', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:10', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:10', '/a7c2b955156f40b88eec46dc3214da73/5526949e421f4ef28489283fd8362daf/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('56077d94f21f4c0f9452cf50329c172e', 'CRUD', 'a7c2b955156f40b88eec46dc3214da73', '增删改查', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:04:02', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:04:02', '/a7c2b955156f40b88eec46dc3214da73/56077d94f21f4c0f9452cf50329c172e/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('5a453eca55fb495a849fdd2851483e12', 'UNIOPS', 'a9ff6afebb464d0fb06495fec2623353', 'UNIOPS', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:23:59', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:23:59', '/a9ff6afebb464d0fb06495fec2623353/5a453eca55fb495a849fdd2851483e12/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('5c6b3c06df2d490e94dd157833afbbe0', '常用逻辑', 'a7c2b955156f40b88eec46dc3214da73', '常用逻辑', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:04:14', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:04:14', '/a7c2b955156f40b88eec46dc3214da73/5c6b3c06df2d490e94dd157833afbbe0/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('7fc121a03bca4f0ab9005dcfc59272ee', '结果格式化', 'a7c2b955156f40b88eec46dc3214da73', '结果格式化', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:00', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:00', '/a7c2b955156f40b88eec46dc3214da73/7fc121a03bca4f0ab9005dcfc59272ee/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('8397e7f441b24f32938e7c9a5538af11', '字符串', 'a7c2b955156f40b88eec46dc3214da73', '字符串', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:29', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:29', '/a7c2b955156f40b88eec46dc3214da73/8397e7f441b24f32938e7c9a5538af11/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('888f29323b8644359a26079f53e4852d', 'Kingsware', '53030b65dd88469489cd6e803ef40abd', 'Kingsware', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:09:58', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:09:58', '/111/53030b65dd88469489cd6e803ef40abd/888f29323b8644359a26079f53e4852d/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('8b2b6c1efcb746e281a003da4f3eb1e3', 'AI', 'a7c2b955156f40b88eec46dc3214da73', 'AI', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:18', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:18', '/a7c2b955156f40b88eec46dc3214da73/8b2b6c1efcb746e281a003da4f3eb1e3/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('8ea8588366ec41e0a77189b5838d822f', '日期函数', 'a9ff6afebb464d0fb06495fec2623353', '日期函数', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:08', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:08', '/a9ff6afebb464d0fb06495fec2623353/8ea8588366ec41e0a77189b5838d822f/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('9b9c2f0fcede493d82afbfda23e45825', '协议类', NULL, '通讯协议', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:04:59', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:04:59', '/9b9c2f0fcede493d82afbfda23e45825/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('a7c2b955156f40b88eec46dc3214da73', '逻辑编排模板', NULL, '逻辑编排模板能力', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:46:09', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:46:09', '/a7c2b955156f40b88eec46dc3214da73/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('a8919101f7ec475eb88a49eeddeed8fe', 'AI', 'a9ff6afebb464d0fb06495fec2623353', 'AI', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:24:25', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:24:25', '/a9ff6afebb464d0fb06495fec2623353/a8919101f7ec475eb88a49eeddeed8fe/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('a9ff6afebb464d0fb06495fec2623353', '函数库', NULL, '函数库能力', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:45:55', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:45:55', '/a9ff6afebb464d0fb06495fec2623353/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('cd25686ed2554d75bcdd0bf6eb4dc7a1', 'SQL工具', 'a9ff6afebb464d0fb06495fec2623353', 'SQL工具', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:41:45', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:41:45', '/a9ff6afebb464d0fb06495fec2623353/cd25686ed2554d75bcdd0bf6eb4dc7a1/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('d072b8ffa8ba4ebea09fc8a63095ba59', '数学函数', 'a7c2b955156f40b88eec46dc3214da73', '数学函数', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:37', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:37', '/a7c2b955156f40b88eec46dc3214da73/d072b8ffa8ba4ebea09fc8a63095ba59/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('d33bd216a4b74810aef35174cfa71216', 'List集合', 'a9ff6afebb464d0fb06495fec2623353', 'List集合', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:28', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 08:59:28', '/a9ff6afebb464d0fb06495fec2623353/d33bd216a4b74810aef35174cfa71216/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('d8f42d1b0bcb496a85ca29e60c274b2e', '实用工具', 'a9ff6afebb464d0fb06495fec2623353', '实用工具', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:23:35', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:23:35', '/a9ff6afebb464d0fb06495fec2623353/d8f42d1b0bcb496a85ca29e60c274b2e/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('ddc28065595f457895279ebd49b058b5', '实用工具', 'a7c2b955156f40b88eec46dc3214da73', '实用工具', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:04:36', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:04:36', '/a7c2b955156f40b88eec46dc3214da73/ddc28065595f457895279ebd49b058b5/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('e740fac57ff44fbc88e4b24ea891f227', 'UNIOPS', '3c8ac8fb2fdf4f6eb388652dd86553c8', 'UNIOPS', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:16:09', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:16:09', '/9b9c2f0fcede493d82afbfda23e45825/3c8ac8fb2fdf4f6eb388652dd86553c8/e740fac57ff44fbc88e4b24ea891f227/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('e8b047bed0414f20bfadba637df79d2d', '日期函数', 'a7c2b955156f40b88eec46dc3214da73', '日期函数', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:54', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:00:54', '/a7c2b955156f40b88eec46dc3214da73/e8b047bed0414f20bfadba637df79d2d/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('eeaeecca27e54287afbcdc72915db7fe', '结果格式化', 'a9ff6afebb464d0fb06495fec2623353', '结果格式化', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:47:28', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:47:28', '/a9ff6afebb464d0fb06495fec2623353/eeaeecca27e54287afbcdc72915db7fe/');
INSERT INTO dev_power_tree
(id, name, parent_id, note, who_created, when_created, who_modified, when_modified, `path`)
VALUES('f557cd2bac244e9cac5a70836c5cb87c', '上下文取值/赋值', 'a9ff6afebb464d0fb06495fec2623353', '上下文取值/赋值', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:47:04', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 08:47:04', '/a9ff6afebb464d0fb06495fec2623353/f557cd2bac244e9cac5a70836c5cb87c/');

INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('01c5b20eb6e249ad89fd8585128a0198', 'd072b8ffa8ba4ebea09fc8a63095ba59', 'c80b0accc6a6499e8f3cdb08455aea25', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:10:46');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('03d664898b5e4903b16ecda021daa637', 'd33bd216a4b74810aef35174cfa71216', 'bb289ae294b447f4bc36bf1e0d1cd125', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:22:44');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('05924790d02b439d9a4f86e51182c950', '4e2d4f2e71804139aab21a4d9e2db23e', 'e777866ff04347308fc4d056bd0767e4', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 14:18:15');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('07ee5039fc364047bfbf7c4d14c793b8', '8ea8588366ec41e0a77189b5838d822f', 'f44ea169da5f49f4acbe34ff6bfc0349', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:46:18');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('0800ee81ffe30c26fa923cccf444e0ca', 'd8f42d1b0bcb496a85ca29e60c274b2e', '49754556309349b6b32d3fe4b79ad879', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('0a6fb943c76d495dbec401f30dfe9984', 'e8b047bed0414f20bfadba637df79d2d', '6445c079c4664b4fb9ff60e30004a331', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:08:36');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('0a795d3ac5484d4eaa69395d78685797', 'd072b8ffa8ba4ebea09fc8a63095ba59', '4bd41f777d5b4d59872a21bee8822064', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:49:28');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('0bca0e86dd4c4dae92f02eadf2d169ee', 'e8b047bed0414f20bfadba637df79d2d', 'eb657ee1615940c18fc966fac607d9f5', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:59:02');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('0ed53d3f798c49eea96e119c23feb802', 'd33bd216a4b74810aef35174cfa71216', '1bb1b04490754559809787fbe378f46a', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:22:16');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('101be920e19f48c787f9b34dd2e4368b', '8ea8588366ec41e0a77189b5838d822f', '856b3c00a324454f966eb6d1fd28e0e1', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:21:27');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('10235fbd81a74befb909d2d25c694dd5', 'e8b047bed0414f20bfadba637df79d2d', 'bd7196d6e8c845e19ad932729cfaf768', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:08:40');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('1136f1c05eb741aea6ef74b0a5ffec1c', 'e8b047bed0414f20bfadba637df79d2d', '43694b27881a46c69e5e45e5ab4ba1cc', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:12:41');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('11896129abf04e20b16b1c8dc5ca6b64', 'd33bd216a4b74810aef35174cfa71216', '0a6a26e094334783bdb8ad54bc2768ee', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:19:57');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('124003188f5a4d5dad4dfee9abaffc99', '36ab4e2464aa48a1896d8de17e6df0e2', 'c3c08eef673c40b9bb5fd5b1640e67b9', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 16:40:32');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('15730b6fe7999d7ffdc037a06f026d8c', 'f557cd2bac244e9cac5a70836c5cb87c', 'dab3e912ce68491ba5849b4aa00f1c95', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('1c5b595f21544c179272bd3f1fa0d2f0', '8ea8588366ec41e0a77189b5838d822f', '368fc458b7a64ac3ab9f6d387df6e0a8', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:38:54');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('1e7b399af1724d06b1f2486d55b9a900', '56077d94f21f4c0f9452cf50329c172e', 'ab75ff7e4ec8448ebc717a546e463313', 4, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 02:25:04');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('22cb43b4a7b8470285fba064af1ec559', 'd8f42d1b0bcb496a85ca29e60c274b2e', 'ef803c45249146378931f9fa07cbb9fd', 2, 'bab66508eefe49ada701257548bbe54a', '2022-06-09 11:57:34');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('23527b16ab8547f595803a6c05ef67f3', '8ea8588366ec41e0a77189b5838d822f', 'fbdab7bff4344ad5bb0c29972a309e1b', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:39:22');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('246947e5c780433f9b4a50b502c22b71', 'd8f42d1b0bcb496a85ca29e60c274b2e', 'ddb3f33c35b94a1aba12388f240279a0', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:34:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('253caea86efa8e533f653dace70cfd32', 'd8f42d1b0bcb496a85ca29e60c274b2e', '208e67e287fc4e1b882a94cc779c7f01', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('291517af4fb443bf95e8bd7edd013bfb', '8397e7f441b24f32938e7c9a5538af11', '11727a7c7bea4c64818b9f62bca71878', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:29:03');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('2c1e01f35548b5b5f7a12bb08e7b87c8', 'a8919101f7ec475eb88a49eeddeed8fe', '64b293002b4249dc910b11dd6a7fc9ff', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('2c2646cfe04f486f886cf4ea1b0b5616', 'e8b047bed0414f20bfadba637df79d2d', 'd342a9fd46ba488caba6cd9bbf858e3b', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:09:08');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('2c9d4b0b3f594808a1dd49e1739e6fa2', '8397e7f441b24f32938e7c9a5538af11', '8a73df8cc8cc4cb6a1beecd3affed184', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:17:00');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('2def8b04d79d46b0a20591949e682ed7', '8ea8588366ec41e0a77189b5838d822f', '3488cd2d391d4b8b8a8c5b21fdf05efe', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:44:01');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('3170591367ff4d2d9be880c9bd7fcb50', '8ea8588366ec41e0a77189b5838d822f', 'fbe0103879c747a4acaa8d0ec5d7071c', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:38:01');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('3236eb8e01f544bf8a66bb0f92d67d66', '4e2d4f2e71804139aab21a4d9e2db23e', 'daf336a6eeb44fe4bdfdda623a4db079', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 14:44:28');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('33304395d9f54deebe14266738e14ce4', '8ea8588366ec41e0a77189b5838d822f', 'a55902ea87f04a40b13810ba4fb932cf', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:38:13');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('33d4e7cf475b4bd684e6331b30343a16', 'd33bd216a4b74810aef35174cfa71216', 'd36a2f9da3724323a51b3a99633c57be', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:22:29');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('3483b6fb99084ba8a3ec809c916983ab', '8397e7f441b24f32938e7c9a5538af11', 'dbd4b310bc324db6abbfb248a766ec5d', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:02:08');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('38af94aad1924b7f9518f5073ef8fea1', 'd33bd216a4b74810aef35174cfa71216', '5d223330098d4b77b8b2544bf5ed07d6', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:21:02');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('396b5a4738654550b7c2c6ac03a7619e', 'f557cd2bac244e9cac5a70836c5cb87c', 'setResult', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-07 18:17:49');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('3d6e297b4f3a4c67abc4a0e1fbff5c20', '8ea8588366ec41e0a77189b5838d822f', '6a0dd2f7d84244a99d1886dfc67b968f', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:54:06');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('3f3eb88cae4e49ffa87f64d5a5858e8f', '8ea8588366ec41e0a77189b5838d822f', '9bdea81838544e9db6d7407c0ba903fd', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:34:47');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('3fbde160a0394425a284984766d0ef40', '8397e7f441b24f32938e7c9a5538af11', 'aa4ffc254f054dea857480752a325893', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:39:53');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('429e26dc803648998945886147dcd4e3', 'd33bd216a4b74810aef35174cfa71216', '2e52e9bc4dda4e1e8eff117595ade8d6', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:19:19');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('43b43fc06c6f4f7180b6220d5e9fa2a6', 'd072b8ffa8ba4ebea09fc8a63095ba59', '14785aa058eb4e15a7aeb175ea5cbf3f', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:05:55');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('43ca6d3737af436cb5da12db5c09d68a', 'd072b8ffa8ba4ebea09fc8a63095ba59', '50a0b3b7c9534617b656a23149aa9667', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:51:35');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('44726ddd6066490e851c23dbff315814', 'e8b047bed0414f20bfadba637df79d2d', '4b509549eda74be5b8f88531601c4a05', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:30:35');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('44843576980d45e7a635d945db05beed', '8ea8588366ec41e0a77189b5838d822f', 'faa149de1e5148a696c03be099425710', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:55:47');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('448cf941a1bd4a25b0ea7a741ec28ef9', '56077d94f21f4c0f9452cf50329c172e', '7882cb65d3c946d9871fdf6f4870ecf9', 4, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 10:55:52');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('449ac7863df14ee4acf4b4fc94c43926', '4e2d4f2e71804139aab21a4d9e2db23e', '36983d1d3baf470d8249e75d21d419d2', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 11:03:00');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('46a70c71364c4564a05548fa720f6f03', '41804268bde5404683719c0e60b87c0a', 'f50cece6cb034bd2b4184ca6b60aa3c6', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 09:53:24');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('477c11533ce04783ae8ef4799611a875', '41804268bde5404683719c0e60b87c0a', 'ce2ce2a4c8434037a824086f95d7ecae', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:32:05');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('48e26a14b4ee4eb7860a15a1c9d05e4d', 'd072b8ffa8ba4ebea09fc8a63095ba59', 'd618fdb8a9eb473aaec17b3fe816bd53', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:53:32');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('4a65ba6fe32d473587bd3c3016bc7d85', '41804268bde5404683719c0e60b87c0a', '83229d107eb44fb9a298dce88cfcfd16', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:22:26');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('4e8eb8872dbbe1e7617bf67b4343faa1', 'eeaeecca27e54287afbcdc72915db7fe', '899a86b482b4434da109c446253f1a5b', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('4fdf576cc0574120b68bbd1e857b02bc', 'e8b047bed0414f20bfadba637df79d2d', 'a1191d2b1ed84367a4a1005a3be71620', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:50:51');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('5160d31687da47ed97188e65ee488eb2', '8ea8588366ec41e0a77189b5838d822f', 'f45e144b04274c5b9028be20d3a3737b', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:38:12');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('51d332f22bf14c33837e3036e3fcc618', '8397e7f441b24f32938e7c9a5538af11', '012de0ef6a1343948ff2974988c8566c', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:50:01');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('537c8918081941939fc790d547dd458c', 'e8b047bed0414f20bfadba637df79d2d', 'b2fe507249654114a2c6c9009e8c5184', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:39:55');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('5539276d627a49108cc196ed3d9f54c4', 'e8b047bed0414f20bfadba637df79d2d', 'aa5a2cdc583347e1861e3cdd2de8aa4e', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:28:10');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('571f2c6d71c3b9a0903e3ac585a3575b', 'eeaeecca27e54287afbcdc72915db7fe', '33ba9f5c8d5d4740bfef34f90a64100b', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('5aa021412abb4fd58e429ae5d4c69d84', '8ea8588366ec41e0a77189b5838d822f', 'c0108f5f8c89415db7f11a8914a4256c', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:49:59');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('5b03186e345f42e6898aab155594ef5d', '36ab4e2464aa48a1896d8de17e6df0e2', 'a4e9782a866f4f95890e33a9b237e289', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 17:36:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('5c55aa075e0d41ccb3c60a3bc655d118', '8ea8588366ec41e0a77189b5838d822f', '7e633e5347914bbc887906975c1ef372', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:38:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('5ce36b6ac782471f9c9092fd4df97dbf', 'e8b047bed0414f20bfadba637df79d2d', 'a5d3e27f4d464e6d9a7f636d1ed47363', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:42:00');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('609120f4fdc44142ac6a76f6adeb6c3d', '56077d94f21f4c0f9452cf50329c172e', 'be84ae5fdad746aa946d32dcd4256a07', 4, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 11:04:23');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('60b855f71c4b40de85712dfadd39d727', 'e8b047bed0414f20bfadba637df79d2d', '9de33e0b8007404984a9ae56dbd561d5', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:39:09');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('618d86bc2b464dbfa34fdc35e99b6435', '36ab4e2464aa48a1896d8de17e6df0e2', '05a49eb8c8fb4f32bee487e535e2845f', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 16:59:05');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('631d78c99b20403ea3f4342c72166bfa', 'e8b047bed0414f20bfadba637df79d2d', '1ce3018db61f4c0c83496e23133510e6', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:23:45');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('63251d717c6a7e2e8201f30f3a6d002c', '5a453eca55fb495a849fdd2851483e12', 'd13d731b1bf1494a9d4fa1eb5adc46a4', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('6648b6c942e9786ebd0d7d7b54730f15', 'eeaeecca27e54287afbcdc72915db7fe', '74a16a5219dc4f8594438df3379facb4', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('668652ee96dc4e9d9c54246aff3ef3ce', '36ab4e2464aa48a1896d8de17e6df0e2', 'cfba546884564ff0a554015f62c106c3', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 16:38:48');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('681229ad04414b8e83d12aec4748ed1f', '8397e7f441b24f32938e7c9a5538af11', 'ef87144940ef48478fda6d685d1f04f5', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:36:28');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('6831ef16f78c430ea284dfc079c4aa9e', '8ea8588366ec41e0a77189b5838d822f', 'd3fe5df591fb4c50b5c303a6bd3c8b6b', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:58:35');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('694fa71c620141918424c843c3eae624', '8ea8588366ec41e0a77189b5838d822f', 'a3a9b440deab4f6c9d7db17df03bc71a', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:51:29');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('6a4f7e358f5a9f6781740985334875f6', 'eeaeecca27e54287afbcdc72915db7fe', '212f7ddd1a20496bb89e05a31f6365c3', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('6ae06b18df614dd8b27aa3d23b729cde', 'e8b047bed0414f20bfadba637df79d2d', '9a9d4979b76f4e968107a7d06d1a8fa7', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:34:00');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('6b5fc8bdc53145468aec0cd7a548162b', '8397e7f441b24f32938e7c9a5538af11', '74715fff0c1e4a3f8fa83812dd86ca60', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:46:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('6e3515e682bd49d08cd02e304f58bb9d', 'e8b047bed0414f20bfadba637df79d2d', 'e1d5aef017e749a8bc8f7a1e519926b5', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:21:48');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('730026c7b81d48189ce6353a06a9b4d1', 'd33bd216a4b74810aef35174cfa71216', '13f34ad117da4b83bb5dfb3c3eb535ad', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:21:42');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('731b23d6464a43afbf2d2ba492ef8704', '8ea8588366ec41e0a77189b5838d822f', 'd48c475280944a6da8a9ca5a4b835351', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:33:36');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('737f446884b8d2d6b20cbcb4a9e9b92c', 'eeaeecca27e54287afbcdc72915db7fe', 'f4525b4bf9b940429173eaaa9d33c114', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('73e4e8a5fddd4cde9fddba4eef9e9389', '8ea8588366ec41e0a77189b5838d822f', 'cd6cb47b2b9746e5be2d80b1f67eefb6', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:52:42');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('74936417665a4ed1b82a7e5cc850314f', '41804268bde5404683719c0e60b87c0a', '6942e9c99a7b48f2844007fdd399d0c1', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:14:25');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('767932e2d1384c09bb181cc4edeb0f09', 'd072b8ffa8ba4ebea09fc8a63095ba59', '7e79b35525cd4de9a6b6c07c12ae099e', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:56:35');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('7bc94e0bfdb748608d6f11861a83337a', 'e8b047bed0414f20bfadba637df79d2d', '90fdde057de144ed86ad3bda10a79dce', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:56:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('7c61ef7e1eff48cfb94e1b2360c5e461', '4e2d4f2e71804139aab21a4d9e2db23e', '396a481662644957bbb637755a618712', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 14:11:56');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('7cc281d54ba141d78e9ee89f21bf2ed8', 'e8b047bed0414f20bfadba637df79d2d', 'e73d5f2dbd4848f1975496e4cbda59d4', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:09:21');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('7ec6f1da828d44fcbdc97db17cffe082', '8ea8588366ec41e0a77189b5838d822f', 'e038d12f87924f1090a3ad7328b93a0d', 2, '13641225a0724418a05e3b2c6a3adc5e', '2022-06-11 20:45:17');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('81210bd6904945e0b25c82fe876d7608', '36ab4e2464aa48a1896d8de17e6df0e2', 'af82ae45f7f44e05b225175cf1c517d6', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 15:51:02');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('82099d2db47040a88a3fe2b765f03c23', '8397e7f441b24f32938e7c9a5538af11', '0f90021a2c1542ef9a4ed0a3edd40bbf', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:52:44');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8596cee239447e13c941b87f045cbc48', 'a8919101f7ec475eb88a49eeddeed8fe', 'c94827d4c764488fae7d68456ce764c7', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8758e2f1163a4119815c6a00444d58ee', '5c6b3c06df2d490e94dd157833afbbe0', 'ce0abc83737b4d3bba03d1bb9ee46e0f', 4, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-14 17:35:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('879b95e095d44afdbfae52dbb68618f3', 'd33bd216a4b74810aef35174cfa71216', '041e15d5fb594003b67370c2af1b30b9', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:22:02');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8813e018f20e44aea02d06261e8fea9f', '8ea8588366ec41e0a77189b5838d822f', '83f2f3b8a0e54f27a108ba69cb47ae43', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:27:29');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('89c69854bff9450fac1b763f3bdddb27', '4e2d4f2e71804139aab21a4d9e2db23e', '569698ea2dc7469f8932ffa2f176a328', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 14:24:25');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8a6de3a725ce48ac942596c17e42396d', 'e8b047bed0414f20bfadba637df79d2d', 'd817b136fac045aeb3f21594abb429e1', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:10:51');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8abe301aff374a4c91af698026b5c8d9', '8ea8588366ec41e0a77189b5838d822f', 'f5c90b0b4bb94b3e899bb32d0fc42385', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:44:06');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8b12bb1ba34d45b9a09b4c6e6d0a2bcd', '41804268bde5404683719c0e60b87c0a', 'c4f88ec99f234f849128acea81e4dba9', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 14:19:53');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8cdc6d84bc5a44a190e19c552b35e9a6', '36ab4e2464aa48a1896d8de17e6df0e2', '1139c3d98c9745a3a6d2e66ac51a29dc', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 15:47:04');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8d2708a74fdd47b599933c60de378378', 'e8b047bed0414f20bfadba637df79d2d', 'a457516067214d7f95c8297420127502', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:20:21');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('8edba28cd26344f49b03d4921f236068', '41804268bde5404683719c0e60b87c0a', '2caa7c86bf31423b977958fcafa3c910', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:00:04');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('91090cbbd80e4ba7a62de45b5005bf5f', 'd8f42d1b0bcb496a85ca29e60c274b2e', '3438bf86659b4851a5106a7f89e14a3c', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:35:22');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('917f8aa47fc97e08cea6a2891559e51b', 'f557cd2bac244e9cac5a70836c5cb87c', 'b3b849c8b07841eabbc7204ecab964ff', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('92e136c4a2b94025a5146e5f73a8e088', 'e8b047bed0414f20bfadba637df79d2d', '2e9c21ca655b4b209f7b392215b7fa9f', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:16:46');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('9384ee3645dc4cb5a85cf865aa7feb7e', '8397e7f441b24f32938e7c9a5538af11', 'e4d11405aaf64b8c81e5ee9995778b24', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:25:47');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('974a1dbcd7234f74828d448a78b3fcbc', '4e2d4f2e71804139aab21a4d9e2db23e', '51c2d600095346b69192886034bc2b52', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 14:09:55');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('9774e8f60af049e7acdc11ebbda11f39', 'd8f42d1b0bcb496a85ca29e60c274b2e', 'f5b974d085de424694688f144b649a2f', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:34:40');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('97a2f7f5a7688d253e9e8183e1c6081c', 'd8f42d1b0bcb496a85ca29e60c274b2e', '5f225909e1144bd49cc9109a509bb230', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('9ab9b378a33e4b95a9a6c577d8798d73', 'e8b047bed0414f20bfadba637df79d2d', 'abe8865e2f8b4564a04898561d7487cc', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:17:18');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('9af42f249b8645f9a456afa35651b63f', 'e8b047bed0414f20bfadba637df79d2d', '481d80348ce44c9cb3bb1c5e4c160ed3', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 10:37:39');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('9bfe3b1f2e3a49acb8aa97374d4819a0', '41804268bde5404683719c0e60b87c0a', '66f4c6da5693474f832e006de86b03f7', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:36:43');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('9f269131ed1c4a6a9455a0d6f7b5a32c', '8ea8588366ec41e0a77189b5838d822f', '082e4c7a5f7c4264a24d3557d088ee6d', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:36:37');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('a085d5ec08ec45a7b675d370298be775', '5a453eca55fb495a849fdd2851483e12', 'b3c85907268c4fd69837f7f39844237b', 2, 'a7d903b65e8c42479b9774db664f9468', '2022-06-09 10:31:58');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('a20b817c72d7480c9af7153c2e846509', 'd072b8ffa8ba4ebea09fc8a63095ba59', 'c8ae526e7a0f4831a542530b7e8d0b5e', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:39:03');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('a7b499cf7ee24324841d08c709c353a5', '8ea8588366ec41e0a77189b5838d822f', 'b035217543154c7d9de92bf3244479ac', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:29:51');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('a82e5de12a0642dab083a2b9f4f06774', '56077d94f21f4c0f9452cf50329c172e', 'a6454cc563bc4930b14ac312ed9430f4', 4, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 10:57:36');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('a8978fcc3de689105d9800a9658b60b8', 'd8f42d1b0bcb496a85ca29e60c274b2e', '2fb22181a5b0448dacccad55f5d14c76', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('a9f2aa081e4949b79d03d29bf986f0aa', '8ea8588366ec41e0a77189b5838d822f', '1545d1a600a542f4bacecc580fd50f0c', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:45:00');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ab01fbbfb7aa47b5aa8fdcf60edc4a7b', '8ea8588366ec41e0a77189b5838d822f', '5d97fae6e4b64f22ac16a26988caa883', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:34:55');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ab4d0e772d3744c08cd4a2448fb98c4e', 'e8b047bed0414f20bfadba637df79d2d', '6a7e774302f849478fe6a7843e6df391', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:17:12');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ac14ffa58f4a427eacc250b29ee69653', 'd8f42d1b0bcb496a85ca29e60c274b2e', 'ca90bb1198a84274b042a21a4f5ca84a', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:34:13');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ae3736e77dc54dbbb55b3b91c7e415ed', '5c6b3c06df2d490e94dd157833afbbe0', '8be741b4126e46b6844f1adf56166441', 4, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-12 21:55:41');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b0c9f03f2bd9467fa0819d9b98ae6347', '8ea8588366ec41e0a77189b5838d822f', '6a82b2c260624f058637545e31cb6847', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:31:04');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b16a1a4bfc72463a9538a57cf4bbca62', 'e8b047bed0414f20bfadba637df79d2d', '9c7aed9597c049eba88954e1ab4ffcc1', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:18:43');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b5a009da81ae4267bccde0d076cccabd', 'd072b8ffa8ba4ebea09fc8a63095ba59', '5d827d64c7574a83982ca13f22c4a015', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:27:11');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b8226f8c395541c29e0150d9a8844af3', '36ab4e2464aa48a1896d8de17e6df0e2', 'f8d4c20970914f46be4d9c0b8b0feb21', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 16:40:48');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b925a42dd2954540a970e70a36732f49', '8ea8588366ec41e0a77189b5838d822f', 'dc17cdb0a97042b5842fa164c71c31c4', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:57:06');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b9dbe6571b1e4ede9c88bd9fb1cf0aaa', 'd072b8ffa8ba4ebea09fc8a63095ba59', '999a0462f13843cb8b67dbe64c4c2831', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:08:25');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('b9fe55d52419429b85801098cb6bd665', '4e2d4f2e71804139aab21a4d9e2db23e', '2766f6c927984537b73c821485245326', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 11:38:37');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('bb71bf4db96e427e9ad4c455c90f5125', 'e8b047bed0414f20bfadba637df79d2d', 'e9ae2a15a2e74f62bef6afd0801e26dd', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:26:55');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('bb81095998464b8eafd5e8b577b371a2', 'e8b047bed0414f20bfadba637df79d2d', 'f16783468815412a808b5a0755379533', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 16:55:58');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('bd5ce34bdf224c2cb5a679cb937675ae', '8397e7f441b24f32938e7c9a5538af11', '8b109338bf524b93a87a7816cb064a82', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:59:54');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('bef09ee63f0e43e7b1d0d48c6c484e5c', '8397e7f441b24f32938e7c9a5538af11', '3811e5aed7594c929587393905a6a5f2', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:22:12');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('bf109e4073d64dce8eb973288d5cd16d', '8ea8588366ec41e0a77189b5838d822f', 'e546e2e0f0594aea93529c4c58825c7c', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:50:48');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('c0b498c6fe5a456a93cf770b7ae28c65', 'd8f42d1b0bcb496a85ca29e60c274b2e', '571f11f027104426a18fcb93707cdf4d', 2, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-10 10:29:44');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('c60c54c25a624438b0a545318496005b', 'e8b047bed0414f20bfadba637df79d2d', 'f12f879afb844015946d5604218e468e', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:19:07');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('c765ec7d3359447db4dc011b7a3cc8ed', '4e2d4f2e71804139aab21a4d9e2db23e', '15eed5c29aac436faddd97634e4d0824', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:53:26');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('c846363074be4c2a82936108f294f75b', '8ea8588366ec41e0a77189b5838d822f', 'cdbb51365733477fae43c45c51e6ddee', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:59:51');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('c8dde9ffce504e60876dcc5b18e02cad', '36ab4e2464aa48a1896d8de17e6df0e2', 'be899fd6aaf1475781b17857575cfaa7', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 17:07:08');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('c95264f14c7d44119964444c416e742f', 'e8b047bed0414f20bfadba637df79d2d', 'c237861ea16642d8a64ae9f7863161c3', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:44:04');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ca326374bb4d44bfb6b56c810d6e8cac', '8ea8588366ec41e0a77189b5838d822f', '63e49ca166c44561ba55d1ac4d61e8ab', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:38:28');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ccc67439df124ea4b76c1019e23169c6', '8ea8588366ec41e0a77189b5838d822f', 'c3a52a7d959f4dbba2a32e2617f62e14', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:49:10');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ccca8ba0b99144248e3b2bb9b7755788', 'e8b047bed0414f20bfadba637df79d2d', 'b7907edcad1b48188e0d6207d8e9b25b', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:08:57');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('cdb8fc30ffff80d32b7cab1d306327f5', 'cd25686ed2554d75bcdd0bf6eb4dc7a1', '792dcfb2452442c7966e292e8a0b9d8c', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('cdcb22335c0d413fbdd56fe700ba9578', '8397e7f441b24f32938e7c9a5538af11', 'f72afedafeb3410db2c6305178957788', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:44:36');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ce0227209fcc474490c3c326ddac8f55', '4e2d4f2e71804139aab21a4d9e2db23e', '6aa9db58df6947679c119ad03d721fcf', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 11:13:17');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ced7fb2538f046b185efa3f3b8e4a23f', 'd072b8ffa8ba4ebea09fc8a63095ba59', 'dee217c687224b63b8ba7f823fa45f87', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:28:54');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d059e72d6a6f4d44b8d140cf24c677de', 'e8b047bed0414f20bfadba637df79d2d', '8b07f1f2c34244a6959c7159e687aa6b', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 16:56:11');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d0a845a8959ad0a618f3112097f1b866', 'eeaeecca27e54287afbcdc72915db7fe', 'f76e85dd0b50443dbec267918d82c375', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d0e27f3ecec447bbadc508b705032494', '36ab4e2464aa48a1896d8de17e6df0e2', '978a1833596c4cc98b5e36af1bc31c98', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 17:04:09');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d0e49ad3071f41938e74ff54479527cd', '8ea8588366ec41e0a77189b5838d822f', '69d305168d2c4429bde369dd5322ad20', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:39:12');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d6b6d131cdc34044a4235cacf418548b', '8ea8588366ec41e0a77189b5838d822f', 'ddde42eb19574b89b9dc8ffc14819465', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:20:03');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d816314e900d4f0b8fdcb12b2c18e96d', '4e2d4f2e71804139aab21a4d9e2db23e', 'c5f91d9b3813409188715543746391db', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 11:29:49');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d8204538d99e491f83e96f6a351c117f', '36ab4e2464aa48a1896d8de17e6df0e2', 'bfc0fc091f854a2786c3cc2b775c6e7e', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 15:10:38');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d86d984a4c974555aebc5de6613d23a3', '4e2d4f2e71804139aab21a4d9e2db23e', 'aeeb917ff1d64f478250706221df71d2', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 14:26:34');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('d8ffdee0e1fb4cb89e4f9d31f649d8ba', 'e8b047bed0414f20bfadba637df79d2d', 'ce3873614966440f8fcd9238856bc53c', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:46:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('dc11c297d92d4b31a06024d3240e2c89', '41804268bde5404683719c0e60b87c0a', '117702b208e84d0faee803d51ee52bdf', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:15:52');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('dd5bae945b724deca2b50459e3525460', '8397e7f441b24f32938e7c9a5538af11', 'fcb3971711e24861b079c54e23abb701', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:33:35');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ded01f19ebc94a14a22c5186329b88b9', 'd072b8ffa8ba4ebea09fc8a63095ba59', '5bd189beb00f46938913367341cf8545', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:17:58');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e032a188fd914908b838be1064fee9da', '36ab4e2464aa48a1896d8de17e6df0e2', '82dbb5289c30488b9c3bb996e51e79f3', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 17:20:18');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e077f5be8c2147b093d27878059e53d3', 'd33bd216a4b74810aef35174cfa71216', '0aecd4ac005e4c63a09332fee49230d6', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:19:41');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e07933779094440cb6a0fb115aefc6ac', 'e8b047bed0414f20bfadba637df79d2d', '97d8d710a49940aa96db883fb82bb07b', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:47:50');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e0c2aa4679d54d33aff2c8494e08743b', 'd8f42d1b0bcb496a85ca29e60c274b2e', '2ec6cb0819f6427e98802f62af85c834', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 20:24:43');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e131cc71d3d6483b8ff1286ee985147a', '36ab4e2464aa48a1896d8de17e6df0e2', 'b4912c8c2ced44da9f78ed2e338c52e3', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 15:03:56');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e1afdcaf0e8d456a9a081ebe7c31efdc', 'd072b8ffa8ba4ebea09fc8a63095ba59', '4b3ba7310e324b1995f46404a1e5cc8e', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:58:29');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e1c5ceb85aaa4d719d50d6b89958906a', '41804268bde5404683719c0e60b87c0a', '192256d616c14f56835faf40f4b5a021', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:16:37');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e851c6b63848af59d4891475f8bbecb4', 'eeaeecca27e54287afbcdc72915db7fe', '7e0195d9f2cd43fc90d097834d9b3ebf', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e931a0e238974d44aa2b026e8c611246', 'd33bd216a4b74810aef35174cfa71216', 'b7a2ab69c54c4ccf8614c10b78de425c', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:19:00');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('e98e65bb5fcb4cb99c348174e2b87917', '41804268bde5404683719c0e60b87c0a', '9b1aaeead0fd42c0824900b1d1522459', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:55:55');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('eae78e5121ba4d84b6be48915d9ad440', 'd33bd216a4b74810aef35174cfa71216', 'a3b9eaa123de4862ad26ed32fb93c5ef', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:18:18');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ebda35990d6f4ae586836d81154b2c1b', '8ea8588366ec41e0a77189b5838d822f', '2bf4e2e1420044c2bef31c77d6ad7036', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:32:19');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('ec1ed137253043f9add6a933914d933c', 'e8b047bed0414f20bfadba637df79d2d', 'b0fd2d2deaae419a99398922a8151d07', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:26:16');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('edc7d150dc8343bb8c4a433486ac6c69', '4e2d4f2e71804139aab21a4d9e2db23e', '397e6ee1261a45fe946f49402a800d57', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 10:59:37');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('effa4fd80420445ba064e909327a7911', '8ea8588366ec41e0a77189b5838d822f', '13f75c1566e4418db09c7767944ef85c', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 16:35:15');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('f22240bfb558364928a1d2a628aa6489', 'a8919101f7ec475eb88a49eeddeed8fe', '087a57170c0442cabf45075ad26965d4', 2, NULL, NULL);
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('f45c02c089df4ffabbe6d24083803b87', '36ab4e2464aa48a1896d8de17e6df0e2', 'aee5be026d1146398b6da20d63bf51da', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 17:37:12');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('f5746e4f44c54351abcb46ac54f1694c', '8ea8588366ec41e0a77189b5838d822f', '50efcdb2291d4da6a57e6ed1646c39b6', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 15:37:22');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('f718d84711214d84a2c80ae6225b36f6', 'd072b8ffa8ba4ebea09fc8a63095ba59', '469fe1e72817467789da9279c7b22ff0', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 12:00:07');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('f7f7a3a6bba7408bb427fe6e56c98193', '4e2d4f2e71804139aab21a4d9e2db23e', '0942c809190a4b9687f9a51f4a3076dd', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 11:34:30');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('fdd660ef258b4c95ae07e6302610fb54', 'e8b047bed0414f20bfadba637df79d2d', '0bd13228b8a34566867f06f6c2b30323', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:24:20');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('fde79a87c815467b937ceda6ffd9704b', 'f557cd2bac244e9cac5a70836c5cb87c', '671d412170a947d69f58569104e5a401', 2, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 11:19:22');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('fe596a691ad6459298328104bed1c5eb', '8ea8588366ec41e0a77189b5838d822f', '6563457c1b684b69ba224737f55b0f8f', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:48:06');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('fed72df2b3ee4cbd8eeb5129fd88d751', '8ea8588366ec41e0a77189b5838d822f', '262bb380f7f84ffc9ae038d53082cdfc', 2, '8c263f81d2b14784906787d67ce58b47', '2022-06-10 14:34:40');
INSERT INTO dev_power_link
(id, tree_id, power_id, power_type, who_created, when_created)
VALUES('fefccc1e59e242e8824fefae5bcd64c9', '41804268bde5404683719c0e60b87c0a', '6e9115d3781d451dbc088ea15271158f', 4, '8c263f81d2b14784906787d67ce58b47', '2022-06-13 14:17:55');



