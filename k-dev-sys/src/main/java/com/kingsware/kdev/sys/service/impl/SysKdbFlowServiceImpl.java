package com.kingsware.kdev.sys.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.jsonschema.JsonschemaMock;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.kflow.define.*;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.annotation.Transactional;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.sys.bean.ApplicationConfig;
import com.kingsware.kdev.sys.bean.CopyProcessData;
import com.kingsware.kdev.sys.bean.ExportData;
import com.kingsware.kdev.sys.bean.ExportRootData;
import com.kingsware.kdev.sys.manager.CopyAppManager;
import com.kingsware.kdev.sys.model.*;
import com.kingsware.kdev.sys.ret.SysDemoRet;
import com.kingsware.kdev.sys.ret.SysFlowDebugRet;
import com.kingsware.kdev.sys.ret.SysFlowDefineRet;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysApiService;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
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
@Slf4j
@SuppressWarnings("all")
public class SysKdbFlowServiceImpl extends BaseServiceImpl implements SysKdbFlowService {
    @Resource
    private SysApiService sysApiService;
    @Value("${app.mode.dev:false}")
    private boolean modeDev;


    @Override
    public SysKdbFlowRet get(String id) {
        // 转换成ret对象
        FlowInfo kdbFlow = DB.kdbApi().get(id);
        // 从数据库查询
        String sql = "select t0.in_argv, t0.app_id tran_ctrl, t0.name, t0.out_argv, t0.tags, t0.flow_id as id, t0.application_id, t1.name as application_name from sys_logic_flow t0 left join dev_application t1 on t1.id=t0.application_id where flow_id=?";
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
            defineRet.setDefaultSourceName(logicFlow.getDefaultSourceName());
            defineRet.setTranCtrl(logicFlow.getTranCtrl());
            defineRet.setNewFlowJson(logicFlow.getNewFlowJson());
            // 定义mock的merge map
            List<Map<String, Object>> mockMapList = new ArrayList<>();
            Map<String, Object> mockMap = JsonschemaMock.getInstance().mockMap(logicFlow.getInArgv());
            mockMapList.add(mockMap);
            // 获取子流程
            if (StringUtils.isNotEmpty(logicFlow.getSubFlowIds())) {
                String[] arr = logicFlow.getSubFlowIds().split(",");
                SqlWrapper sqlWrapper = SqlWrapper.selectWrapper(SysLogicFlow.class, "t", "t.in_argv, t.out_argv").in("t.flow_id", Arrays.asList(arr));
                List<SysLogicFlow> subLoginFlows = DB.findList(SysLogicFlow.class, sqlWrapper.getSql(), sqlWrapper.getParams().toArray(new Object[0]));
                for (SysLogicFlow sysLogicFlow : subLoginFlows) {
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
        // 查找所有节点模板
        List<DevFaasNode> devFaasNodes = DB.findList(DevFaasNode.class, "select * from dev_faas_node");
        Map<String, DevFaasNode> faasNodeMap = devFaasNodes.stream().collect(Collectors.toMap(DevFaasNode::getCode, node -> node));

        Set<String> nodeIds = new HashSet<>();
        for (NodeDefinition nodeDefinition : flowDefinition.getNodeDefinitions()) {
            String executeType = "";
            String dataSource = "";
            String content = "";
            String beforeContent = "";
            String afterContent = "";
            String zIndex = "";
            String subFlowName = "";
            String position = "";
            String columnLabelCase = "normal";
            if (nodeDefinition.getExecute() != null && nodeDefinition.getExecute().getScript() != null) {
                executeType = nodeDefinition.getExecute().getScript().getType();
                dataSource = nodeDefinition.getExecute().getScript().getSourceName();
                content = nodeDefinition.getExecute().getScript().getContent();
                columnLabelCase = nodeDefinition.getExecute().getScript().getColumnLabelCase();
                if (StringUtils.isEmpty(columnLabelCase)) {
                    columnLabelCase = "normal";
                }
            }
            if (nodeDefinition.getListener() != null && nodeDefinition.getListener().getBefore() != null && nodeDefinition.getListener().getBefore().getScript() != null) {
                beforeContent = nodeDefinition.getListener().getBefore().getScript().getContent();
            }
            if (nodeDefinition.getListener() != null && nodeDefinition.getListener().getAfter() != null && nodeDefinition.getListener().getAfter().getScript() != null) {
                afterContent = nodeDefinition.getListener().getAfter().getScript().getContent();
            }

            if (nodeDefinition.getExtra() != null) {
                zIndex = (String) nodeDefinition.getExtra().get("zIndex");
                position = (String) nodeDefinition.getExtra().get("position");
                subFlowName = (String) nodeDefinition.getExtra().get("subFlowName");
            }
            if (nodeDefinition.getFlowId() != null && StringUtils.isEmpty(subFlowName)) {
                FlowInfo subFlowInfo = DB.kdbApi().get(nodeDefinition.getFlowId());
                if (subFlowInfo != null) {
                    subFlowName = subFlowInfo.getName();
                }
            }
            if (nodeDefinition.getExtra().containsKey("exeData") ) {
                Map<String, Object> exeData = (Map<String, Object>) nodeDefinition.getExtra().get("exeData");
                if (!exeData.isEmpty()) {
                    DevFaasNode faasNode = faasNodeMap.get(exeData.get("code"));
                    if (faasNode != null) {
                        exeData.put("icon", faasNode.getIcon());
                        exeData.put("name", faasNode.getName());
                        if (exeData.containsKey("formData")) {
                            Map<String, Object> formData = (Map<String, Object>) exeData.get("formData");
                            if (formData.containsKey("content")) {
//                                formData.put("content", content);
                            }
                        }
                        nodeDefinition.getExtra().put("exeData", exeData);
                    }
                }
            }
            defineRet.addNode(nodeDefinition.getId(), nodeDefinition.getName(), nodeDefinition.getType(), executeType,
                    dataSource, zIndex, position, beforeContent, content, afterContent, nodeDefinition.getFlowId(), subFlowName, columnLabelCase, nodeDefinition.getExtra().get("exeData"));
            nodeIds.add(nodeDefinition.getId());
        }
        // 处理连线
        for (NodeLink link : flowDefinition.getNodeLinks()) {
            String expr = "";
            if (link.getConditions() != null && link.getConditions().getDecision() != null) {
                expr = link.getConditions().getDecision().getExpr();
            }
            if (StringUtils.isEmpty(link.getCatchException())) {
                link.setCatchException("false");
            }
            // 判断一下from和to是否存在，如果有一个不存在，那么就丢弃它
            if (nodeIds.contains(link.getFrom()) && nodeIds.contains(link.getTo())) {
                defineRet.addLink(link.getId(), link.getName(), link.getFrom(), link.getTo(), expr, link.getCatchException());
            }

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
        for (SysFlowDefineArgv.Node node : argv.getNodes()) {
            NodeDefinition nodeDefinition = new NodeDefinition();
            nodeDefinition.setAuto(true);
            nodeDefinition.setDebug(false);
            nodeDefinition.setType(node.getType());
            nodeDefinition.setName(node.getLabel());
            nodeDefinition.setId(node.getId());
            // 其他属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("zIndex", node.getZIndex());
            extra.put("position", node.getPosition());
            // 判断是否有执行器数据
            if (node.getExeData() != null && !node.getExeData().isEmpty()) {
                extra.put("exeData", node.getExeData());
            }

            // 如果是子流程
            if (node.getType().equalsIgnoreCase(NodeTypeEnum.SUB.getValue())) {
                nodeDefinition.setFlowId(node.getFlowId());
                String subFlowName = "";
                if (StringUtils.isNotEmpty(node.getFlowId())) {
                    FlowInfo subFlowInfo = DB.kdbApi().get(nodeDefinition.getFlowId());
                    if (subFlowInfo != null) {
                        subFlowName = subFlowInfo.getName();
                    }
                }

                extra.put("subFlowName", subFlowName);
            }
            // 只有任务节点是任务时，才会这些脚本信息
            if (node.getType().equalsIgnoreCase(NodeTypeEnum.TASK.getValue())) {
                // 判断执行类型
                if (StringUtils.isNotEmpty(node.getExecuteType()) && StringUtils.isNotEmpty(node.getContent())) {
                    // 根据不同的执行类型，生成不同的执行内容
                    if (ScriptTypeEnum.SQL.getValue().equals(node.getExecuteType())) {
                        nodeDefinition.setExecute(ExecuteDefinition.createSqlScript(node.getSourceName(), node.getContent(), node.getColumnLabelCase()));
                    } else if (ScriptTypeEnum.JS.getValue().equals(node.getExecuteType())) {
                        nodeDefinition.setExecute(ExecuteDefinition.createJsScript(node.getContent()));
                    }
                }

                // 创建前置、后置脚本监听器
                if (ScriptTypeEnum.SQL.getValue().equals(node.getExecuteType())) {
                    if (StringUtils.isNotEmpty(node.getBeforeContent()) && StringUtils.isNotEmpty(node.getAfterContent())) {
                        nodeDefinition.setListener(FlowNodeLister.createWithBeforeAndAfter(node.getBeforeContent(), node.getAfterContent()));
                    } else if (StringUtils.isNotEmpty(node.getBeforeContent())) {   // 有前置
                        nodeDefinition.setListener(FlowNodeLister.createWithBefore(node.getBeforeContent()));
                    } else if (StringUtils.isNotEmpty(node.getAfterContent())) {    // 有后置
                        nodeDefinition.setListener(FlowNodeLister.createWithAfter(node.getAfterContent()));
                    }
                }

            }


            // 加入其他属性
            nodeDefinition.setExtra(extra);
            flowDefinition.getNodeDefinitions().add(nodeDefinition);
        }
        // 处理连线
        for (SysFlowDefineArgv.Link link : argv.getLinks()) {
            // 先校验
            if (StringUtils.isEmpty(link.getSource()) || StringUtils.isEmpty(link.getTarget())) {
                throw BusinessException.serviceThrow(I18n.t("SysKdbFlowServiceImpl.linkEmptyTip","连线的开始和结束节点均不能为空！"));
            }
            if (link.getTarget().equals(link.getSource())) {
                throw BusinessException.serviceThrow(I18n.t("SysKdbFlowServiceImpl.allInOneTip","连线开始和结束节点不能是同一个！"));
            }
            NodeLink nodeLink = new NodeLink();
            nodeLink.setId(link.getId());
            nodeLink.setName(link.getLabel());
            nodeLink.setFrom(link.getSource());
            nodeLink.setTo(link.getTarget());
            nodeLink.setCatchException(link.getCatchException());
            if (StringUtils.isEmpty(link.getCatchException())) {
                nodeLink.setCatchException("false");
            }
            if (StringUtils.isNotEmpty(link.getExpr())) {
                nodeLink.setConditions(ConditionDefinition.createDecisionCondition(link.getExpr()));
            }
            flowDefinition.getNodeLinks().add(nodeLink);
        }

        // 查询到FAAS
        EditFlowInfo info = new EditFlowInfo();
        info.setContent(flowDefinition.toJson());
        info.setName(argv.getName());
        info.setFlowId(argv.getId());
        info.setDescription(argv.getDescription());
        // 保存到kdb
        KdbApi api = (KdbApi) (DB.getDefault());
        api.editFlow(info);

        // 保存到数据库
        SysLogicFlow sysLogicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", argv.getId()).build());
        if (sysLogicFlow != null) {
            // 更新修改时间
            if (!argv.isV3()) {
                sysLogicFlow.setNewFlowJson("");
            }
            DB.update(sysLogicFlow);
        }

        // 保存历史记录
        SysLogicHistory flowHistory = new SysLogicHistory();
        flowHistory.setFlowId(argv.getId());
        flowHistory.setFlowJson(info.getContent());
        DB.save(flowHistory);

    }

    /**
     * 根据json获取所有子流程id
     *
     * @param content 流程定义
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
        for (NodeDefinition nodeDefinition : flowDefinition.getNodeDefinitions()) {
            if (nodeDefinition.getType().equalsIgnoreCase(NodeTypeEnum.SUB.getValue()) && StringUtils.isNotEmpty(nodeDefinition.getFlowId())) {
                flowIds.add(nodeDefinition.getFlowId());
            }
        }
        return StringUtils.joinToString(Arrays.asList(flowIds.toArray()), ",");
    }

    private SysKdbFlowRet toRet(FlowInfo info, SysKdbFlowRet logicFlow) {
        SysKdbFlowRet flowRet = new SysKdbFlowRet();
        flowRet.setInArgv(logicFlow.getInArgv());
        flowRet.setOutArgv(logicFlow.getOutArgv());
        flowRet.setTags(logicFlow.getTags());
        flowRet.setApplicationName(logicFlow.getApplicationName());
        flowRet.setApplicationId(logicFlow.getApplicationId());
        flowRet.setApiUrl(logicFlow.getApiUrl());
        flowRet.setTranCtrl(logicFlow.getTranCtrl());
        flowRet.setApiMethod(logicFlow.getApiMethod());
        flowRet.setId(logicFlow.getId());
        flowRet.setName(logicFlow.getName());
        flowRet.setDescription(logicFlow.getDescription());
        flowRet.setDbId(logicFlow.getDbId());

        if (info != null) {
            flowRet.setContent(info.getContent());
            if (info.getCreateTime() != null) {
                flowRet.setWhenCreated(new Timestamp(info.getCreateTime()));
            } else {
                flowRet.setWhenCreated(new Timestamp(0));
            }
            if (info.getUpdateTime() != null) {
                flowRet.setWhenModified(new Timestamp(info.getUpdateTime()));
            }
        }


        return flowRet;
    }

    @Override
    public void add(SysKdbFlowArgv argv) {
        // 校验接口唯一性
        if (StringUtils.isNotEmpty(argv.getApiUrl()) && StringUtils.isNotEmpty(argv.getApiMethod())) {
            SysApi sysApi = new SysApi();
            sysApi.setApiUrl(argv.getApiUrl());
            sysApi.setApiMethod(argv.getApiMethod());
            sysApiService.checkUnique(sysApi);
        }

        AddFlowInfo info = new AddFlowInfo();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setDescription(argv.getDescription());
        if (StringUtils.isEmpty(argv.getContent())) {
            FlowDefinition definition = FlowDefinition.start(argv.getName()).toEnd();
            info.setContent(definition.toJson());
        }
        // 保存到kdb
        KdbApi api = (KdbApi) (DB.getDefault());
        String logicFlowId = StringUtils.isNotEmpty(argv.getId()) ? argv.getId() : StringUtils.getUUID();
        info.setFlowId(logicFlowId);
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
        logicFlow.setTranCtrl(argv.getTranCtrl());

        // 保存历史记录
        SysLogicHistory flowHistory = new SysLogicHistory();
        flowHistory.setFlowId(flowId);
        flowHistory.setFlowJson(info.getContent());
        DB.save(flowHistory);

        // 获取子流程id
        String subFlowIds = getSubFlowIds(argv.getContent());
        logicFlow.setSubFlowIds(subFlowIds);
        DB.save(logicFlow);

        if (StringUtils.isNotEmpty(argv.getApiUrl()) && StringUtils.isNotEmpty(argv.getApiMethod())) {
            SysApiArgv apiArgv = new SysApiArgv();
            apiArgv.setApiUrl(argv.getApiUrl());
            apiArgv.setApiMethod(argv.getApiMethod());
            apiArgv.setAppId(argv.getApplicationId());
            apiArgv.setApiName(argv.getName());
            apiArgv.setApiFlowId(logicFlow.getFlowId());
            apiArgv.setCallType(2);
            apiArgv.setApiNote(argv.getDescription());
            apiArgv.setApiTags(argv.getTags());
            apiArgv.setApiCode(argv.getApiCode());
            sysApiService.add(apiArgv);
        }

    }

    @Override
    public void edit(SysKdbFlowArgv argv) {

        // 获取子流程id
        String subFlowIds = getSubFlowIds(argv.getContent());
        // 保存到数据库
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, Expr.builder().add("flowId", "=", argv.getId()).build());
        if (logicFlow != null) {
            logicFlow.setName(argv.getName());
            logicFlow.setApplicationId(argv.getApplicationId());
            logicFlow.setNote(argv.getDescription());
            logicFlow.setInArgv(argv.getInArgv());
            logicFlow.setOutArgv(argv.getOutArgv());
            logicFlow.setTags(argv.getTags());
            logicFlow.setFlowId(argv.getId());
            logicFlow.setSubFlowIds(subFlowIds);
            logicFlow.setTranCtrl(argv.getTranCtrl());
            DB.update(logicFlow);
        }
        // 更新流程名称
        KdbApi api = (KdbApi) (DB.getDefault());
        // 查询到FAAS
        FlowInfo flowInfo = api.get(argv.getId());
        EditFlowInfo info = new EditFlowInfo();
        info.setContent(StringUtils.isEmpty(argv.getContent()) ? flowInfo.getContent() : argv.getContent());
        info.setName(argv.getName());
        info.setFlowId(argv.getId());
        info.setDescription(argv.getDescription());
        // 保存到kdb
        // 查找关联接口
        List<String> apiIds = DB.findSingleAttributeList(String.class, "select id from sys_api where api_flow_id=?", argv.getId());
        for (String apiId : apiIds) {
            sysApiService.cacheApi(apiId);
        }
        api.editFlow(info);

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
        kdbFlowArgv.setName(kdbFlow.getName() + I18n.t("common.copied", "副本") );
        kdbFlowArgv.setDescription(kdbFlow.getDescription());
        // 直接调用新增接口
        add(kdbFlowArgv);
    }

    @Override
    @SuppressWarnings("")
    public PageDataRet<SysKdbFlowRet> query(SysKdbFlowQueryArgv argv) {

//        /** 流程id **/
//        private String id;
//        /** 数据库数据id **/
//        private String dbId;
//        /** 流程内容 **/
//        private String content;
//        /** 名称 **/
//        private String name;
//        /** 描述 **/
//        private String description = "";
//        /**  应用名称 **/
//        private String applicationId;
//        /**  应用名称 **/
//        private String applicationName;
//        /** 标签 **/
//        private String tags;
//        /** 输入参数 **/
//        private String inArgv;
//        /** 输出参数 **/
//        private String outArgv;
//        /** 关联接口URL **/
//        private String apiUrl;
//        /** 关联接口请求方式 **/
//        private String apiMethod;
//        /** 创建时间 **/
//        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
//        private Timestamp whenCreated;
//        /** 更新时间 **/
//        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
//        private Timestamp whenModified;
//        /** 是否开始事务 **/
//        private String tranCtrl;

        // 拼装sql
        // 根据条件查询appId、tags和apiUrl数据
        String selectFields = "t0.id as db_id, t0.name,t0.in_argv, t0.out_argv, t0.tags, t0.flow_id as id, t0.application_id, t1.name as application_name, sa.api_url, sa.api_method, t0.when_created, t0.note  as description, t0.app_id as tran_ctrl ";

//        if (argv.isPageQuery()) {
//            selectFields = "t0.id as db_id, t0.name,t0.in_argv, t0.out_argv, t0.tags, t0.flow_id as id, t0.application_id, t1.name as application_name, sa.api_url, sa.api_method, t0.when_created, t0.note  as description, t0.app_id as tran_ctrl ";
//        }
        String sql = "select " + selectFields +
                " from sys_logic_flow t0 " +
                " left join sys_api sa on sa.api_flow_id = t0.flow_id " +
                " left join dev_application t1 on t1.id=t0.application_id " +
                " where 1=1";
        SqlWrapper wrapper = new SqlWrapper(sql);

        if (StringUtils.isNotEmpty(argv.getApplicationId())) {
            wrapper.addCondition("t0.application_id", Op.EQ,  argv.getApplicationId());
        }
        if (StringUtils.isNotEmpty(argv.getApiUrl())) {
            wrapper.addCondition("sa.api_url", Op.LIKE, "%" + argv.getApiUrl() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getTags())) {
            wrapper.addCondition("t0.tags", Op.LIKE, "%" + argv.getTags() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("t0.name", Op.LIKE, "%" + argv.getName() + "%");
        }
        wrapper.sortBy("t0.when_created desc");

        return (PageDataRet<SysKdbFlowRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysKdbFlowRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        KdbApi api = (KdbApi) (DB.getDefault());
        for (String id : argv.getIds()) {
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
            throw BusinessException.serviceThrow(I18n.t("SysKdbFlowServiceImpl.nameDuplicate", "存在多个名称相同的流程"));
        }
        // 如果是空，则是新增
        SysKdbFlowArgv argv = new SysKdbFlowArgv();
        argv.setName(name);
        argv.setContent(content);
        argv.setDescription(I18n.t("SysKdbFlowServiceImpl.defaultNote", "这个人很懒，什么都没有留下"));
        if (list.isEmpty()) {
            add(argv);
        } else {
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
        requestMap.put("headers", ServletUtil.getHeaders(ServletUtil.request()));
        argvMap.put("request", requestMap);
        argvMap.put("_appId", logicFlow.getApplicationId());
        // 调用流程
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(argv.getFlowId(), logicFlow == null ? "" : logicFlow.getSubFlowIds(), argvMap, context, true, false, argv.getDebugger());
        long t2 = System.currentTimeMillis();
//        log.info("用时:{}", t2-t1);

        SysFlowDebugRet ret = new SysFlowDebugRet();
        ret.setTakeMs(t2 - t1);
        ret.setResponseBody(JsonUtil.toJson(result.getData()));
        ret.setLog(result.getLog());
        ret.setExceptionStack(result.getExceptionStack());
        return ret;
    }

    @Override
    public void copyData(String id, CopyContextArgv context) {
        // 拷贝过程数据
        CopyProcessData copyProcessData = new CopyProcessData();
        // 拷贝逻辑编排数据
        CopyAppManager.getInstance().copyFlowData(id, context, copyProcessData);
        if (copyProcessData.getToCopySet().size() <= 1) {
            throw BusinessException.serviceThrow(I18n.t("SysKdbFlowServiceImpl.logicEmptyCopyFail", "当前逻辑编排流程定义为空，不允许拷贝！"));
        }
        // 开始替换
        CopyAppManager.getInstance().action(copyProcessData, context);
    }

    @Override
    public void exportPine(MultiIdArgv argv) {

        CopyContextArgv contextArgv = new CopyContextArgv();
        contextArgv.setDeepCopy(1);
        contextArgv.setUrlSuffix("v1");
        contextArgv.setCodeSuffix("v1");
        contextArgv.setTargetAppId("hello-world");
        contextArgv.setSourceAppId("hello-world");
        contextArgv.setWithSystemData(1);
        contextArgv.setNameSuffix("hello-world");
        CopyProcessData copyProcessData = new CopyProcessData();
        for (String id: argv.getIds()) {
            SysLogicFlow sysLogicFlow = DB.findById(SysLogicFlow.class, id);
            CopyAppManager.getInstance().copyFlowData(id, contextArgv, copyProcessData);
        }
        KdbRetFile retFile = CopyAppManager.getInstance().exportPine(copyProcessData);
        ServletUtil.responseFile(ServletUtil.response(), "Logic" + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_1) + ".pine", retFile.getData());
    }


}
