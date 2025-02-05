package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysConfig;
import com.kingsware.kdev.sys.model.SysDict;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C09SysDictTask extends TableCopyTask {
    @Override
    public String name() {
        return "字典表";
    }


    @Override
    public Integer order() {
        return 9;
    }

    @Override
    public String note() {
        return "字典表-sys_dict";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysDict> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysDict.class, "select * from sys_dict");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_dict");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysDict.class);
        current = list.size();

    }
}
