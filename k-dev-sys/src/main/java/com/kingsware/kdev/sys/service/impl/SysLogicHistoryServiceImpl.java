package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.orm.kdb.AddFlowInfo;
import com.kingsware.kdev.core.orm.kdb.EditFlowInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.FaasInvoke;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysLogicHistoryArgv;
import com.kingsware.kdev.sys.argv.SysLogicHistoryQueryArgv;
import com.kingsware.kdev.sys.model.SysLogicHistory;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.ret.SysLogicHistoryRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import com.kingsware.kdev.sys.service.SysLogicHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
public class SysLogicHistoryServiceImpl extends BaseServiceImpl implements SysLogicHistoryService {

    @Resource
    private SysKdbFlowService sysKdbFlowService;

    @Override
    public SysLogicHistoryRet get(String id) {
        // 查询model
        SysLogicHistory model = DB.findById(SysLogicHistory.class, id);
        // 转换成ret对象
        return (SysLogicHistoryRet) model2Ret(model, SysLogicHistoryRet.class);
    }

    @Override
    public void add(SysLogicHistoryArgv argv) {
        SysLogicHistory model = BeanUtils.copyObject(argv, SysLogicHistory.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysLogicHistoryArgv argv) {
        SysLogicHistory model = DB.findById(SysLogicHistory.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }

    @Override
    public void rollback(SysLogicHistoryArgv argv) {
        // 保存到kdb
        KdbApi api = (KdbApi) (DB.getDefault());
        FlowInfo ret = api.get(argv.getFlowId());

        if (ret != null) {
            // 查询到FAAS
            EditFlowInfo info = new EditFlowInfo();
            info.setContent(argv.getFlowJson());
            info.setName(ret.getName());
            info.setFlowId(argv.getFlowId());
            info.setDescription(ret.getDescription());

            api.editFlow(info);

            // 如果要回滚旧的，要清除一下 newFlowJson，去除 v3 设计器代码
            // 这样，当 v3 设计器重新被打开时，就会自动重新构建 newFlowJson，保证代码一致
            DB.executeUpdateSql("update sys_logic_flow set new_flow_json = null where flow_id=?", argv.getFlowId());

        }
        else {
            String name = DB.findSingleAttribute(String.class, "select name from sys_logic_flow where flow_id=?", argv.getFlowId());
            AddFlowInfo flowInfo = new AddFlowInfo();
            flowInfo.setFlowId(argv.getFlowId());
            flowInfo.setContent(argv.getFlowJson());
            flowInfo.setName(name);
            flowInfo.setDescription("");
            api.addFlow(flowInfo);
        }

        // 提交Git
        Map<String, Object> params = new HashMap<>();
        params.put("flowId", argv.getFlowId());
        KdbFlowResult result = FaasInvoke.callFlow("f494d882a9554d1cbd9fb72559f6b9db", params);
        if (result.getData() instanceof ErrorResult){
            throw BusinessException.serviceThrow(result.getExceptionStack());
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysLogicHistoryRet> query(SysLogicHistoryQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select dph.*, su.real_name as created_user_name, su.avatar as created_user_avatar from sys_logic_history dph left join sys_user su on su.id = dph.who_created where 1=1   ");
        if (StringUtils.isNotEmpty(argv.getFlowId())) {
            wrapper.addCondition("dph.flow_id", Op.EQ, argv.getFlowId());
        }
        wrapper.sortBy("dph.when_created desc");
        return (PageDataRet<SysLogicHistoryRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysLogicHistoryRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysLogicHistory.class, id);
        }
    }
}
