package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C01SysUserTask extends TableCopyTask {
    @Override
    public String name() {
        return "用户表";
    }


    @Override
    public Integer order() {
        return 1;
    }

    @Override
    public String note() {
        return "用户数据，对应于sys_user表";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysUser> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysUser.class, "select * from sys_user");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_user");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysUser.class);
        current = list.size();

    }
}
