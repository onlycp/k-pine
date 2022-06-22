ALTER TABLE `sys_dict_item`
drop foreign key fk_sys_dict_id,
DROP INDEX `idx_sys_dict_id_value_uindex`,
ADD INDEX `idx_sys_dict_id_value_uindex`(`id`, `value`) USING BTREE;