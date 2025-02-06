package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C02SysRoleTask extends TableCopyTask {
    @Override
    public String name() {
        return "角色表";
    }


    @Override
    public Integer order() {
        return 2;
    }

    @Override
    public String note() {
        return "角色表，对应于sys_role表";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysRole> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysRole.class, "select * from sys_role");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_role");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysRole.class);
        current = list.size();

    }
}
