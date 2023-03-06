-- 删除外键关联
ALTER TABLE `sys_dict_item` DROP FOREIGN KEY `fk_sys_dict_id`;
ALTER TABLE `sys_user` DROP FOREIGN KEY `fk_sys_unit_id`;
ALTER TABLE `sys_user_role` DROP FOREIGN KEY `fk_sys_role_key`;
ALTER TABLE `sys_user_role` DROP FOREIGN KEY `fk_sys_user_role_user`;
ALTER TABLE `sys_data_access_resource` DROP FOREIGN KEY `fk_sys_data_access_group_access`;
ALTER TABLE `sys_data_access_user` DROP FOREIGN KEY `fk_sys_data_access_group_group_id`;
ALTER TABLE `sys_data_access_user` DROP FOREIGN KEY `fk_sys_data_access_group_user`;
ALTER TABLE `sys_role_menu` DROP FOREIGN KEY `fk_sys_role`;

