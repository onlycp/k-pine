package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysUnit;
import com.kingsware.kdev.sys.model.SysUserUnit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C07SysUserUnitTask extends TableCopyTask {
    @Override
    public String name() {
        return "用户单位表";
    }


    @Override
    public Integer order() {
        return 7;
    }

    @Override
    public String note() {
        return "用户单位表-sys_user_unit";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysUserUnit> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysUserUnit.class, "select * from sys_user_unit");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_user_unit");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysUserUnit.class);
        current = list.size();

    }
}
