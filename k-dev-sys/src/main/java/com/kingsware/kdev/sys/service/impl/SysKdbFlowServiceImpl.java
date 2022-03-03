package com.kingsware.kdev.sys.service.impl;

import com.google.common.collect.Maps;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.jsonschema.JsonschemaMock;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.define.*;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysFlowDebugArgv;
import com.kingsware.kdev.sys.argv.SysFlowDefineArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowQueryArgv;
import com.kingsware.kdev.sys.model.SysLogicFlow;
import com.kingsware.kdev.sys.ret.SysFlowDebugRet;
import com.kingsware.kdev.sys.ret.SysFlowDefineRet;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysKdbFlowServiceImpl extends BaseServiceImpl implements SysKdbFlowService {

    @Override
    public SysKdbFlowRet get(String id) {
        // 参数
        KdbFlowQueryArgv argv = new KdbFlowQueryArgv();
        argv.setFlowId(id);
        // 查询model
        KdbApi api = DB.kdbApi();
        List<FlowInfo> list = api.query(argv);

        // 转换成ret对象
        FlowInfo kdbFlow = list.get(0);
        // 从数据库查询
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", id).build());
        return toRet(kdbFlow, logicFlow);
    }

    @Override
    public SysFlowDefineRet getDefine(String id) {
        // 参数
        KdbFlowQueryArgv argv = new KdbFlowQueryArgv();
        argv.setFlowId(id);
        // 查询model
        KdbApi api = DB.kdbApi();
        List<FlowInfo> list = api.query(argv);
        FlowInfo flowInfo = list.get(0);
        // 转为流程定义
        SysFlowDefineRet defineRet = new SysFlowDefineRet();
        defineRet.setId(flowInfo.getFlowId());
        defineRet.setName(flowInfo.getName());
        defineRet.setDescription(flowInfo.getDescription());
        // 从数据库查询
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", id).build());
        if (logicFlow != null) {
            defineRet.setInArgv(logicFlow.getInArgv());
            defineRet.setOutArgv(logicFlow.getOutArgv());
            if (StringUtils.isNotEmpty(logicFlow.getInArgv())) {
                defineRet.setInExample(JsonschemaMock.getInstance().mock(logicFlow.getInArgv()));
            }
        }
        // 处理节点
        FlowDefinition flowDefinition = JsonUtil.toBean(flowInfo.getContent(), FlowDefinition.class);
        for(NodeDefinition nodeDefinition: flowDefinition.getNodeDefinitions()) {
            String executeType = "";
            String dataSource = "";
            String content = "";
            String afterContent = "";
            if (nodeDefinition.getExecute() != null && nodeDefinition.getExecute().getScript() != null) {
                executeType = nodeDefinition.getExecute().getScript().getType();
                dataSource = nodeDefinition.getExecute().getScript().getSourceName();
                content = nodeDefinition.getExecute().getScript().getContent();
            }
            if (nodeDefinition.getListener() != null && nodeDefinition.getListener().getAfter() != null && nodeDefinition.getListener().getAfter().getScript() != null) {
                afterContent = nodeDefinition.getListener().getAfter().getScript().getContent();
            }
            defineRet.addNode(nodeDefinition.getId(), nodeDefinition.getName(), nodeDefinition.getType(), executeType, dataSource, content, afterContent );
        }
        // 处理连线
        for (NodeLink link: flowDefinition.getNodeLinks()) {
            String expr = "";
            if (link.getConditions() != null && link.getConditions().getDecision() != null ) {
                expr = link.getConditions().getDecision().getExpr();
            }
            defineRet.addLink(link.getId(), link.getName(), link.getFrom(), link.getTo(), expr);
        }

        return defineRet;
    }

    @Override
    public void editDefine(SysFlowDefineArgv argv) {
        // 重新生成流程文件
        FlowDefinition flowDefinition = new FlowDefinition();
        // 设置流程名称
        flowDefinition.setName(argv.getName());
        // 处理节点
        for (SysFlowDefineArgv.Node node: argv.getNodes()) {
            NodeDefinition nodeDefinition = new NodeDefinition();
            nodeDefinition.setAuto(true);
            nodeDefinition.setDebug(false);
            nodeDefinition.setType(node.getType());
            nodeDefinition.setName(node.getLabel());
            nodeDefinition.setId(node.getId());
            // 判断执行类型
            if (StringUtils.isNotEmpty(node.getExecuteType())  && StringUtils.isNotEmpty(node.getContent())) {
                // 根据不同的执行类型，生成不同的执行内容
                if (ScriptTypeEnum.SQL.getValue().equals(node.getExecuteType())) {
                    nodeDefinition.setExecute(ExecuteDefinition.createSqlScript(node.getSourceName(), node.getContent()));
                }
                else if (ScriptTypeEnum.JS.getValue().equals(node.getExecuteType())) {
                    nodeDefinition.setExecute(ExecuteDefinition.createJsScript(node.getContent()));
                }
            }
            // 判断后置脚本

            if (StringUtils.isNotEmpty(node.getAfterContent())) {
                nodeDefinition.setListener(FlowNodeLister.createWithAfter(node.getAfterContent()));
            }
            flowDefinition.getNodeDefinitions().add(nodeDefinition);
        }
        // 处理连线
        for (SysFlowDefineArgv.Link link: argv.getLinks()) {
            // 先校验
            if(StringUtils.isEmpty(link.getSource()) || StringUtils.isEmpty(link.getTarget())) {
                throw BusinessException.serviceThrow(String.format("连线:%s的开始和结束节点均不能为空！", link.getLabel()));
            }
            if (link.getTarget().equals(link.getSource())) {
                throw BusinessException.serviceThrow(String.format("连线:%s的开始和结束节点不能是同一个！", link.getLabel()));
            }
            NodeLink nodeLink = new NodeLink();
            nodeLink.setId(link.getId());
            nodeLink.setName(link.getLabel());
            nodeLink.setFrom(link.getSource());
            nodeLink.setTo(link.getTarget());
            if (StringUtils.isNotEmpty(link.getExpr())) {
                nodeLink.setConditions(ConditionDefinition.createDecisionCondition(link.getExpr()));
            }
            flowDefinition.getNodeLinks().add(nodeLink);
        }

        // 保存到kdb
        EditFlowInfo info = new EditFlowInfo();
        info.setContent(flowDefinition.toJson());
        info.setName(argv.getName());
        info.setFlowId(argv.getId());
        info.setDescription(argv.getDescription());
        KdbApi api = (KdbApi)(DB.getDefault());
        api.editFlow(info);

    }

    private SysKdbFlowRet toRet(FlowInfo info, SysLogicFlow logicFlow) {
        SysKdbFlowRet flowRet = new SysKdbFlowRet();
        flowRet.setId(info.getFlowId());
        flowRet.setContent(info.getContent());
        flowRet.setName(info.getName());
        flowRet.setDescription(info.getDescription());
        if (info.getCreateTime() !=null ) {
            flowRet.setWhenCreated(new Timestamp(info.getCreateTime()));
        }
        else {
            flowRet.setWhenCreated(new Timestamp(0));
        }
        if (info.getUpdateTime() != null) {
            flowRet.setWhenModified(new Timestamp(info.getUpdateTime()));
        }
        if (logicFlow != null) {
            flowRet.setInArgv(logicFlow.getInArgv());
            flowRet.setOutArgv(logicFlow.getOutArgv());
            flowRet.setTags(logicFlow.getTags());
        }
        return flowRet;
    }

    @Override
    public void add(SysKdbFlowArgv argv) {

        AddFlowInfo info = new AddFlowInfo();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setDescription(argv.getDescription());
        if (StringUtils.isEmpty(argv.getContent())) {
            FlowDefinition definition = FlowDefinition.start(argv.getName()).toEnd();
            info.setContent(definition.toJson());
        }
        // 保存到kdb
        KdbApi api = (KdbApi)(DB.getDefault());
        String flowId = api.addFlow(info);
        // 将平台信息保存
        SysLogicFlow logicFlow = new SysLogicFlow();
        logicFlow.setName(argv.getName());
        logicFlow.setApplicationId(argv.getApplicationId());
        logicFlow.setNote(argv.getDescription());
        logicFlow.setInArgv(argv.getInArgv());
        logicFlow.setOutArgv(argv.getOutArgv());
        logicFlow.setTags(argv.getTags());
        logicFlow.setFlowId(flowId);
        DB.save(logicFlow);
    }

    @Override
    public void edit(SysKdbFlowArgv argv) {
        EditFlowInfo info = new EditFlowInfo();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setFlowId(argv.getId());
        info.setDescription(argv.getDescription());
        // 保存到kdb
        KdbApi api = (KdbApi)(DB.getDefault());
        api.editFlow(info);
        // 保存到数据库
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", argv.getId()).build());
        if (logicFlow == null) {
            logicFlow = new SysLogicFlow();
            logicFlow.setName(argv.getName());
            logicFlow.setApplicationId(argv.getApplicationId());
            logicFlow.setNote(argv.getDescription());
            logicFlow.setInArgv(argv.getInArgv());
            logicFlow.setOutArgv(argv.getOutArgv());
            logicFlow.setTags(argv.getTags());
            logicFlow.setFlowId(argv.getId());
            DB.save(logicFlow);
        }
        else {
            logicFlow.setName(argv.getName());
            logicFlow.setApplicationId(argv.getApplicationId());
            logicFlow.setNote(argv.getDescription());
            logicFlow.setInArgv(argv.getInArgv());
            logicFlow.setOutArgv(argv.getOutArgv());
            logicFlow.setTags(argv.getTags());
            logicFlow.setFlowId(argv.getId());
            DB.update(logicFlow);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysKdbFlowRet> query(SysKdbFlowQueryArgv argv) {
        KdbFlowQueryArgv info = new KdbFlowQueryArgv();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setParentId(argv.getParentId());
        // 查询所有数据
        KdbApi api = (KdbApi)(DB.getDefault());
        List<FlowInfo> list = api.query(info);
        // 从数据库里查询所有数据
        List<SysLogicFlow> logicFlows = DB.findList(SysLogicFlow.class, Collections.emptyList());
        Map<String, SysLogicFlow> dbMap = new HashMap<>();
        logicFlows.forEach(it -> dbMap.put(it.getFlowId(), it));
        // 转为ret类
        List<SysKdbFlowRet> retList = new ArrayList<>();
        for (FlowInfo infoL: list) {
            retList.add(toRet(infoL, dbMap.get(infoL.getFlowId())));
        }
        // 排序
        if (!retList.isEmpty()) {
            retList.sort(((o1, o2) -> o2.getWhenCreated().compareTo(o1.getWhenCreated())));
        }
        return PageUtil.memoryPage(argv, retList, SysKdbFlowRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        KdbApi api = (KdbApi)(DB.getDefault());
        for (String id: argv.getIds()) {
            api.deleteFlow(id);
            SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", id).build());
            if (logicFlow != null) {
                DB.delete(logicFlow);
            }
        }
    }

    @Override
    public void addOrUpdate(String name, String content) {
        // 参数
        KdbFlowQueryArgv argvName = new KdbFlowQueryArgv();
        argvName.setName(name);
        // 查询model
        KdbApi api = DB.kdbApi();
        List<FlowInfo> list = api.query(argvName);
        if (list.size() > 1) {
            throw BusinessException.serviceThrow("存在多个名称相同的流程");
        }
        // 如果是空，则是新增
        SysKdbFlowArgv argv = new SysKdbFlowArgv();
        argv.setName(name);
        argv.setContent(content);
        argv.setDescription("这个人很懒，什么都没有留下");
        if (list.isEmpty()) {
            add(argv);
        }
        else {
            argv.setId(list.get(0).getFlowId());
            edit(argv);
        }
    }

    @Override
    public SysFlowDebugRet debug(SysFlowDebugArgv argv) {
        KFlowContext context = KFlowContext.createBaseContext();
        String json = argv.getJson();
        if (StringUtils.isEmpty(json)) {
            json = "{}";
        }
        long t1 = System.currentTimeMillis();
        Map<String, Object> argvMap = JsonUtil.toMap(json);
        Object result = KdbFlowExecutor.getInstance().execute(argv.getFlowId(), argvMap, context);
        Object apiResult = KdbFlowExecutor.getInstance().toApiResult(result);
        long t2 = System.currentTimeMillis();
        SysFlowDebugRet ret = new SysFlowDebugRet();
        ret.setTakeMs(t2 - t1);
        ret.setResponseBody(JsonUtil.toJson(apiResult));
        return ret;
    }

}
