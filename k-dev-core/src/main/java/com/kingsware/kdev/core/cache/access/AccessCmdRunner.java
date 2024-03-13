package com.kingsware.kdev.core.cache.access;

import com.kingsware.kdev.core.orm.DB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限启动加载类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/11 5:38 下午
 */
@Component
public class AccessCmdRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 查询超级管理员id
        String id = DB.findSingleAttribute(String.class, "select id from sys_role where code = ?", "admin");
        AccessManager.getInstance().setSupperAdminRoleId(id);
        // 查询表
        List<DataResourceInfo> tables = DB.findList(DataResourceInfo.class, "select table_name, extra_sql, value_field from sys_data_resource where status = ?", 1);
        Map<String, DataResourceInfo>  map = new HashMap<>();
        for (DataResourceInfo resource: tables) {
            String[] arr = resource.getTableName().trim().split("\\.");
            if (arr.length == 1) {
                resource.setSourceName("db");
            }
            else {
                resource.setSourceName(arr[0]);
                resource.setTableName(arr[1]);
            }
            map.put(resource.getTableName(), resource);
        }
        AccessManager.getInstance().setAccessTables(map);
    }
}
