package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 用于添加FAAS的字段
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/3 14:14
 */
@Component
@Slf4j
public class FaasDDlInitialize implements SystemInitialize {
    @Override
    public void execute() throws FileNotFoundException {
        // 处理函数
        this.ddlFunction();
        // 处理数据源
        this.ddlDatasource();
    }

    /**
     * 数据库函数元数据表的DDL操作
     * 本方法用于检查并执行数据库函数元数据表的结构修改，确保表结构符合要求
     */
    private void ddlFunction() {
        // 查询数据库中的第一个函数信息
        String sql = "select * from PUBLIC.FUNCTIONS limit 1 offset 0";
        Map<String, Object> map = DB.byName("kingDB").findOne(Map.class, sql);
        if (map == null) {
            // 如果没有找到函数信息，则不进行后续操作
            return;
        }
        if (!map.containsKey("APP_ID")) {
            // 如果当前函数信息中不包含APP_ID字段，则向表中添加此字段
            DB.byName("kingDB").executeUpdateSql("alter table PUBLIC.FUNCTIONS add column APP_ID varchar(36)");
            log.info("FAAS-DDL：functions" + "添加APP_ID字段成功");
        }
    }

    /**
     * 数据源元数据表的DDL操作
     * 本方法用于检查并执行数据库数据源元数据表的结构修改，确保表结构符合要求
     */
    private void ddlDatasource() {
        // 查询数据库中的第一个数据源信息
        String sql = "select * from PUBLIC.DATA_SOURCE limit 1 offset 0";
        Map<String, Object> map = DB.byName("kingDB").findOne(Map.class, sql);
        if (map == null) {
            // 如果没有找到数据源信息，则不进行后续操作
            return;
        }
        if (!map.containsKey("APP_ID")) {
            // 如果当前数据源信息中不包含APP_ID字段，则向表中添加此字段
            DB.byName("kingDB").executeUpdateSql("alter table PUBLIC.DATA_SOURCE add column APP_ID varchar(36)");
            log.info("FAAS-DDL：data_source" + "添加APP_ID字段成功");
        }
    }

    @Override
    public int sort() {
        return 4;
    }
}
