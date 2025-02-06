package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.config.SysConst;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.CollectUtils;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class C13DevPageTask extends TableCopyTask {
    @Override
    public String name() {
        return "页面表";
    }


    @Override
    public Integer order() {
        return 13;
    }

    @Override
    public String note() {
        return "页面表-dev_page";
    }

    @Override
    public void start(DataCopyParam context) {
        List<DevPage> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(DevPage.class, "select * from dev_page");
        tatal = list.size();
//        DB.executeUpdateSql("delete from dev_page where id not in ('01ea280e2600403dae260824d74b7a1e')");
        List<List<DevPage>> lists = CollectUtils.splitList(list, 100);
        for (List<DevPage> devPages : lists) {
            DB.batchSaveOrUpdate(devPages, DevPage.class);
            this.current += devPages.size();
        }

    }
}
