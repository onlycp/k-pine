package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.CollectUtils;
import com.kingsware.kdev.sys.devops.DataCopyConst;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.model.SysApi;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class C15SysLogicFlowTask extends TableCopyTask {
    @Override
    public String name() {
        return "逻辑编排";
    }


    @Override
    public Integer order() {
        return 15;
    }

    @Override
    public String note() {
        return "逻辑编排-sys_logic_flow";
    }

    @Override
    public void start(DataCopyParam context) {
        List<SysLogicFlow> list = DB.byName(DataCopyConst.PROD_DATA_SOURCE).findList(SysLogicFlow.class, "select * from sys_logic_flow");
        tatal = list.size();
        DB.executeUpdateSql("delete from sys_logic_flow");
        List<List<SysLogicFlow>> lists = CollectUtils.splitList(list, 100);
        for (List<SysLogicFlow> sysLogicFlows : lists) {
            DB.batchSaveOrUpdate(sysLogicFlows, SysLogicFlow.class);
            this.current += sysLogicFlows.size();
        }

    }
}
