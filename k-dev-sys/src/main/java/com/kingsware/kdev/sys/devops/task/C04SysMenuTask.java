package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysMenu;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C04SysMenuTask extends TableCopyTask {
    @Override
    public String name() {
        return "菜单表";
    }


    @Override
    public Integer order() {
        return 4;
    }

    @Override
    public String note() {
        return "菜单表，对应于sys_menu表";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysMenu> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysMenu.class, "select * from sys_menu");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_menu");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysMenu.class);
        current = list.size();

    }
}
