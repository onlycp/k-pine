package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysMenu;
import com.kingsware.kdev.sys.model.SysRoleMenu;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C05SysRoleMenuTask extends TableCopyTask {
    @Override
    public String name() {
        return "角色菜单表";
    }


    @Override
    public Integer order() {
        return 5;
    }

    @Override
    public String note() {
        return "角色菜单表，对应于sys_role_menu表";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysRoleMenu> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysRoleMenu.class, "select * from sys_role_menu");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_role_menu");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysRoleMenu.class);
        current = list.size();

    }
}
