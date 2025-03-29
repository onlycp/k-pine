package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysConfig;
import com.kingsware.kdev.sys.model.SysUserUnit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class C08SysConfigTask extends TableCopyTask {
    @Override
    public String name() {
        return "系统配置表";
    }


    @Override
    public Integer order() {
        return 8;
    }

    @Override
    public String note() {
        return "系统配置表-sys_config";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysConfig> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysConfig.class, "select * from sys_config");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_config");
        // 批量保存
        DB.batchSaveOrUpdate(list, SysConfig.class);
        current = list.size();

    }
}
