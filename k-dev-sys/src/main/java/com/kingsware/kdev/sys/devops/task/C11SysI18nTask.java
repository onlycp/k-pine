package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.CollectUtils;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysDictItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C11SysI18nTask extends TableCopyTask {
    @Override
    public String name() {
        return "国际化表";
    }


    @Override
    public Integer order() {
        return 11;
    }

    @Override
    public String note() {
        return "国际化表-sys_i18n";
    }

    @Override
    public void start(DataCopyParam context) {

        List<SysI18n> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysI18n.class, "select * from sys_i18n");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_i18n");
        List<List<SysI18n>> lists = CollectUtils.splitList(list, 100);
        for (List<SysI18n> sysI18ns : lists) {
            DB.batchSaveOrUpdate(sysI18ns, SysI18n.class);
            this.current += sysI18ns.size();
        }
    }
}
