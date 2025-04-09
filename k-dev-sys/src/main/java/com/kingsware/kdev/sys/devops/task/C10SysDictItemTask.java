package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.model.SysDictItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C10SysDictItemTask extends TableCopyTask {
    @Override
    public String name() {
        return "字典明细表";
    }


    @Override
    public Integer order() {
        return 10;
    }

    @Override
    public String note() {
        return "字典明细表-sys_dict_item";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysDictItem> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysDictItem.class, "select * from sys_dict_item");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_dict_item");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysDictItem.class);
        current = list.size();

    }
}
