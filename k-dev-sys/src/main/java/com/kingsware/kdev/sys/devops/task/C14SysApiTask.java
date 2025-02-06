package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.CollectUtils;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysApi;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class C14SysApiTask extends TableCopyTask {
    @Override
    public String name() {
        return "接口数据";
    }


    @Override
    public Integer order() {
        return 14;
    }

    @Override
    public String note() {
        return "接口数据-sys_api";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysApi> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysApi.class, "select * from sys_api");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_api");
        List<List<SysApi>> lists = CollectUtils.splitList(list, 100);
        for (List<SysApi> sysApis : lists) {
            DB.batchSaveOrUpdate(sysApis, SysApi.class);
            this.current += sysApis.size();
        }

    }
}
