package com.kingsware.kdev.core.cache.access;

import com.kingsware.kdev.core.orm.DB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

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
        List<String> tables = DB.findSingleAttributeList(String.class, "select table_name from sys_data_resource where status = ?", 1);
        AccessManager.getInstance().setAccessTables(new HashSet<>(tables));
    }
}
