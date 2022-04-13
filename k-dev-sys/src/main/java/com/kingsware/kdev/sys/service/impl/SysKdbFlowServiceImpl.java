package com.kingsware.kdev.sys.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.jsonschema.JsonschemaMock;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.define.*;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MapUtil;
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
import java.util.stream.Collectors;

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
        // 转换成ret对象
        FlowInfo kdbFlow = DB.kdbApi().get(id);
        // 从数据库查询
        String sql = "select t0.in_argv, t0.out_argv, t0.tags, t0.flow_id as id, t0.application_id, t1.name as application_name from sys_logic_flow t0 left join dev_application t1 on t1.id=t0.application_id where flow_id=?";
        SysKdbFlowRet logicFlow = DB.findOne(SysKdbFlowRet.class, sql, id);
        return toRet(kdbFlow, logicFlow);
    }

    @Override
    public SysFlowDefineRet getDefine(String id) {

        FlowInfo flowInfo = DB.kdbApi().get(id);
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
            defineRet.setApplicationId(logicFlow.getApplicationId());
            defineRet.setTags(logicFlow.getTags());
            // 定义mock的merge map
            List<Map<String, Object>> mockMapList = new ArrayList<>();
            Map<String, Object> mockMap = JsonschemaMock.getInstance().mockMap(logicFlow.getInArgv());
            mockMapList.add(mockMap);
            // 获取子流程
            if (StringUtils.isNotEmpty(logicFlow.getSubFlowIds())) {
                String[] arr = logicFlow.getSubFlowIds().split(",");
                SqlWrapper sqlWrapper = SqlWrapper.selectWrapper(SysLogicFlow.class, "t", "t.in_argv, t.out_argv").in("t.flow_id", Arrays.asList(arr));
                List<SysLogicFlow> subLoginFlows = DB.findList(SysLogicFlow.class, sqlWrapper.getSql(), sqlWrapper.getParams().toArray(new Object[0]));
                for (SysLogicFlow sysLogicFlow: subLoginFlows) {
                    Map<String, Object> subMockMap = JsonschemaMock.getInstance().mockMap(sysLogicFlow.getInArgv());
                    mockMapList.add(subMockMap);
                }
            }
            // 设置例子
            defineRet.setInExample(JsonUtil.toJson(MapUtil.mergerMap(mockMapList)));
        }
        // 处理节点
        FlowDefinition flowDefinition = JsonUtil.toBean(flowInfo.getContent(), FlowDefinition.class);
        if (flowDefinition == null) {
            return defineRet;
        }
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
            defineRet.addNode(nodeDefinition.getId(), nodeDefinition.getName(), nodeDefinition.getType(), executeType, dataSource, content, afterContent, nodeDefinition.getFlowId() );
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
            // 如果是子流程
            if (node.getType().equalsIgnoreCase(NodeTypeEnum.SUB.getValue())) {
                nodeDefinition.setFlowId(node.getFlowId());
            }
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

        SysKdbFlowArgv sysKdbFlowArgv = new SysKdbFlowArgv();
        sysKdbFlowArgv.setInArgv(argv.getInArgv());
        sysKdbFlowArgv.setOutArgv(argv.getOutArgv());
        sysKdbFlowArgv.setId(argv.getId());
        sysKdbFlowArgv.setName(argv.getName());
        sysKdbFlowArgv.setTags(argv.getTags());
        sysKdbFlowArgv.setApplicationId(argv.getApplicationId());
        sysKdbFlowArgv.setContent(flowDefinition.toJson());
        this.edit(sysKdbFlowArgv);

    }

    /**
     * 根据json获取所有子流程id
     * @param content   流程定义
     * @return
     */
    private String getSubFlowIds(String content) {
        // 处理节点
        String finalContent = StringUtils.isEmpty(content) ? "{}" : content;
        FlowDefinition flowDefinition = JsonUtil.toBean(finalContent, FlowDefinition.class);
        if (flowDefinition == null) {
            return "";
        }
        Set<String> flowIds = new HashSet<>();
        for (NodeDefinition nodeDefinition: flowDefinition.getNodeDefinitions()) {
            if (nodeDefinition.getType().equalsIgnoreCase(NodeTypeEnum.SUB.getValue()) && StringUtils.isNotEmpty(nodeDefinition.getFlowId())) {
                flowIds.add(nodeDefinition.getFlowId());
            }
        }
        return StringUtils.joinToString(Arrays.asList(flowIds.toArray()), ",");
    }

    private SysKdbFlowRet toRet(FlowInfo info, SysKdbFlowRet logicFlow) {
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
            flowRet.setApplicationName(logicFlow.getApplicationName());
            flowRet.setApplicationId(logicFlow.getApplicationId());
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
        // 获取子流程id
        String subFlowIds = getSubFlowIds(argv.getContent());
        logicFlow.setSubFlowIds(subFlowIds);
        DB.save(logicFlow);
    }

    @Override
    public void edit(SysKdbFlowArgv argv) {
        EditFlowInfo info = new EditFlowInfo();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setFlowId(argv.getId());
        info.setDescription(argv.getDescription());
        // 获取子流程id
        String subFlowIds = getSubFlowIds(argv.getContent());
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
            logicFlow.setSubFlowIds(subFlowIds);
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
            logicFlow.setSubFlowIds(subFlowIds);
            DB.update(logicFlow);
        }
    }

    @Override
    public void copy(String id) {

        // 查询model
        KdbApi api = DB.kdbApi();
        // 转换成ret对象
        FlowInfo kdbFlow = api.get(id);
        // 拷贝
        SysKdbFlowArgv kdbFlowArgv = new SysKdbFlowArgv();
        // 查找本地库的信息
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", id).build());
        if (logicFlow != null) {
            kdbFlowArgv.setInArgv(logicFlow.getInArgv());
            kdbFlowArgv.setOutArgv(logicFlow.getOutArgv());
            kdbFlowArgv.setApplicationId(logicFlow.getApplicationId());
            kdbFlowArgv.setTags(logicFlow.getTags());
        }
        kdbFlowArgv.setContent(kdbFlow.getContent());
        kdbFlowArgv.setName(kdbFlow.getName() + "副本");
        kdbFlowArgv.setDescription(kdbFlow.getDescription());
        // 直接调用新增接口
        add(kdbFlowArgv);
    }

    @Override
    @SuppressWarnings("")
    public PageDataRet<SysKdbFlowRet> query(SysKdbFlowQueryArgv argv) {
        KdbFlowQueryArgv info = new KdbFlowQueryArgv();
        // 查询所有数据
        KdbApi api = (KdbApi)(DB.getDefault());
        List<FlowInfo> list = api.query(info);
        // 从数据库里查询所有数据
        String sql = "select t0.in_argv, t0.out_argv, t0.tags, t0.flow_id as id, t0.application_id, t1.name as application_name from sys_logic_flow t0 left join dev_application t1 on t1.id=t0.application_id";
        if (StringUtils.isNotEmpty(argv.getApplicationId())) {
            sql += " where (t0.application_id = '" + argv.getApplicationId() + "' or t0.application_id is null)";
        }
        List<SysKdbFlowRet> logicFlows  = DB.findList(SysKdbFlowRet.class, sql );
        Map<String, SysKdbFlowRet> dbMap = new HashMap<>();
        logicFlows.forEach(it -> dbMap.put(it.getId(), it));
        // 转为ret类
        List<SysKdbFlowRet> retList = new ArrayList<>();
        for (FlowInfo infoL: list) {
            retList.add(toRet(infoL, dbMap.get(infoL.getFlowId())));
        }
        // 查询过滤
        List<SysKdbFlowRet> filterList = retList.stream().filter(it -> {
            if (StringUtils.isNotEmpty(argv.getName())) {
                return it.getName().contains(argv.getName());
            }
            return true;
        }).filter(it -> {
            if (StringUtils.isNotEmpty(argv.getApplicationId()) ) {
                return it.getApplicationId() != null && it.getApplicationId().equalsIgnoreCase(argv.getApplicationId());
            }
            return true;
        }).filter(it -> {
            if (StringUtils.isNotEmpty(argv.getTags())) {
                return it.getTags() != null && it.getTags().contains(argv.getTags());
            }
            return true;
        }).collect(Collectors.toList());
        // 排序
        if (!filterList.isEmpty()) {
            filterList.sort(((o1, o2) -> o2.getWhenCreated().compareTo(o1.getWhenCreated())));
        }
        return PageUtil.memoryPage(argv, filterList, SysKdbFlowRet.class);
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
        // 查询流程信息
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", argv.getFlowId()).build());
        // 创建上下文
        KFlowContext context = KFlowContext.createBaseContext(logicFlow == null ? "{}" : logicFlow.getInArgv(), logicFlow == null ? "{}" : logicFlow.getOutArgv());
        String json = argv.getJson();
        if (StringUtils.isEmpty(json)) {
            json = "{}";
        }
        long t1 = System.currentTimeMillis();
        // 上下文参数
        Map<String, Object> argvMap = new HashMap<>();
        // 判断是list还是object
        JsonNode jsonNode = JsonUtil.toTree(json);
        if (jsonNode instanceof ObjectNode) {
            Map<String, Object> fieldMap = JsonUtil.toMap(json);
            if (fieldMap != null) {
                argvMap.putAll(fieldMap);
            }
        }
        // 将请求的body加进去
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("body", json);
        argvMap.put("request", requestMap);
        // 调用流程
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(argv.getFlowId(), logicFlow == null? "" :logicFlow.getSubFlowIds(),argvMap, context);
        long t2 = System.currentTimeMillis();
        SysFlowDebugRet ret = new SysFlowDebugRet();
        ret.setTakeMs(t2 - t1);
        ret.setResponseBody(JsonUtil.toJson(result));
        return ret;
    }

}
