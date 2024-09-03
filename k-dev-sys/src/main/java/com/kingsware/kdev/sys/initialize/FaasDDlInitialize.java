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
        // 查找一个函数
        String sql = "select * from PUBLIC.FUNCTIONS limit 1 offset 0";
        Map<String, Object> map = DB.byName("kingDB").findOne(Map.class, sql);
        if (map == null) {
            return;
        }
        if (!map.containsKey("APP_ID")) {
            DB.byName("kingDB").executeUpdateSql("alter table PUBLIC.FUNCTIONS add column APP_ID varchar(36)");
            log.info("FAAS-DDL：functions" + "添加APP_ID字段成功");
        }
    }

    @Override
    public int sort() {
        return 4;
    }
}
