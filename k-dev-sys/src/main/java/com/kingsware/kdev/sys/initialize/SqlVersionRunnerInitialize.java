package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2022/4/19 08:47
 */
@Component
@Slf4j
public class SqlVersionRunnerInitialize implements SystemInitialize {

    private final static String DEV_SQL_RUN_TABLE = "CREATE TABLE IF NOT EXISTS `dev_sql_run` (  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',  `version` int(11) NOT NULL COMMENT '关联版本号',  `md5` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'md5',  `when_created` timestamp NULL DEFAULT NULL COMMENT '执行时间',  `execution_time` int(11) DEFAULT NULL COMMENT '执行时长（毫秒）',  `success` tinyint(4) NOT NULL COMMENT '是否成功',  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;";

    @Override
    public void execute() {
        // DB.executeUpdateSql(DEV_SQL_RUN_TABLE);
    }

    @Override
    public int sort() {
        return 1;
    }
}
