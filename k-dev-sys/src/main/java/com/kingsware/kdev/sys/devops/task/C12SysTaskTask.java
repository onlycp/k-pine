package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.config.SysConst;
import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class C12SysTaskTask extends TableCopyTask {
    @Override
    public String name() {
        return "定时任务表";
    }


    @Override
    public Integer order() {
        return 12;
    }

    @Override
    public String note() {
        return "定时任务表-sys_task";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysTask> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysTask.class, "select * from sys_task");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_task");
        if (context.getTask()  == Boolean.FALSE) {
            for (SysTask sysTask : list) {
                if (sysTask.getTaskType() == 1 || SysConst.pineAppId.equalsIgnoreCase(sysTask.getApplicationId()) ||  SysConst.pineAppId.equalsIgnoreCase(sysTask.getAppId())) {
                    continue;
                }
                sysTask.setEnable(0);
            }
        }
        // 批量保存
        DB.batchSaveOrUpdate(list, SysTask.class);
        current = list.size();

    }
}
