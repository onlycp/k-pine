package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.DevOpsManager;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.ret.SysKdbFunRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class C17FunctionTask extends TableCopyTask {
    @Override
    public String name() {
        return "FAAS函数";
    }


    @Override
    public Integer order() {
        return 17;
    }

    @Override
    public String note() {
        return "FAAS函数";
    }

    @Override
    public void start(DataCopyParam context) {
        // 查找当前
        FunctionQueryArgv functionQueryArgv = new FunctionQueryArgv();
        List<Functions> functions = DB.kdbApi().queryFunction(functionQueryArgv);
        Map<String, Functions> functionsHashMap = new HashMap<>();
        for (Functions fun : functions) {
            functionsHashMap.put(fun.getId(), fun);
        }
        List<SysKdbFunRet> list = DevOpsManager.getInstance().funList();
        tatal = list.size();
        for (SysKdbFunRet ret : list) {
            Functions fun = functionsHashMap.get(ret.getId());
            // 如果没有，则新增，后面之所以再次编辑，就是为了实时生效
            if (fun == null) {
                try {
                    String sql = "insert into functions (id,name,type,script) values (?,?,?,?)";
                    DB.byName("kingDB").executeUpdateSql(sql, ret.getId(), ret.getName(), ret.getType(), ret.getScript());

                }
                catch (Exception ignored) {

                }
            }
            EditFunctionInfo editFunctionInfo = new EditFunctionInfo();
            editFunctionInfo.setId(ret.getId());
            editFunctionInfo.setName(ret.getName());
            editFunctionInfo.setDesc(ret.getDesc());
            editFunctionInfo.setScript(ret.getScript());
            editFunctionInfo.setType(ret.getType());
            DB.kdbApi().editFun(editFunctionInfo);
            current++;
        }

    }
}
