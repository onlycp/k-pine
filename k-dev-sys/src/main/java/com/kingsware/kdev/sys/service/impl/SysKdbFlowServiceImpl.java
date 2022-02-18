package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.kflow.define.NodeDefinition;
import com.kingsware.kdev.core.kflow.define.NodeLink;
import com.kingsware.kdev.core.kflow.define.NodeTypeEnum;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowQueryArgv;
import com.kingsware.kdev.sys.ret.SysFlowDefineRet;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
        return toRet(list.get(0));
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

    private SysKdbFlowRet toRet(FlowInfo info) {
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
        return flowRet;
    }

    @Override
    public void add(SysKdbFlowArgv argv) {

        AddFlowInfo info = new AddFlowInfo();
//        info.setFlowId(StringUtils.getUUID());
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setDescription(argv.getDescription());
        if (StringUtils.isEmpty(argv.getContent())) {
            FlowDefinition definition = FlowDefinition.start(argv.getName()).toNode(NodeTypeEnum.TASK, "空节点").toEnd();
            info.setContent(definition.toJson());
        }

        KdbApi api = (KdbApi)(DB.getDefault());
        api.addFlow(info);
    }

    @Override
    public void edit(SysKdbFlowArgv argv) {
        EditFlowInfo info = new EditFlowInfo();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setFlowId(argv.getId());
        info.setDescription(argv.getDescription());
        KdbApi api = (KdbApi)(DB.getDefault());
        api.editFlow(info);
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
        // 转为ret类
        List<SysKdbFlowRet> retList = new ArrayList<>();
        for (FlowInfo infoL: list) {
            retList.add(toRet(infoL));
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

}
