-- sys_task.distributed 关键字要加双引号

-- sqlserver不支持子查询里有order by
-- SELECT COUNT
-- 	( 1 )
-- FROM
-- 	(
-- 	SELECT
-- 		u.*,
-- 		un.name AS sys_unit_name,
-- 		un.path AS sys_unit_path
-- 	FROM
-- 		sys_user u
-- 		LEFT JOIN sys_unit un ON un.id= u.sys_unit_id
-- 	WHERE
-- 		1 = 1
-- 	ORDER BY
-- 	u.when_created DESC
-- 	) tmp_cnt;
--
-- sys_task.distributed关键字字段要加双引号


-- ----------------------------
-- Table structure for dev_sql_run
-- ----------------------------
CREATE TABLE  dev_sql_run (  id varchar(36)  NOT NULL ,  version int NOT NULL ,  md5 varchar(100)  DEFAULT NULL ,  when_created datetime ,  execution_time int DEFAULT NULL ,  success tinyint NOT NULL ,  PRIMARY KEY (id)) ;
