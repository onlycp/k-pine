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

-- 逻辑编排模板
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('012de0ef6a1343948ff2974988c8566c', '获取字符', '8397e7f441b24f32938e7c9a5538af11', '<h2>获取字符</h2>
<p>获取字符</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc",<br /></span>  "index":1
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"b"</pre>', '[{"angle":0,"position":{"x":-20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"获取字符"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar index = getResult(\\"index\\")\\nvar res = stringCharAt(str, index)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"获取字符","zIndex":119,"label":"获取字符"}]', '[{"id":"6b0e001f6e224296a3fa5391b38c1489","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->获取字符","type":"link","shape":"link"},{"id":"09cad6da3c97428f92f17d8fee619bb7","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"获取字符->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:49:37', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:50:01', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('0bd13228b8a34566867f06f6c2b30323', '加日期加年', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期加年</h2>
<p>加日期加年</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("year", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2019-06-02T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-110,"y":280},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"加日期加年"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"7625a1e050444c89890b660e53d7daa0","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"\\nvar date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar year = getResult(\\"year\\")\\nvar res = dateAddYear(date, year)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"加日期加年","zIndex":9,"label":"加日期加年"}]', '[{"id":"52dcaa29ea394db98059f8dca58e0ff9","label":"","source":"start","sourcePort":null,"target":"7625a1e050444c89890b660e53d7daa0","targetPort":null,"expr":"","name":"开始->加日期加年","type":"link","shape":"link"},{"id":"856292584d4b43cb8078bd9327196f4d","label":"","source":"7625a1e050444c89890b660e53d7daa0","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期加年->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:24:20', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:24:20', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('0f90021a2c1542ef9a4ed0a3edd40bbf', '获取长度', '8397e7f441b24f32938e7c9a5538af11', '<h2>获取长度</h2>
<p>获取长度</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc"</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>3</pre>', '[{"angle":0,"position":{"x":-20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"获取长度"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar res = stringLen(str)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"获取长度","zIndex":126,"label":"获取长度"}]', '[{"id":"f53522251dbf4a7eb9a528e0bc0f5c7a","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->获取长度","type":"link","shape":"link"},{"id":"2932ea751ddb47f189e257467ae758d2","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"获取长度->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:52:44', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:52:44', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('11727a7c7bea4c64818b9f62bca71878', '拆分字符串为集合', '8397e7f441b24f32938e7c9a5538af11', '<h2>求List中所有的数字最小值</h2>
<p>求List中所有的数字最小值</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc"</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>["a","b","c"]</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"拆分字符串为集合"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar res = stringToList(str)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"拆分字符串为集合","zIndex":67,"label":"拆分字符串为集合"}]', '[{"id":"00c137d3f3d6496e8363bba181f6795f","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->拆分字符串为集合","type":"link","shape":"link"},{"id":"7443c835729a40dab50f4acdc0bf87ea","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"拆分字符串为集合->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:29:03', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:29:03', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('117702b208e84d0faee803d51ee52bdf', '实例化一个ArrayList', '41804268bde5404683719c0e60b87c0a', '<h2>实例化一个ArrayList</h2>
<p>实例化一个ArrayList</p>
<p><strong>参数如下：</strong></p>
<pre>不需要参数</pre>
<p><strong>返回数据结构：</strong></p>
<pre>[]</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"实例化一个ArrayList"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var res = listNew();\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"实例化一个ArrayList","zIndex":66,"label":"实例化一个ArrayList"}]', '[{"id":"339677faf92d497d898b4a537928cd12","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->实例化一个ArrayList","type":"link","shape":"link"},{"id":"f681b8852901415f8231106f758b9813","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"实例化一个ArrayList->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 14:41:07', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:15:52', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('14785aa058eb4e15a7aeb175ea5cbf3f', '判断是否为数字', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>判断是否为数字</h2>
<p>判断是否为数字</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":10</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>true</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"判断是否为数字","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-20,"y":130},"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathIsNumber(num)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"判断是否为数字","shape":"task"}]', '[{"id":"87e85a47274e4aa48d58ec7f5389c1ae","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->判断是否为数字","type":"link","shape":"link"},{"id":"159ffe53249d47638f40b0071db47e1a","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"判断是否为数字->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:05:55', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:05:55', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('192256d616c14f56835faf40f4b5a021', '从List当中删除对象', '41804268bde5404683719c0e60b87c0a', '<h2>从List当中删除对象</h2>
<p>从List当中删除对象</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3],<br /></span>  "item":2
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>[1,3]</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"从List当中删除对象","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-30,"y":130},"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar item = getResult(\\"item\\")\\n\\nvar res = listRemove(list, item)\\n\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"从List当中删除对象","shape":"task"}]', '[{"id":"99574605feca432b8854cdd51dfd3c4e","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->从List当中删除对象","type":"link","shape":"link"},{"id":"c8e257cf00354c268c9cf8ccd78f98e3","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"从List当中删除对象->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 14:15:36', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:16:37', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('1ce3018db61f4c0c83496e23133510e6', '取分钟', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取分钟</h2>
<p>取分钟</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>23</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取分钟"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateMinute(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取分钟","zIndex":36,"label":"取分钟"}]', '[{"id":"c330b088ff164fdfa8bec5c81173beb9","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取分钟","type":"link","shape":"link"},{"id":"818ad0359ffe4f4da88dc9186670f403","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取分钟->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:23:45', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:23:45', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('2caa7c86bf31423b977958fcafa3c910', '求List大小', '41804268bde5404683719c0e60b87c0a', '<h2>求List大小</h2>
<p>求List大小</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3]</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>3</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"求List大小"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = listSize(list)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"求List大小","zIndex":15,"label":"求List大小"}]', '[{"id":"89dd6ace74684c5f8e93ac6e47bf56cb","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求List大小","type":"link","shape":"link"},{"id":"9ed3fc669a1d431c8fd4e97e2a77cfa9","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求List大小->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:00:04', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:00:04', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('2e9c21ca655b4b209f7b392215b7fa9f', '字符串转日期', 'e8b047bed0414f20bfadba637df79d2d', '<h2>字符串转日期</h2>
<p>字符串转日期</p>
<p><strong>输入参数如下：</strong></p>
<pre>//需要注意的是，底层原理是通过索引位置截取年月日时分秒的，因此分隔符并没有限制，但需要保持长度是19位(中间分隔符不能省略)<br />//时分秒可传可不传，不传默认就是00:00:00<br />setResult("date", "2022-06-02")<br />setResult("date", "2022-06-02 18:00:00")</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-02T10:00:000Z"</pre>
<p><strong>特别注意：</strong></p>
<ul>
<li>因为无法通过setResult直接保存对象，所以字符串转成日期类型后，是通过JSON.stringify处理过的</li>
<li>在下一节点转回日期类型？JSON.parse()无效，但因其值是带有时区格式的字符串，因此可以直接被new Date解析</li>
<li>所以处理方法如下：</li>
</ul>
<pre>var d = getResult("result")<br />var o = new Date(d)</pre>', '[{"angle":0,"position":{"x":-50,"y":180},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"字符串转日期"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"c46fc701a13e45009dd58f349cd5fe9d","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\n//字符串转日期\\nvar res = dateParse(date)\\n\\n//这里的res是一个Date类型的时间对象，目前青松无法直接保存，需要先通过JSON.stringify处理\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n//注意：下一节点拿到result数据的处理方法（如何转回日期类型）？\\n//JSON.parse()无效，但因为其值是带有时区格式的字符串，因此可以直接被new Date解析，处理方法如下：\\n//var d = getResult(\\"result\\")\\n//var o = new Date(d)\\n","afterContent":"\\n","zindex":null,"name":"字符串转日期","zIndex":25,"label":"字符串转日期"}]', '[{"id":"8f24eaf24dbc49c4818c0ef0e1930ed2","label":"","source":"start","sourcePort":null,"target":"c46fc701a13e45009dd58f349cd5fe9d","targetPort":null,"expr":"","name":"开始->字符串转日期","type":"link","shape":"link"},{"id":"5f687ab25b6d4b7a868f1c5f7891a998","label":"","source":"c46fc701a13e45009dd58f349cd5fe9d","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"字符串转日期->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 10:59:43', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:16:46', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('3811e5aed7594c929587393905a6a5f2', '字符最后出现位置', '8397e7f441b24f32938e7c9a5538af11', '<h2>字符最后出现位置</h2>
<p>字符最后出现位置</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abcefa",<br /></span>  "item":"a"
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>5</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"字符最后出现位置"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar item = getResult(\\"item\\")\\nvar res = stringLastIndexOf(str,item)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"字符最后出现位置","zIndex":50,"label":"字符最后出现位置"}]', '[{"id":"8711e2fe16ac4d4e994212cdea33d9e4","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->字符最后出现位置","type":"link","shape":"link"},{"id":"fb4c0a226e2e40b68472f81fa4f13725","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"字符最后出现位置->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:22:12', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:22:12', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('43694b27881a46c69e5e45e5ab4ba1cc', '日期相减返回周数', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回周数</h2>
<p>日期相减返回周数</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 10, 15, 12, 15, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>4</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期相减返回周数"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnWeeks(date1, date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回周数","zIndex":122,"label":"日期相减返回周数"}]', '[{"id":"3954e6a39e374ae99f5f6ebf53576121","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回周数","type":"link","shape":"link"},{"id":"5878d81e160e43f59ac9455593b68d14","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回周数->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 11:12:41', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:12:41', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('469fe1e72817467789da9279c7b22ff0', '求正弦', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求正弦</h2>
<p>求正弦</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":0</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<div>
<pre><code>0</code></pre>
</div>', '[{"angle":0,"position":{"x":300,"y":345},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"求正弦"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathSin(num)\\nsetResult(''result'',res)\\n","afterContent":"\\n","zindex":null,"name":"求正弦","zIndex":85,"label":"求正弦"}]', '[{"id":"a156e6a6bcf74c928295bf00d91d266e","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求正弦->结束","type":"link","shape":"link"},{"id":"7ae027cdb92a4b2c86966aef61d0f33f","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求正弦","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 12:00:07', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 12:00:07', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('481d80348ce44c9cb3bb1c5e4c160ed3', '日期转字符串', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期转字符串</h2>
<p>日期转字符串</p>
<p><strong>特别注意：</strong></p>
<ul>
<li>因为无法通过setResult直接保存对象，所以上一节点传递日期类型时需要先通过JSON.stringify处理一下</li>
<li>fmt是可选参数，表示时间格式，默认是yyyy-MM-dd HH:mm:ss，改变时需要保持字母大小写和中间的空格一致（原理是字符串替换）</li>
</ul>
<p><strong>参数如下：</strong></p>
<pre>setResult("date",JSON.stringify(new Date()))<br />setResult("fmt","yyyy-MM-dd HH:mm:ss")</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-14 10:27:25"</pre>', '[{"angle":0,"position":{"x":-90,"y":310},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期转字符串"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"8debe021c015453b83458b2d3feaf40c","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\n//注意：这里获取到date是字符串类型，必须转成对象(无法使用JSON.parse解析)\\ndate = new Date(date)\\n\\nvar fmt = getResult(\\"fmt\\")\\n\\n//日期转字符串\\nvar res;\\nif (fmt === null || fmt === undefined || fmt === \\"\\") {\\n    res = dateFormat(date)\\n} else {\\n    res = dateFormat(date, fmt)\\n}\\n\\nsetResult(\\"result\\",res)\\n","afterContent":"\\n","zindex":null,"name":"日期转字符串","zIndex":39,"label":"日期转字符串"}]', '[{"id":"7238094f953f4647874090a55972fe8a","label":"","source":"8debe021c015453b83458b2d3feaf40c","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期转字符串->结束","type":"link","shape":"link"},{"id":"1b971c1e02024065819886ef6395a547","label":"","source":"start","sourcePort":null,"target":"8debe021c015453b83458b2d3feaf40c","targetPort":null,"expr":"","name":"开始->日期转字符串","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 10:34:52', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 10:37:39', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('4b3ba7310e324b1995f46404a1e5cc8e', '求正切', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求正切</h2>
<p>求正切</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":0</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<div>
<pre><code>0</code></pre>
</div>', '[{"angle":0,"position":{"x":300,"y":345},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"求正切"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathTan(num)\\nsetResult(''result'',res)\\n","afterContent":"\\n","zindex":null,"name":"求正切","zIndex":78,"label":"求正切"}]', '[{"id":"c303078ac0204f619361bc6a003a9862","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求正切","type":"link","shape":"link"},{"id":"d32a3a05d693469db2749bc36db0e807","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求正切->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 11:58:29', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:58:29', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('4b509549eda74be5b8f88531601c4a05', '加日期加天', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期加天</h2>
<p>加日期加天</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("dateNum", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-80,"y":250},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"加日期加天"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"d4125be4e8dc452fa1cdfa2adaea0fc9","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar dateNum = getResult(\\"dateNum\\")\\nvar res = dateAddDate(date, dateNum)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"加日期加天","zIndex":29,"label":"加日期加天"}]', '[{"id":"e652a583574949b599d1b03549380888","label":"","source":"start","sourcePort":null,"target":"d4125be4e8dc452fa1cdfa2adaea0fc9","targetPort":null,"expr":"","name":"开始->加日期加天","type":"link","shape":"link"},{"id":"51e118b1f21640fe84a7b5c7a8ace39e","label":"","source":"d4125be4e8dc452fa1cdfa2adaea0fc9","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期加天->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:30:35', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:30:35', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('4bd41f777d5b4d59872a21bee8822064', '求e为底的对数', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求e为底的对数</h2>
<p>求e为底的对数</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":2</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre><code>0.6931471805599453</code></pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"求e为底的对数","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":300,"y":355},"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathLog(num)\\nsetResult(''result'',res)\\n","afterContent":"\\n","zindex":null,"name":"求e为底的对数","shape":"task"}]', '[{"id":"0a7880cea9a54b738551b51560e4fdb0","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求e为底的对数->结束","type":"link","shape":"link"},{"id":"0ab11cf21fa4450eba20a93d1d98354e","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求e为底的对数","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 11:48:01', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:49:28', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('50a0b3b7c9534617b656a23149aa9667', '求余切', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求余切</h2>
<p>求余切</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":2</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<div>
<pre><code>-0.45765755436028577</code></pre>
</div>', '[{"angle":0,"position":{"x":300,"y":355},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"求余切"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathCot(num)\\nsetResult(''result'',res)\\n","afterContent":"\\n","zindex":null,"name":"求余切","zIndex":62,"label":"求余切"}]', '[{"id":"0ae6c3c3dff148248fa74a21618b19e8","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求余切->结束","type":"link","shape":"link"},{"id":"34d3655b27a74817b0a572d31b9a6644","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求余切","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 11:51:35', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:51:35', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('5bd189beb00f46938913367341cf8545', '四舍五入', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>四舍五入</h2>
<p>四舍五入</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":10.5</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>11</pre>', '[{"angle":0,"position":{"x":-20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"四舍五入"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathRound(num)\\nsetResult(\\"result\\", res)\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"四舍五入","zIndex":179,"label":"四舍五入"}]', '[{"id":"2de98f52f7ac4ef59ecf8103e4a05d55","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->四舍五入","type":"link","shape":"link"},{"id":"4998245678c849a685edeb016b84433f","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"四舍五入->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:17:58', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:17:58', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('5d827d64c7574a83982ca13f22c4a015', '最大值', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>最大值</h2>
<p>最大值</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3]</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>3</pre>', '[{"angle":0,"position":{"x":-10,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"最大值"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = mathMax(list)\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"最大值","zIndex":198,"label":"最大值"}]', '[{"id":"dcaf8253426d44cf934b54acfd2085c7","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->最大值","type":"link","shape":"link"},{"id":"f8cf0e20e3864aa69b8b4d090d004dd6","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"最大值->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:27:11', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:27:11', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('6445c079c4664b4fb9ff60e30004a331', '日期相减返回小时', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回小时</h2>
<p>日期相减返回小时</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 11, 15, 9, 15, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>3</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期相减返回小时"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnHours(date1, date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回小时","zIndex":113,"label":"日期相减返回小时"}]', '[{"id":"01b5a4c0f00a4bfc824e385af867c068","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回小时","type":"link","shape":"link"},{"id":"504416c2441c4c51bf360dad88a4732a","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回小时->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 11:06:50', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:08:36', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('66f4c6da5693474f832e006de86b03f7', '集合排序', '41804268bde5404683719c0e60b87c0a', '<h2>集合排序</h2>
<p>集合排序</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[3,1,2]</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>[1,2,3]</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"集合排序","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-10,"y":130},"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = listSort(list)\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"集合排序","shape":"task"}]', '[{"id":"511d37e30d2a4ff4b5834b4d05b67621","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->集合排序","type":"link","shape":"link"},{"id":"de89217bb1494e3f83fe126bdbc32b0f","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"集合排序->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:36:43', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:36:43', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('6942e9c99a7b48f2844007fdd399d0c1', '抽取集合属性', '41804268bde5404683719c0e60b87c0a', '<h2>抽取集合属性</h2>
<p>抽取集合属性</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json">{<br />&nbsp; "list":[<br />&nbsp; &nbsp; {"id":1,"name":"zs"},<br />&nbsp; &nbsp; {"id":2,"name":"ls"}<br />&nbsp; ],<br />&nbsp; "field":"id"<br />} </code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>[1,2]</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"抽取集合属性","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-30,"y":130},"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar field = getResult(\\"field\\")\\nvar res = listGetAttribute(list, field)\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n","afterContent":"\\n","zindex":null,"name":"抽取集合属性","shape":"task"}]', '[{"id":"d1c751f2477b426aad327bc223f798f1","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->抽取集合属性","type":"link","shape":"link"},{"id":"337a1f94b67843a5825541575bd57276","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"抽取集合属性->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 15:06:05', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:14:25', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('6a7e774302f849478fe6a7843e6df391', '取年份', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取年份</h2>
<p>取年份</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>2022</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取年份"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateYear(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取年份","zIndex":8,"label":"取年份"}]', '[{"id":"5cd12011dc5247c780d8ce99e562d054","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取年份->结束","type":"link","shape":"link"},{"id":"ea20083fb9c64f5e873e4d3b064d6d64","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取年份","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:12:57', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:17:12', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('6e9115d3781d451dbc088ea15271158f', 'List是否为空', '41804268bde5404683719c0e60b87c0a', '<h2>List是否为空</h2>
<p>判断List集合是否为空</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3]</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>false</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"List是否为空"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = listIsEmpty(list)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"List是否为空","zIndex":8,"label":"List是否为空"}]', '[{"id":"80d6b4407d0e40fea47ec783375c1226","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->List是否为空","type":"link","shape":"link"},{"id":"f08d08ca27bf44a1889456264b78c54f","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"List是否为空->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-10 16:26:57', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 14:17:55', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('74715fff0c1e4a3f8fa83812dd86ca60', '替换所有字符串', '8397e7f441b24f32938e7c9a5538af11', '<h2>替换所有字符串</h2>
<p>替换所有字符串</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abcb",<br /></span>  "subStr":"b",<br />  "newSubStr":"d"
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"adcd"</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"替换所有字符串","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-20,"y":130},"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar subStr = getResult(\\"subStr\\")\\nvar newSubStr = getResult(\\"newSubStr\\")\\nvar res = stringReplaceAll(str, subStr, newSubStr)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"替换所有字符串","shape":"task"}]', '[{"id":"0946be231082448d817a5d5a4770e052","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->替换所有字符串","type":"link","shape":"link"},{"id":"166a636fdeaa4afc8dedfe33dce7ad0e","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"替换所有字符串->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:46:20', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:46:20', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('7882cb65d3c946d9871fdf6f4870ecf9', '新增数据', '56077d94f21f4c0f9452cf50329c172e', '<h2>新增数据</h2>
<p>快速生成一个新增数据功能的节点组</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
  "id": "xxxx",<br /><span class="token property">  "name"</span><span class="token operator">:</span> "new name"
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
<span class="token punctuation">  <br />}</span></code></pre>', '[{"angle":0,"position":{"x":-210,"y":80},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"新增"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"d5bdd09402f34f7db2eb4921831ce606","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","beforeContent":"","content":" INSERT INTO biz_demo_user\\n(id, name, age, birthday, id_card, when_created, who_created)\\nVALUES(#{sys.uuid}, #{name}, #{age}, #{birthday}, #{idCard}, #{sys.when}, #{sys.who});\\n","afterContent":"","zindex":null,"name":"新增","zIndex":1,"label":"新增"}]', '[{"id":"b23bad89546844a78ccc4f022384cdd3","label":"","source":"start","sourcePort":null,"target":"d5bdd09402f34f7db2eb4921831ce606","targetPort":null,"expr":"","name":"开始->新增","type":"link","shape":"link"},{"id":"37cbfd0b78a4448ea04bc7ba7666ec8a","label":"","source":"d5bdd09402f34f7db2eb4921831ce606","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"结束->新增","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-06 10:55:52', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 10:55:52', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('7e79b35525cd4de9a6b6c07c12ae099e', '求余弦', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求余弦</h2>
<p>求余弦</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":0</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<div>
<pre><code>1</code></pre>
</div>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"求余弦","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":300,"y":345},"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathCos(num)\\nsetResult(''result'',res)\\n","afterContent":"\\n","zindex":null,"name":"求余弦","shape":"task"}]', '[{"id":"cf1ca463702544fcbb4e81529355f3fc","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求余弦","type":"link","shape":"link"},{"id":"5731adcef6c54405b743c65ee9ab781d","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求余弦->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 11:56:35', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 11:56:35', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('83229d107eb44fb9a298dce88cfcfd16', '指定对象是否存在', '41804268bde5404683719c0e60b87c0a', '<h2>指定对象是否存在</h2>
<p>指定对象是否存在</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3],<br /></span>  "item":2
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>true</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"指定对象是否存在"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar item = getResult(\\"item\\")\\nvar res = listIsExistObj(list, item)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"指定对象是否存在","zIndex":14,"label":"指定对象是否存在"}]', '[{"id":"765f82a5a47c49988e6bad5b3b1d9e16","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->指定对象是否存在","type":"link","shape":"link"},{"id":"5c99faa8d0324cc28487ec41329a749e","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"指定对象是否存在->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 15:22:26', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:22:26', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('8a73df8cc8cc4cb6a1beecd3affed184', '去空格', '8397e7f441b24f32938e7c9a5538af11', '<h2>去空格</h2>
<p>去空格</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"  aa  "<br /></span><span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"aa"</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"去空格"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar res = stringTrim(str)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"去空格","zIndex":41,"label":"去空格"}]', '[{"id":"684a55d7ad0b486b818189f021b63caf","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->去空格","type":"link","shape":"link"},{"id":"0f2a03a8a23e4b648754e615353168ed","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"去空格->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:17:00', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:17:00', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('8b07f1f2c34244a6959c7159e687aa6b', '减日期减年', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期减年</h2>
<p>减日期减年</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("year", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2019-06-02T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-160,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"减日期减年"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"e8cfeaa0801647dda7b89c54eeb3e167","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"\\nvar date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar year = getResult(\\"year\\")\\nvar res = dateSubtractYear(date,year)\\nsetResult(\\"result\\", JSON.stringify(res))","afterContent":"\\n","zindex":null,"name":"减日期减年","zIndex":23,"label":"减日期减年"}]', '[{"id":"3d9188a3dbe8401f9996fd9a0eccc0c2","label":"","source":"e8cfeaa0801647dda7b89c54eeb3e167","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期减年->结束","type":"link","shape":"link"},{"id":"89d7c909a7f9440fbdbd6ed5910af2aa","label":"","source":"start","sourcePort":null,"target":"e8cfeaa0801647dda7b89c54eeb3e167","targetPort":null,"expr":"","name":"开始->减日期减年","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 16:49:59', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 16:56:11', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('8b109338bf524b93a87a7816cb064a82', '转大写', '8397e7f441b24f32938e7c9a5538af11', '<h2>转大写</h2>
<p>转大写</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc"</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"ABC"</pre>', '[{"angle":0,"position":{"x":-20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"转大写"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar res = stringToUpper(str)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"转大写","zIndex":134,"label":"转大写"}]', '[{"id":"bc29fff351e54ac6981c5a9b1782753b","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->转大写","type":"link","shape":"link"},{"id":"80ccc840e4bb40b598a8d5c60a16fa12","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"转大写->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:59:36', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:59:54', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('8be741b4126e46b6844f1adf56166441', '决策（判断）逻辑', '5c6b3c06df2d490e94dd157833afbbe0', '<h2>决策（判断）逻辑</h2>
<p>快速生成一个决策逻辑的节点组，先设置条件，后判断分别处理</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
  "id": "xxxx",<br /><span class="token property">  "name"</span><span class="token operator">:</span> "new name"
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
<span class="token punctuation">  <br />}</span></code></pre>', '[{"id":"e8f6c880934d412f8218503e8d482b9a","label":"根据ID获取数据","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":540,"y":300},"beforeContent":"\\n","content":" select *\\n from biz_demo_user\\n where id=#{id}\\n","afterContent":"var cntList = getResult(''result'')\\nklog.info(''context:'' + JSON.stringify(context))\\nvar hasData = cntList?.length > 0\\nsetResult(''hasData'', hasData)\\nif (hasData) {\\n    var model = cntList[0]\\n    console.log(''model'' + JSON.stringify(model))\\n} else {\\n    setResult(''result'', ''error|没有找到数据'')\\n}\\n","zindex":null,"name":"根据ID获取数据","shape":"task"},{"id":"742f4619a345427fa21bd5db06588ebe","label":"存在数据则更新","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":220,"y":740},"beforeContent":"\\n","content":"UPDATE biz_demo_user\\nSET name=#{name}, age=#{age}, birthday=#{birthday}, id_card=#{idCard}\\nWHERE id=#{id}\\n","afterContent":"\\n","zindex":null,"name":"存在数据则更新","shape":"task"},{"id":"2aa02080689a40bb8a8b4b18089c87c3","label":"判断是否存在数据","type":"decision","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":567.5,"y":540},"beforeContent":"","content":"","afterContent":"","zindex":null,"name":"判断是否存在数据","shape":"decision"},{"id":"52c357eaec0f4794b758c0f9e32dcbc8","label":"不存在数据则做其他","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":860,"y":740},"beforeContent":"\\n","content":"\\n","afterContent":"\\n","zindex":null,"name":"不存在数据则做其他","shape":"task"}]', '[{"id":"eab72916c6d3414c8cab0e73eb4b2d6c","label":"","source":"742f4619a345427fa21bd5db06588ebe","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"处理结果->结束","type":"link","shape":"link"},{"id":"7c43be33751f496bafc0c81cc2b5e56f","label":"","source":"start","sourcePort":null,"target":"e8f6c880934d412f8218503e8d482b9a","targetPort":null,"expr":"","name":"开始->根据ID获取数据","type":"link","shape":"link"},{"id":"0bf55e23a4194f3fb0d4f196d19588e2","label":"","source":"e8f6c880934d412f8218503e8d482b9a","sourcePort":null,"target":"2aa02080689a40bb8a8b4b18089c87c3","targetPort":null,"expr":"","name":"根据ID获取数据->判断是否存在数据","type":"link","shape":"link"},{"id":"826bfd56c3db4010a6c8dc7cfed9fb98","label":"","source":"52c357eaec0f4794b758c0f9e32dcbc8","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"判断是否存在数据->结束","type":"link","shape":"link"},{"id":"6bd83e98b7cb44b79dbfd8b26f7d5518","label":"条件：(${hasData}==false)","source":"2aa02080689a40bb8a8b4b18089c87c3","sourcePort":null,"target":"52c357eaec0f4794b758c0f9e32dcbc8","targetPort":null,"expr":"compare(${hasData}==false)","name":"判断是否存在数据->条件2执行节点","type":"link","shape":"link"},{"id":"d86244d8a7ef4293821c88aeeceb99dd","label":"条件：(${hasData}==true)","source":"2aa02080689a40bb8a8b4b18089c87c3","sourcePort":null,"target":"742f4619a345427fa21bd5db06588ebe","targetPort":null,"expr":"compare(${hasData}==true)","name":"条件2执行节点->处理结果","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-12 21:55:41', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-12 21:55:41', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('90fdde057de144ed86ad3bda10a79dce', '减日期', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期</h2>
<p>减日期</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("num", 2)<br />setResult("type", "month")</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-03-02T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-190,"y":240},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"减日期"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"390cbd09e1e94bd480a5097d947a477c","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar num = getResult(\\"num\\")\\nvar type = getResult(\\"type\\")\\n\\nvar res = dateSubtract(date,num,type)\\n\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"减日期","zIndex":20,"label":"减日期"}]', '[{"id":"b031aad3081040a581027b2483426bae","label":"","source":"start","sourcePort":null,"target":"390cbd09e1e94bd480a5097d947a477c","targetPort":null,"expr":"","name":"开始->减日期","type":"link","shape":"link"},{"id":"371599f7c7bd438e83b49e5d875df78e","label":"","source":"390cbd09e1e94bd480a5097d947a477c","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:56:20', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:56:20', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('97d8d710a49940aa96db883fb82bb07b', '日期相减返回分钟', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回分钟</h2>
<p>日期相减返回分钟</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 11, 15, 12, 13, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>2</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期相减返回分钟"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnMinutes(date1,date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回分钟","zIndex":76,"label":"日期相减返回分钟"}]', '[{"id":"ce4ba8c3910d4a6f82cebcd33eb4eada","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回分钟->结束","type":"link","shape":"link"},{"id":"2dd1d4e6cec64daa80964847de8c1df5","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回分钟","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:47:50', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:47:50', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('999a0462f13843cb8b67dbe64c4c2831', '向上取整', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>向上取整</h2>
<p>向上取整</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":10.4</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>11</pre>', '[{"angle":0,"position":{"x":-20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"向上取整"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathCeil(num)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"向上取整","zIndex":161,"label":"向上取整"}]', '[{"id":"1f417a0d5148444ca9654f261b4baf18","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->向上取整","type":"link","shape":"link"},{"id":"662cace539e349ca9e86872978db1d32","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"向上取整->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:08:25', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:08:25', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('9a9d4979b76f4e968107a7d06d1a8fa7', '当前日期', 'e8b047bed0414f20bfadba637df79d2d', '<h2>当前日期</h2>
<p>返回一个表示当前日期的Date对象</p>
<p><strong>输入参数如下：</strong></p>
<pre>无</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-02T10:00:000Z"</pre>
<p>&nbsp;</p>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"当前日期"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var res = new Date()\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n","afterContent":"\\n","zindex":null,"name":"当前日期","zIndex":58,"label":"当前日期"}]', '[{"id":"d86f310becaf4a3dbec712044853d7d3","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->当前日期","type":"link","shape":"link"},{"id":"32fd395e0f994e6d85051ca99bf1e8f4","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"当前日期->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:33:27', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:34:00', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('9b1aaeead0fd42c0824900b1d1522459', '求List中所有的数字最小值', '41804268bde5404683719c0e60b87c0a', '<h2>求List中所有的数字最小值</h2>
<p>求List中所有的数字最小值</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3]</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>1</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"求List中所有的数字最小值","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-30,"y":130},"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = listMin(list)\\nsetResult(\\"result\\", res)","afterContent":"\\n","zindex":null,"name":"求List中所有的数字最小值","shape":"task"}]', '[{"id":"833cdb6cecc04c078ca4a32a80494869","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求List中所有的数字最小值","type":"link","shape":"link"},{"id":"d82b963a9683439e8d96d1ab21078542","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求List中所有的数字最小值->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 15:55:55', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 15:55:55', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('9c7aed9597c049eba88954e1ab4ffcc1', '取天', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取天</h2>
<p>取天</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>15</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取天"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateDate(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取天","zIndex":22,"label":"取天"}]', '[{"id":"c8e24b27eb05460d920f28f56409d81f","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取天","type":"link","shape":"link"},{"id":"7580dd17f5134ff385d2138202735e77","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取天->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:18:43', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:18:43', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('9de33e0b8007404984a9ae56dbd561d5', '加日期加时', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期加时</h2>
<p>加日期加时</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("hour", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"id":"66a6b5367fa8433a83d73485a228e864","label":"加日期加时","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-110,"y":280},"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar hour = getResult(\\"hour\\")\\nvar res = dateAddHour(date, hour)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"加日期加时","shape":"task"}]', '[{"id":"404e8088912c422b96ae662f7ba7e9c4","label":"","source":"start","sourcePort":null,"target":"66a6b5367fa8433a83d73485a228e864","targetPort":null,"expr":"","name":"开始->加日期加时","type":"link","shape":"link"},{"id":"b53a680b231642c580ce9f306cc7de29","label":"","source":"66a6b5367fa8433a83d73485a228e864","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期加时->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:39:09', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:39:09', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('a1191d2b1ed84367a4a1005a3be71620', '日期相减返回秒', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回秒</h2>
<p>日期相减返回秒</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 11, 15, 12, 13, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>120</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期相减返回秒"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnSeconds(date1,date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回秒","zIndex":83,"label":"日期相减返回秒"}]', '[{"id":"1f9b5b89e6234e7c8f4f65ea9a759323","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回秒","type":"link","shape":"link"},{"id":"cc0f48ba9c7c445c811148bd208385fa","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回秒->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:50:51', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:50:51', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('a457516067214d7f95c8297420127502', '日期相减返回相差时间', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回相差时间</h2>
<p>日期相减返回相差时间</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 11, 15, 9, 15, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>3小时前</pre>', '[{"id":"88ae5d2049a44c128fec24b666d47d78","label":"日期相减返回相差时间","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-200,"y":220},"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateBefore(date1, date2)\\n\\nsetResult(\\"result\\",res)\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回相差时间","shape":"task"}]', '[{"id":"48aa031e136244dfb7886662e0f46b67","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回相差时间","type":"link","shape":"link"},{"id":"32a6f0c8d64e488f828f2b4ef7230550","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回相差时间->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 11:19:06', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:20:21', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('a5d3e27f4d464e6d9a7f636d1ed47363', '加日期加分', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期加分</h2>
<p>加日期加分</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("minute", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-160,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"加日期加分"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"41da354287dc4d698ea82268b0f10ebd","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar minute = getResult(\\"minute\\")\\nvar res = dateAddMinute(date, minute)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"加日期加分","zIndex":51,"label":"加日期加分"}]', '[{"id":"c3c51af234e145b1a2c7eb94af7747ab","label":"","source":"start","sourcePort":null,"target":"41da354287dc4d698ea82268b0f10ebd","targetPort":null,"expr":"","name":"开始->加日期加分","type":"link","shape":"link"},{"id":"bba0b39ced374365b44128ea12a1f366","label":"","source":"41da354287dc4d698ea82268b0f10ebd","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期加分->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:42:00', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:42:00', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('a6454cc563bc4930b14ac312ed9430f4', '更新数据', '56077d94f21f4c0f9452cf50329c172e', '<h2>更新数据</h2>
<p>快速生成一个更新数据功能的节点组，会先判断数据是否存在再更新</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
  "id": "xxxx",<br /><span class="token property">  "name"</span><span class="token operator">:</span> "new name"
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
<span class="token punctuation">  <br />}</span></code></pre>', '[{"id":"62890f7cd5f7442ba6d9dcf9ddd838bd","label":"根据ID获取数据","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":550,"y":430},"beforeContent":"","content":" select *\\n from biz_demo_user\\n where id=#{id}\\n","afterContent":"var cntList = getResult(''result'')\\nklog.info(''context:'' + JSON.stringify(context))\\nvar hasData = cntList?.length > 0\\nsetResult(''hasData'', hasData)\\nif (hasData) {\\n    var model = cntList[0]\\n    console.log(''model'' + JSON.stringify(model))\\n} else {\\n    setResult(''result'', ''error|没有找到数据'')\\n}\\n","zindex":null,"name":"根据ID获取数据","shape":"task"},{"id":"2832b60fdf0b4c7e81feec9684bbca5a","label":"处理结果","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":300,"y":790},"beforeContent":"","content":"UPDATE biz_demo_user\\nSET name=#{name}, age=#{age}, birthday=#{birthday}, id_card=#{idCard}\\nWHERE id=#{id}\\n","afterContent":"","zindex":null,"name":"处理结果","shape":"task"},{"id":"6b8f4225e42c451c97f0a8ffcf3ca314","label":"判断是否存在数据","type":"decision","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":577.5,"y":660},"beforeContent":"","content":"","afterContent":"","zindex":null,"name":"判断是否存在数据","shape":"decision"}]', '[{"id":"9db29febfbc24c19a0c958d7ea47da66","label":"","source":"2832b60fdf0b4c7e81feec9684bbca5a","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"处理结果->结束","type":"link","shape":"link"},{"id":"e12e0286517141c5a372754fa0da1c37","label":"","source":"start","sourcePort":null,"target":"62890f7cd5f7442ba6d9dcf9ddd838bd","targetPort":null,"expr":"","name":"开始->检查数据是否存在","type":"link","shape":"link"},{"id":"671a7194c8cb4a4f94bfd79b91e0f4e9","label":"","source":"62890f7cd5f7442ba6d9dcf9ddd838bd","sourcePort":null,"target":"6b8f4225e42c451c97f0a8ffcf3ca314","targetPort":null,"expr":"","name":"根据ID获取数据->判断是否存在数据","type":"link","shape":"link"},{"id":"f064bcd52b37476288051326ad9c39cd","label":"条件：(${hasData}=true)","source":"6b8f4225e42c451c97f0a8ffcf3ca314","sourcePort":null,"target":"2832b60fdf0b4c7e81feec9684bbca5a","targetPort":null,"expr":"compare(${hasData}=true)","name":"判断是否存在数据->处理结果","type":"link","shape":"link"},{"id":"0f1b89f4e80e4a7c897c262922e4bed9","label":"条件：(${hasData}=false)","source":"6b8f4225e42c451c97f0a8ffcf3ca314","sourcePort":null,"target":"end","targetPort":null,"expr":"compare(${hasData}=false)","name":"判断是否存在数据->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-06 10:57:36', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 10:57:36', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('aa4ffc254f054dea857480752a325893', '指定起始的字符串截取', '8397e7f441b24f32938e7c9a5538af11', '<h2>指定起始的字符串截取</h2>
<p>指定起始的字符串截取</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc",<br /></span>  "start":1,<br />  "end":2
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"b"</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"指定起始的字符串截取","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-20,"y":130},"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar start = getResult(\\"start\\")\\nvar end = getResult(\\"end\\")\\nvar res = stringSub(str, start,end)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"指定起始的字符串截取","shape":"task"}]', '[{"id":"5ae618fc06764bc186b9f193a23f5797","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->指定起始的字符串截取","type":"link","shape":"link"},{"id":"d38ebbc81cf841738f93ff6ed46e25ab","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"指定起始的字符串截取->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:39:53', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:39:53', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('aa5a2cdc583347e1861e3cdd2de8aa4e', '取星期', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取星期</h2>
<p>取星期</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>3</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取星期"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateDay(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取星期","zIndex":50,"label":"取星期"}]', '[{"id":"cf5e74392a3b4b23b8895e8760383125","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取星期","type":"link","shape":"link"},{"id":"00afd127860d453392b097d138a771e6","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取星期->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:28:10', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:28:10', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('ab75ff7e4ec8448ebc717a546e463313', '列表分页查询', '56077d94f21f4c0f9452cf50329c172e', '<h2>列表分页查询</h2>
<p>快速生成一个分页查询列表功能的节点组，支持搜索、分页等功能</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
&nbsp; <span class="token property">"pageQuery"</span><span class="token operator">:</span> true,<br /> &nbsp;"pageSize": 10,<br /> &nbsp;"page": 1,<br /> &nbsp;"name": "张三"
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
<span class="token punctuation">&nbsp; "total": 24,<br />&nbsp; "pageCount": 1,<br />&nbsp; "pageSize": 24,<br />&nbsp; "page": 1,<br />&nbsp; "list": [<br />&nbsp; &nbsp; {<br />&nbsp; &nbsp; &nbsp; "birthday": "1652803200",<br />&nbsp; &nbsp; &nbsp; "whoCreated": "7aed8c297a6940f681c26eb6ab68893d",<br />&nbsp; &nbsp; &nbsp; "whenCreated": "2022-05-23 06:42:08",<br />&nbsp; &nbsp; &nbsp; "idCard": "2432342342342",<br />&nbsp; &nbsp; &nbsp; "name": "test",<br />&nbsp; &nbsp; &nbsp; "id": "93f8fb4325034be58d12cd01511c834f",<br />&nbsp; &nbsp; &nbsp; "age": 23<br /> &nbsp;&nbsp; }<br />&nbsp; ]<br />}</span></code></pre>', '[{"id":"8c9d8647dbee48548c5a06200cab6e09","label":"并行开始","type":"fork","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":550,"y":300},"beforeContent":"\\n","content":"","afterContent":"\\n","zindex":null,"name":"并行开始","shape":"fork"},{"angle":0,"position":{"x":220,"y":740},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"查询列表数据"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"ec799f06ad7048f49ecc6a44a9983e7d","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","beforeContent":"\\n","content":"<tpl>\\n\\n    select *\\n    from biz_demo_user\\n    where 1=1\\n    \\n    <if test=\\"id != null\\">\\n        and id = #{id}\\n    </if>\\n    <if test=\\"name != null\\">\\n        and name like ''%${name}%''\\n    </if>\\n    order by when_created desc\\n\\n\\n    <if test=\\"start != null and limit != null and pageQuery != null and pageQuery == ''true'' \\">\\n        limit ${limit} offset ${start}\\n    </if>\\n\\n</tpl>","afterContent":"setResult(''list'', context.get(''result''));","zindex":null,"name":"查询列表数据","zIndex":8,"label":"查询列表数据"},{"id":"f3150f1b29c94371b3e9e091109f7e5f","label":"并行结束","type":"join","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":550,"y":775},"beforeContent":"\\n","content":"","afterContent":"\\n","zindex":null,"name":"并行结束","shape":"join"},{"id":"0ea91d3228924cea8af9f66e9188bef0","label":"处理结果","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":500,"y":950},"beforeContent":"\\n","content":"\\nrenderPaged();\\n ","afterContent":"\\n","zindex":null,"name":"处理结果","shape":"task"},{"id":"9a260f42fe304fe382e32b599a206dc1","label":"是否分页查询","type":"decision","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":715,"y":520},"beforeContent":"\\n","content":"","afterContent":"\\n","zindex":null,"name":"是否分页查询","shape":"decision"},{"id":"94d8015a39ad4b5c85a033b1631382aa","label":"查询总数","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":880,"y":740},"beforeContent":"\\n","content":"<tpl>\\nselect count(1) as total from (\\n    select *\\n    from biz_demo_user\\n    where 1=1\\n    <if test=\\"id != null\\">\\n        and id = #{id}\\n    </if>\\n    <if test=\\"name != null\\">\\n        and name like ''%${name}%''\\n    </if>\\n) tmp\\n</tpl>\\n","afterContent":"var cntList = getResult(''result'')\\nsetResult(''total'', cntList[0][''total'']);","zindex":null,"name":"查询总数","shape":"task"}]', '[{"id":"ff2afa3b631c431e8e325b8eb5429fea","label":"","source":"start","sourcePort":null,"target":"8c9d8647dbee48548c5a06200cab6e09","targetPort":null,"expr":"","name":"开始->并行开始","type":"link","shape":"link"},{"id":"6552b835d59340d680d56cd4de696cb9","label":"","source":"8c9d8647dbee48548c5a06200cab6e09","sourcePort":null,"target":"ec799f06ad7048f49ecc6a44a9983e7d","targetPort":null,"expr":"","name":"并行开始->查询列表数据","type":"link","shape":"link"},{"id":"4e6c17c733024deb83875ce84f90fe28","label":"","source":"ec799f06ad7048f49ecc6a44a9983e7d","sourcePort":null,"target":"f3150f1b29c94371b3e9e091109f7e5f","targetPort":null,"expr":"","name":"查询列表数据->并行结束","type":"link","shape":"link"},{"id":"105b6f9e48bc4d278d5ad31b3eeb233b","label":"","source":"f3150f1b29c94371b3e9e091109f7e5f","sourcePort":null,"target":"0ea91d3228924cea8af9f66e9188bef0","targetPort":null,"expr":"","name":"并行结束->处理结果","type":"link","shape":"link"},{"id":"5a8ef19e711a408e85328a9b5e57c72a","label":"","source":"8c9d8647dbee48548c5a06200cab6e09","sourcePort":null,"target":"9a260f42fe304fe382e32b599a206dc1","targetPort":null,"expr":"","name":"并行开始->是否分页查询","type":"link","shape":"link"},{"id":"a88708bb0dd548aa8f998bd892c52c4e","label":"条件：(${pageQuery}=false)","source":"9a260f42fe304fe382e32b599a206dc1","sourcePort":null,"target":"f3150f1b29c94371b3e9e091109f7e5f","targetPort":null,"expr":"compare(${pageQuery}=false)","name":"是否分页查询->并行结束","type":"link","shape":"link"},{"id":"9d262c25cc194cb28f68beb447fd324f","label":"条件：(${pageQuery}=true)","source":"9a260f42fe304fe382e32b599a206dc1","sourcePort":null,"target":"94d8015a39ad4b5c85a033b1631382aa","targetPort":null,"expr":"compare(${pageQuery}=true)","name":"是否分页查询->查询总数","type":"link","shape":"link"},{"id":"c540db64e9934f5ea360b9fe1b936552","label":"","source":"94d8015a39ad4b5c85a033b1631382aa","sourcePort":null,"target":"f3150f1b29c94371b3e9e091109f7e5f","targetPort":null,"expr":"","name":"查询总数->并行结束","type":"link","shape":"link"},{"id":"1bf7a8e9a6b6447f8810d60fc2d821a2","label":"","source":"0ea91d3228924cea8af9f66e9188bef0","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"处理结果->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-06 02:25:04', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 02:25:04', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('abe8865e2f8b4564a04898561d7487cc', '取月份', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取月</h2>
<p>取月份</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>6</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取月份"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateMonth(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取月份","zIndex":15,"label":"取月份"}]', '[{"id":"86defaacbec347df933f6a2eaeee146c","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取月份","type":"link","shape":"link"},{"id":"bcd0f19c5169454e8c5aea2438d4f3a3","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取月份->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:15:29', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:17:18', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('b0fd2d2deaae419a99398922a8151d07', '取秒', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取秒</h2>
<p>取秒</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>9</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取秒"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateSecond(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取秒","zIndex":43,"label":"取秒"}]', '[{"id":"4252983fb71b4e278a3d6881b35574a5","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取秒","type":"link","shape":"link"},{"id":"435eac50d0bf434e81188f4df576b868","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取秒->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:25:44', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:26:16', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('b2fe507249654114a2c6c9009e8c5184', '取指定月份天数', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取指定月份天数</h2>
<p>取指定月份天数</p>
<p><strong>输入参数如下：</strong></p>
<pre><code>{</code><br /><code>&nbsp; &nbsp; "year":2022,</code><br /><code>&nbsp; &nbsp; "month":2</code><br /><code>}</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>28</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取指定月份天数"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var year = getResult(\\"year\\")\\nvar month = getResult(\\"month\\")\\nvar res = dateDaysOfMonth(year,month)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取指定月份天数","zIndex":65,"label":"取指定月份天数"}]', '[{"id":"aee689a5324c45799c14e9eb9b7d5aef","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取指定月份天数","type":"link","shape":"link"},{"id":"4232a87d087a481a8c5fba7a13f17ddf","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取指定月份天数->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:39:55', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:39:55', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('b7907edcad1b48188e0d6207d8e9b25b', '日期相减返回月', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回月</h2>
<p>日期相减返回月</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2021, 11, 15, 12, 15, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>12</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期相减返回月"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnMonths(date1, date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回月","zIndex":106,"label":"日期相减返回月"}]', '[{"id":"b2a435b9849946978d504b895358a801","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回月","type":"link","shape":"link"},{"id":"5c948f13cd6a4017b21e768924ccdfc6","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回月->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 11:03:06', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:08:57', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('bd7196d6e8c845e19ad932729cfaf768', '减日期减时', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期减时</h2>
<p>减日期减时</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("hour", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"id":"e8cfeaa0801647dda7b89c54eeb3e167","label":"减日期减时","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-160,"y":230},"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar hour = getResult(\\"hour\\")\\nvar res = dateSubtractHour(date, hour)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"减日期减时","shape":"task"}]', '[{"id":"49e76933cf6f403d84f39eb04cfce3ca","label":"","source":"start","sourcePort":null,"target":"e8cfeaa0801647dda7b89c54eeb3e167","targetPort":null,"expr":"","name":"开始->减日期减时","type":"link","shape":"link"},{"id":"2d7b011d352f4441849d65d07cb196b8","label":"","source":"e8cfeaa0801647dda7b89c54eeb3e167","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期减时->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:08:40', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:08:40', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('be84ae5fdad746aa946d32dcd4256a07', '删除数据', '56077d94f21f4c0f9452cf50329c172e', '<h2>删除数据</h2>
<p>快速生成一个根据ID删除数据功能的节点组</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
  "ids": ["xxxx", "yyyy", "zzzz"]
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
<span class="token punctuation">  3<br />}</span></code></pre>', '[{"id":"d11af39aeddc4c198e51ada741c3c7c2","label":"根据ID获取数据","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","position":{"x":160,"y":200},"beforeContent":"\\n","content":"<tpl>\\n  select *\\n  from biz_demo_user\\n  where id in \\n  <foreach item=\\"id\\" index=\\"index\\" collection=\\"ids\\" open=\\"(\\" separator=\\",\\" close=\\")\\">\\n    #{id}\\n  </foreach>\\n\\n</tpl>\\n","afterContent":"var cntList = getResult(''result'')\\nvar hasData = cntList?.length > 0\\nsetResult(''hasData'', hasData)\\n\\n","zindex":null,"name":"根据ID获取数据","shape":"task"},{"angle":0,"position":{"x":420,"y":605},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"处理结果"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"04e2eb5c622d45f686c3688ce55f5291","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","beforeContent":"\\n","content":"<tpl>\\n  delete from biz_demo_user where id in \\n  <foreach item=\\"id\\" index=\\"index\\" collection=\\"ids\\" open=\\"(\\" separator=\\",\\" close=\\")\\">\\n    #{id}\\n  </foreach>\\n</tpl>","afterContent":"\\n","zindex":null,"name":"处理结果","zIndex":3,"label":"处理结果"},{"id":"09a19a3521d444c296b61bea11606e8f","label":"判断是否存在数据","type":"decision","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":187.5,"y":400},"beforeContent":"\\n","content":"","afterContent":"\\n","zindex":null,"name":"判断是否存在数据","shape":"decision"}]', '[{"id":"2dd2e654c7514a008d48548bf1f8852a","label":"","source":"d11af39aeddc4c198e51ada741c3c7c2","sourcePort":null,"target":"09a19a3521d444c296b61bea11606e8f","targetPort":null,"expr":"","name":"根据ID获取数据->判断是否存在数据","type":"link","shape":"link"},{"id":"f2d0bdccf73b40a384c651ac1da61418","label":"条件：(${hasData}=true)","source":"09a19a3521d444c296b61bea11606e8f","sourcePort":null,"target":"04e2eb5c622d45f686c3688ce55f5291","targetPort":null,"expr":"compare(${hasData}=true)","name":"判断是否存在数据->处理结果","type":"link","shape":"link"},{"id":"7701af9dd6534e1385138f7f52b25a01","label":"","source":"start","sourcePort":null,"target":"d11af39aeddc4c198e51ada741c3c7c2","targetPort":null,"expr":"","name":"开始->根据ID获取数据","type":"link","shape":"link"},{"id":"98b5aee49a5f42daba3df603f783c0c5","label":"","source":"04e2eb5c622d45f686c3688ce55f5291","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"处理结果->结束","type":"link","shape":"link"},{"id":"78de87dcd3f44d4a9dccf28fed411e5e","label":"条件：(${hasData}=false)","source":"09a19a3521d444c296b61bea11606e8f","sourcePort":null,"target":"end","targetPort":null,"expr":"compare(${hasData}=false)","name":"判断是否存在数据->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-06 11:04:23', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 11:04:23', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('c237861ea16642d8a64ae9f7863161c3', '加日期加秒', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期加秒</h2>
<p>加日期加秒</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("second", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"id":"0ceb6356000f4146a643ddd7645c6ba7","label":"加日期加秒","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-80,"y":250},"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar second = getResult(\\"second\\")\\nvar res = dateAddSecond(date, second)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"加日期加秒","shape":"task"}]', '[{"id":"b061259b40de4cfbb3e1457325b28759","label":"","source":"start","sourcePort":null,"target":"0ceb6356000f4146a643ddd7645c6ba7","targetPort":null,"expr":"","name":"开始->加日期加秒","type":"link","shape":"link"},{"id":"53639231c02b423db9d36b2d39625ce5","label":"","source":"0ceb6356000f4146a643ddd7645c6ba7","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期加秒->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:44:04', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:44:04', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('c4f88ec99f234f849128acea81e4dba9', '向List当中添加对象', '41804268bde5404683719c0e60b87c0a', '<h2>向List当中添加对象</h2>
<p>向List当中添加对象</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3],<br /></span>  "item":4
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>[1,2,3,4]</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"向List当中添加对象"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar item = getResult(\\"item\\")\\nvar res = listPush(list, item)\\n\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"向List当中添加对象","zIndex":43,"label":"向List当中添加对象"}]', '[{"id":"fa6d97da3fc24b6c83d618205054254c","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->向List当中添加对象","type":"link","shape":"link"},{"id":"f293f77f4bb94ce7a25c7d49c3317853","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"向List当中添加对象->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 14:06:20', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 14:19:53', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('c80b0accc6a6499e8f3cdb08455aea25', '向下取整', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>向下取整</h2>
<p>向下取整</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":10.9</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>10</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"向下取整","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-20,"y":130},"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathFloor(num)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"向下取整","shape":"task"}]', '[{"id":"b14efbe3d877470386c98d10c1d85436","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->向下取整","type":"link","shape":"link"},{"id":"1572ee0d9d66464786f218777e35d1dc","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"向下取整->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:10:46', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:10:46', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('c8ae526e7a0f4831a542530b7e8d0b5e', '求10为底的对数', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求10为底的对数</h2>
<p>求10为底的对数</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":100</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>2</pre>', '[{"angle":0,"position":{"x":20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"求10为底的对数"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathLog10(num)\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"求10为底的对数","zIndex":237,"label":"求10为底的对数"}]', '[{"id":"afa6b06c2df241af86237fdb4275e741","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求10为底的对数","type":"link","shape":"link"},{"id":"2263bb5ac3064313983a007178a59c09","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求10为底的对数->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:39:03', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:39:03', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('ce0abc83737b4d3bba03d1bb9ee46e0f', '循环', '5c6b3c06df2d490e94dd157833afbbe0', '<h2>传入list做循环</h2>
<p>传入list，通过修改循环体的内容，做循环处理操作</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list": [{}, {}]</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>&nbsp;</pre>', '[{"id":"c3207d1aee6a41099e6589fe39ed91ed","label":"准备进入下一个循环项","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":260,"y":490},"beforeContent":"\\n","content":"var loopList = getResult(''loopList'')\\nvar loopIndex = getResult(''loopIndex'')\\nvar loopLength = getResult(''loopLength'')\\nvar loopIsFirst = getResult(''loopIsFirst'')\\n\\n// 1. 如果是第一次进入循环，下标为0，否则下标加1\\nloopIndex = loopIsFirst ? 0 : (loopIndex ?? 0) + 1\\nsetResult(''loopIndex'', loopIndex)\\n\\n// 2. 设置loopHasNext变量，用于判断是否有下一个节点\\nvar loopHasNext = loopLength === loopIndex\\nsetResult(''loopHasNext'', loopHasNext)\\n\\n// 3. 设置loopItem，保存下一个要处理的循环项数据\\nsetResult(''loopItem'', loopHasNext ? loopList[loopIndex] : {})","afterContent":"\\n","zindex":null,"name":"准备进入下一个循环项","shape":"task"},{"id":"cd071473274948d38b6e68182cca3e9b","label":"初始化循环数据","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":260,"y":240},"beforeContent":"\\n","content":"// 1. 获取要被循环数组\\"list\\"(list可根据自己的情况修改)，放到loopList中\\nvar list = getResult(''list'')\\nsetResult(''loopList'', list)\\n\\n// 2. 获取list数组长度\\nsetResult(''loopLength'', list?.length ?? 0)\\n\\n// 3. 表示开始循环\\nsetResult(''loopIsFirst'', true)","afterContent":"\\n","zindex":null,"name":"初始化循环数据","shape":"task"},{"id":"0b48fb86ba3c4a01adc79693b68b4962","label":"判断是否有下一个循环项","type":"decision","flowId":null,"subFlowName":null,"executeType":"","sourceName":"","position":{"x":287.5,"y":740},"beforeContent":"","content":"","afterContent":"","zindex":null,"name":"判断是否有下一个循环项","shape":"decision"},{"angle":0,"position":{"x":650,"y":490},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"执行循环体"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"f9724bc882cb4d25a3f7b968871aedeb","type":"task","flowId":null,"subFlowName":null,"executeType":"sql","sourceName":"MySql","beforeContent":"setResult(''loopUuid'', getUuid())","content":"-- 通过#{loopItem.xxx}获取循环项的值，动态注入SQL\\ninsert into biz_demo_user (id, name, age)\\nvalues (#{loopUuid}, #{loopItem.name}, #{loopItem.age})","afterContent":"\\n","zindex":null,"name":"执行循环体","zIndex":3,"label":"执行循环体"}]', '[{"id":"985f829e6eb94a51bed30c8411baf704","label":"","source":"start","sourcePort":null,"target":"cd071473274948d38b6e68182cca3e9b","targetPort":null,"expr":"","name":"开始->初始化循环数据","type":"link","shape":"link"},{"id":"0a1bc22152da4740b06dff8a477ffb55","label":"","source":"cd071473274948d38b6e68182cca3e9b","sourcePort":null,"target":"c3207d1aee6a41099e6589fe39ed91ed","targetPort":null,"expr":"","name":"初始化循环数据->准备进入下一个循环项","type":"link","shape":"link"},{"id":"e849eb3e6f5140a791d38260fe8baadd","label":"条件：(${loopHasNext}==false)","source":"0b48fb86ba3c4a01adc79693b68b4962","sourcePort":null,"target":"end","targetPort":null,"expr":"compare(${loopHasNext}==false)","name":"判断是否有下一个循环项->结束","type":"link","shape":"link"},{"id":"36ec6740b154439d828bc81ff4deb345","label":"条件：(${loopHasNext}==true)","source":"0b48fb86ba3c4a01adc79693b68b4962","sourcePort":null,"target":"f9724bc882cb4d25a3f7b968871aedeb","targetPort":null,"expr":"compare(${loopHasNext}==true)","name":"准备进入下一个循环项->执行循环体","type":"link","shape":"link"},{"id":"530779c32a244d3d9cd0a968a1340af9","label":"","source":"c3207d1aee6a41099e6589fe39ed91ed","sourcePort":null,"target":"0b48fb86ba3c4a01adc79693b68b4962","targetPort":null,"expr":"","name":"执行循环体->判断是否有下一个循环项","type":"link","shape":"link"},{"id":"75af0a161e284f74ad2713a4a44a5588","label":"","source":"f9724bc882cb4d25a3f7b968871aedeb","sourcePort":null,"target":"c3207d1aee6a41099e6589fe39ed91ed","targetPort":null,"expr":"","name":"执行循环体->准备进入下一个循环项","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:04:16', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-14 17:35:20', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('ce2ce2a4c8434037a824086f95d7ecae', '求List中所有的数字的最大值', '41804268bde5404683719c0e60b87c0a', '<h2>求List中所有的数字的最大值</h2>
<p>求List中所有的数字的最大值</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span>
  "list":[1,2,3]
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>3<code class="language-json"> </code></pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"求List中所有的数字的最大值","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-10,"y":130},"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = listMax(list)\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"求List中所有的数字的最大值","shape":"task"}]', '[{"id":"9839c5ed6a7846308f868731a9eb218e","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求List中所有的数字的最大值","type":"link","shape":"link"},{"id":"d78072f560e74f548d820b29648151cb","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求List中所有的数字的最大值->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:32:05', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:32:05', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('ce3873614966440f8fcd9238856bc53c', '减日期减分', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期减分</h2>
<p>减日期减分</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("minute", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"id":"c05f820c3cac4327a216d71963f596dd","label":"减日期减分","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-70,"y":250},"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar minute = getResult(\\"minute\\")\\nvar res = dateSubtractMinute(date, minute)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"减日期减分","shape":"task"}]', '[{"id":"abb986a7816848218aa1d96297e00b3a","label":"","source":"start","sourcePort":null,"target":"c05f820c3cac4327a216d71963f596dd","targetPort":null,"expr":"","name":"开始->减日期减分","type":"link","shape":"link"},{"id":"5b6267ef58d84726a74d7bba518f62c7","label":"","source":"c05f820c3cac4327a216d71963f596dd","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期减分->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:13:46', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:46:20', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('d342a9fd46ba488caba6cd9bbf858e3b', '日期相减返回天', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回天</h2>
<p>日期相减返回天</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 11, 12, 12, 15, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>3</pre>', '[{"id":"88ae5d2049a44c128fec24b666d47d78","label":"日期相减返回天","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-200,"y":220},"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnDays(date1, date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回天","shape":"task"}]', '[{"id":"775b030e1c12486e94546b5fb00d1827","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回天","type":"link","shape":"link"},{"id":"cbc8c6ce3141457d9dabfdd75e9b7cb9","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回天->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:59:58', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:09:08', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('d618fdb8a9eb473aaec17b3fe816bd53', '求绝对值', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>求绝对值</h2>
<p>求绝对值</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "num":-2</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>2</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"求绝对值","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":20,"y":130},"beforeContent":"\\n","content":"var num = getResult(\\"num\\")\\nvar res = mathAbs(num)\\nsetResult(\\"result\\",res)\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"求绝对值","shape":"task"}]', '[{"id":"2dd6b4e933094c96929a40b53442f638","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->求绝对值","type":"link","shape":"link"},{"id":"dee224e97fa34ba2a038c1386aa0052a","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"求绝对值->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:53:32', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:53:32', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('d817b136fac045aeb3f21594abb429e1', '减日期减天', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期减天</h2>
<p>减日期减天</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("dateNum", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-110,"y":280},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"减日期减天"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"c6ee93c51f194f02840b2e9200c26325","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar dateNum = getResult(\\"dateNum\\")\\nvar res = dateSubtractDate(date, dateNum)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"减日期减天","zIndex":9,"label":"减日期减天"}]', '[{"id":"0f8e029bf28b47498abb9998a6ddeb00","label":"","source":"start","sourcePort":null,"target":"c6ee93c51f194f02840b2e9200c26325","targetPort":null,"expr":"","name":"开始->减日期减天","type":"link","shape":"link"},{"id":"4d334c6c0f6d4df385295ccf01afa16c","label":"","source":"c6ee93c51f194f02840b2e9200c26325","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期减天->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:00:58', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:10:51', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('dbd4b310bc324db6abbfb248a766ec5d', '转小写', '8397e7f441b24f32938e7c9a5538af11', '<h2>转小写</h2>
<p>转小写</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"ABC"</span>
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"abc"</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"转小写","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-20,"y":130},"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar res = stringToLower(str)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"转小写","shape":"task"}]', '[{"id":"1362e27c0296455f940a741e128a304d","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->转小写","type":"link","shape":"link"},{"id":"592c29adbcec4fb386659adeba47b884","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"转小写->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:02:08', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:02:08', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('dee217c687224b63b8ba7f823fa45f87', '最小值', 'd072b8ffa8ba4ebea09fc8a63095ba59', '<h2>最小值</h2>
<p>最小值</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3]</span>
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>1</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"最小值","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-10,"y":130},"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar res = mathMin(list)\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"最小值","shape":"task"}]', '[{"id":"ba2ddb8799eb461486670847235bbe27","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->最小值","type":"link","shape":"link"},{"id":"73a66cb973c04434a744ae1e92e97a20","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"最小值->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 17:28:54', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 17:28:54', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('e1d5aef017e749a8bc8f7a1e519926b5', '取小时', 'e8b047bed0414f20bfadba637df79d2d', '<h2>取小时</h2>
<p>取小时</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>10</pre>', '[{"angle":0,"position":{"x":-200,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取小时"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar res = dateHour(date)\\nsetResult(\\"result\\",res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取小时","zIndex":29,"label":"取小时"}]', '[{"id":"43142759d2ce431cae71f1b6a0215434","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->取小时","type":"link","shape":"link"},{"id":"fc05fd4f846f44ef8ea5cd8533c70c68","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取小时->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:21:48', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 10:21:48', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('e4d11405aaf64b8c81e5ee9995778b24', '字符首次出现位置', '8397e7f441b24f32938e7c9a5538af11', '<h2>字符首次出现位置</h2>
<p>字符首次出现位置</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abcefa",<br /></span>  "item":"a"
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>0</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"字符首次出现位置"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar item = getResult(\\"item\\")\\nvar res = stringIndexOf(str,item)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"字符首次出现位置","zIndex":58,"label":"字符首次出现位置"}]', '[{"id":"cb046c75fde64d078a48c0794535a761","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->字符首次出现位置","type":"link","shape":"link"},{"id":"b0e5dd1d20d046ca9d6551c141f29381","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"字符首次出现位置->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:25:47', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:25:47', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('e73d5f2dbd4848f1975496e4cbda59d4', '日期相减返回毫秒', 'e8b047bed0414f20bfadba637df79d2d', '<h2>日期相减返回毫秒</h2>
<p>日期相减返回毫秒</p>
<p><strong>输入参数如下：</strong></p>
<pre>const date1 = new Date(2022, 11, 15, 12, 15, 0);<br />const date2 = new Date(2022, 10, 15, 12, 15, 0);<br />setResult("date1", JSON.stringify(date1))<br />setResult("date2", JSON.stringify(date2))</pre>
<p><strong>返回数据结构：</strong></p>
<pre>2592000000</pre>', '[{"angle":0,"position":{"x":-200,"y":220},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"日期相减返回毫秒"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"88ae5d2049a44c128fec24b666d47d78","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"//字符串转日期\\nvar date1 = getResult(\\"date1\\")\\ndate1 = new Date(date1)\\nvar date2 = getResult(\\"date2\\")\\ndate2 = new Date(date2)\\n//调用函数\\nvar res = dateSubtractReturnMilliSeconds(date1, date2)\\n\\nsetResult(\\"result\\",JSON.stringify(res))\\n\\n\\n","afterContent":"\\n","zindex":null,"name":"日期相减返回毫秒","zIndex":90,"label":"日期相减返回毫秒"}]', '[{"id":"2d200c96c7cd4037b2ed9f2272fc9f74","label":"","source":"start","sourcePort":null,"target":"88ae5d2049a44c128fec24b666d47d78","targetPort":null,"expr":"","name":"开始->日期相减返回毫秒","type":"link","shape":"link"},{"id":"d72ed8e534b84e4381b2c0cc010e779f","label":"","source":"88ae5d2049a44c128fec24b666d47d78","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"日期相减返回毫秒->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-15 10:54:40', '8c263f81d2b14784906787d67ce58b47', '2022-06-15 11:09:21', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('e9ae2a15a2e74f62bef6afd0801e26dd', '加日期加月', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期加月</h2>
<p>加日期加月</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("month",3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-03-02T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-110,"y":280},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"加日期加月"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"91be0f3acd524e3e8168281a5436ea0a","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"\\nvar date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar month = getResult(\\"month\\")\\nvar res = dateAddMonth(date, month)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"加日期加月","zIndex":17,"label":"加日期加月"}]', '[{"id":"429290e596bc4d55afe7a29502fb3180","label":"","source":"start","sourcePort":null,"target":"91be0f3acd524e3e8168281a5436ea0a","targetPort":null,"expr":"","name":"开始->加日期加月","type":"link","shape":"link"},{"id":"49eaaf808ebb4c6c8320ca1908e404f2","label":"","source":"91be0f3acd524e3e8168281a5436ea0a","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期加月->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:26:55', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:26:55', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('eb657ee1615940c18fc966fac607d9f5', '加日期', 'e8b047bed0414f20bfadba637df79d2d', '<h2>加日期</h2>
<p>加日期</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("num", 2)<br />setResult("type", "month")</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-03-02T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-190,"y":240},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"加日期"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"390cbd09e1e94bd480a5097d947a477c","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar num = getResult(\\"num\\")\\nvar type = getResult(\\"type\\")\\n\\nvar res = dateAdd(date,num,type)\\n\\nsetResult(\\"result\\", JSON.stringify(res))\\n\\n","afterContent":"\\n","zindex":null,"name":"加日期","zIndex":27,"label":"加日期"}]', '[{"id":"7060f41a3560443da77d78621941eeaa","label":"","source":"start","sourcePort":null,"target":"390cbd09e1e94bd480a5097d947a477c","targetPort":null,"expr":"","name":"开始->加日期","type":"link","shape":"link"},{"id":"ab0a23821bd943a4ab10ee2112151e6a","label":"","source":"390cbd09e1e94bd480a5097d947a477c","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"加日期->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:59:02', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:59:02', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('ef87144940ef48478fda6d685d1f04f5', '指定结束的字符串截取', '8397e7f441b24f32938e7c9a5538af11', '<h2>指定结束的字符串截取</h2>
<p>指定结束的字符串截取</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc",<br /></span>  "end":1
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"a"</pre>', '[{"id":"cd917a27887442ef8095142da9602c9e","label":"指定结束的字符串截取","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"position":{"x":-20,"y":130},"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar end = getResult(\\"end\\")\\nvar res = stringSubEnd(str, end)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"指定结束的字符串截取","shape":"task"}]', '[{"id":"3ce8f37d31b147b58f761f700583e25c","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->指定结束的字符串截取","type":"link","shape":"link"},{"id":"34dc35fa1201463b8a7e7be079b29328","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"指定结束的字符串截取->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:36:28', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:36:28', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('f12f879afb844015946d5604218e468e', '减日期减秒', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期减秒</h2>
<p>减日期减秒</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("second", 3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-06-11T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-110,"y":280},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"减日期减秒"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"c6ee93c51f194f02840b2e9200c26325","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar second = getResult(\\"second\\")\\nvar res = dateSubtractSecond(date, second)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"减日期减秒","zIndex":24,"label":"减日期减秒"}]', '[{"id":"baa7d207103844128274b285efdb7636","label":"","source":"start","sourcePort":null,"target":"c6ee93c51f194f02840b2e9200c26325","targetPort":null,"expr":"","name":"开始->减日期减秒","type":"link","shape":"link"},{"id":"631ed7ad536b4b13ac902157f5c51594","label":"","source":"c6ee93c51f194f02840b2e9200c26325","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期减秒->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 17:19:07', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 17:19:07', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('f16783468815412a808b5a0755379533', '减日期减月', 'e8b047bed0414f20bfadba637df79d2d', '<h2>减日期减月</h2>
<p>减日期减月</p>
<p><strong>输入参数如下：</strong></p>
<pre>setResult("date", JSON.stringify(new Date()))<br />setResult("month",3)</pre>
<p><strong>返回数据结构：</strong></p>
<pre>"2022-03-02T10:00:000Z"</pre>', '[{"angle":0,"position":{"x":-160,"y":230},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"减日期减月"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"e8cfeaa0801647dda7b89c54eeb3e167","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"\\nvar date = getResult(\\"date\\")\\ndate = new Date(date)\\nvar month = getResult(\\"month\\")\\nvar res = dateSubtractMonth(date, month)\\nsetResult(\\"result\\", JSON.stringify(res))\\n","afterContent":"\\n","zindex":null,"name":"减日期减月","zIndex":37,"label":"减日期减月"}]', '[{"id":"24d0ab64642541058901b02538b4f87f","label":"","source":"e8cfeaa0801647dda7b89c54eeb3e167","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"减日期减月->结束","type":"link","shape":"link"},{"id":"3ffbde2f6d43429e846170d075157069","label":"","source":"start","sourcePort":null,"target":"e8cfeaa0801647dda7b89c54eeb3e167","targetPort":null,"expr":"","name":"开始->减日期减月","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-14 16:55:58', '8c263f81d2b14784906787d67ce58b47', '2022-06-14 16:55:58', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('f50cece6cb034bd2b4184ca6b60aa3c6', '取List中指定位置对象', '41804268bde5404683719c0e60b87c0a', '<h2>取List中指定位置对象</h2>
<p>取List中指定位置对象</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "list":[1,2,3],<br /></span>  "index":1
<span class="token punctuation">}</span>
</code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>2</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"取List中指定位置对象"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var list = getResult(\\"list\\")\\nvar index = getResult(\\"index\\")\\nvar res = listGet(list,index)\\nsetResult(\\"result\\", res)\\n\\n","afterContent":"\\n","zindex":null,"name":"取List中指定位置对象","zIndex":7,"label":"取List中指定位置对象"}]', '[{"id":"8b2ffc1e8a20411087ac15bfa75282e7","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->取List中指定位置对象","type":"link","shape":"link"},{"id":"d80c020babb5433986093a10930ca2f1","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"取List中指定位置对象->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 09:52:39', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 09:53:24', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('f72afedafeb3410db2c6305178957788', '替换字符串', '8397e7f441b24f32938e7c9a5538af11', '<h2>替换字符串</h2>
<p>替换字符串</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abcb",<br /></span>  "subStr":"b",<br />  "newSubStr":"d"
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"adcb"</pre>', '[{"angle":0,"position":{"x":-20,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"替换字符串"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar subStr = getResult(\\"subStr\\")\\nvar newSubStr = getResult(\\"newSubStr\\")\\nvar res = stringReplace(str,subStr,newSubStr)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"替换字符串","zIndex":104,"label":"替换字符串"}]', '[{"id":"2304f7f8af984f8b84e0092a649fea21","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->替换字符串","type":"link","shape":"link"},{"id":"ac036e46c6e14d9685021582e615eed4","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"替换字符串->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:43:51', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:44:36', '8c263f81d2b14784906787d67ce58b47');
INSERT INTO sys_logic_template
(id, name, module_id, description, nodes, links, app_id, when_created, who_created, when_modified, who_modified)
VALUES('fcb3971711e24861b079c54e23abb701', '指定开始的字符串截取', '8397e7f441b24f32938e7c9a5538af11', '<h2>指定开始的字符串截取</h2>
<p>指定开始的字符串截取</p>
<p><strong>参数如下：</strong></p>
<pre><code class="language-json"><span class="token punctuation">{</span><br /><span class="token property">  "str":"abc",<br /></span>  "start":1
<span class="token punctuation">}</span></code></pre>
<p><strong>返回数据结构：</strong></p>
<pre>"bc"</pre>', '[{"angle":0,"position":{"x":-30,"y":130},"size":{"width":200,"height":150},"attrs":{"text":{"fontSize":14,"fill":"#000000","refX":0.5,"refY":0.5,"textAnchor":"middle","textVerticalAnchor":"middle","fontFamily":"Arial, helvetica, sans-serif","text":"指定开始的字符串截取"},"body":{"fill":"none","stroke":"none","refWidth":"100%","refHeight":"100%"},"fo":{"refWidth":"100%","refHeight":"100%"}},"view":"html-view","shape":"task","ports":{"items":[]},"id":"cd917a27887442ef8095142da9602c9e","type":"task","flowId":null,"subFlowName":null,"executeType":"js","sourceName":null,"beforeContent":"\\n","content":"var str = getResult(\\"str\\")\\nvar start = getResult(\\"start\\")\\nvar res = stringSubStart(str, start)\\nsetResult(\\"result\\", res)\\n","afterContent":"\\n","zindex":null,"name":"指定开始的字符串截取","zIndex":76,"label":"指定开始的字符串截取"}]', '[{"id":"9058d5ae151e4d4089ffb57c24eb4297","label":"","source":"start","sourcePort":null,"target":"cd917a27887442ef8095142da9602c9e","targetPort":null,"expr":"","name":"开始->指定开始的字符串截取","type":"link","shape":"link"},{"id":"75f420f96c594d39856676b5d8c3e81d","label":"","source":"cd917a27887442ef8095142da9602c9e","sourcePort":null,"target":"end","targetPort":null,"expr":"","name":"指定开始的字符串截取->结束","type":"link","shape":"link"}]', '064b3b44b85a45fe87fcce88d72b2519', '2022-06-13 16:33:35', '8c263f81d2b14784906787d67ce58b47', '2022-06-13 16:33:35', '8c263f81d2b14784906787d67ce58b47');
