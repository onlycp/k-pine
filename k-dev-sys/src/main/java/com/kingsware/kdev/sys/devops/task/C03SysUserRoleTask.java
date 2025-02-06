package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysUserRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C03SysUserRoleTask extends TableCopyTask {
    @Override
    public String name() {
        return "用户角色表";
    }


    @Override
    public Integer order() {
        return 3;
    }

    @Override
    public String note() {
        return "用户角色表，对应于sys_role表";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysUserRole> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysUserRole.class, "select * from sys_user_role");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_user_role");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysUserRole.class);
        current = list.size();

    }
}
