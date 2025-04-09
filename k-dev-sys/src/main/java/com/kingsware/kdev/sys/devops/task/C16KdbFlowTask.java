package com.kingsware.kdev.sys.devops.task;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.EditFlowInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbFlowQueryArgv;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.DevOpsManager;
import com.kingsware.kdev.sys.devops.TableCopyTask;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class C16KdbFlowTask extends TableCopyTask {
    @Override
    public String name() {
        return "FAAS逻辑编排";
    }


    @Override
    public Integer order() {
        return 16;
    }

    @Override
    public String note() {
        return "FAAS逻辑编排";
    }

    @Override
    public void start(DataCopyParam context) {
        // 查找当前
        KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
        List<FlowInfo> kdbFlows = DB.kdbApi().query(kdbFlowQueryArgv);
        Map<String, FlowInfo> kdbFlowMap = new HashMap<>();
        for (FlowInfo flowInfo : kdbFlows) {
            kdbFlowMap.put(flowInfo.getFlowId(), flowInfo);
        }

        List<SysKdbFlowRet> list = DevOpsManager.getInstance().flowList();
        tatal = list.size();
        for (SysKdbFlowRet ret : list) {
            SysKdbFlowRet detail = null;
            try {
                detail = DevOpsManager.getInstance().getFlow(ret.getId());

                if (detail != null && StringUtils.isNotEmpty(detail.getContent())) {
                    if ("base_flow".equalsIgnoreCase(ret.getId())) {
                        continue;
                    }
                    if (!kdbFlowMap.containsKey(ret.getId())) {
                        try {
                            String sql = "insert into flow (flowid,name,content,description) values (?,?,?,?)";
                            DB.byName("kingDB").executeUpdateSql(sql, detail.getId(), detail.getName(), detail.getContent(), detail.getDescription());
                        } catch (Exception ignored) {
                        }
                    }
                    EditFlowInfo editFlowInfo = new EditFlowInfo();
                    editFlowInfo.setFlowId(detail.getId());
                    editFlowInfo.setContent(detail.getContent());
                    editFlowInfo.setName(detail.getName());
                    editFlowInfo.setDescription(detail.getDescription());
                    DB.kdbApi().editFlow(editFlowInfo);
                }

            }
            catch (Exception ignored) {
            }
            finally {
                current++;
            }


        }
    }
}
