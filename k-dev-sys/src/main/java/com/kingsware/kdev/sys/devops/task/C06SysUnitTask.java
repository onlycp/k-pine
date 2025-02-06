package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysRoleMenu;
import com.kingsware.kdev.sys.model.SysUnit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C06SysUnitTask extends TableCopyTask {
    @Override
    public String name() {
        return "单位表";
    }


    @Override
    public Integer order() {
        return 6;
    }

    @Override
    public String note() {
        return "单位表-sys_unit";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysUnit> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysUnit.class, "select * from sys_unit");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_unit");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysUnit.class);
        current = list.size();

    }
}
