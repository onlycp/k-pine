package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 新增流程实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFlowDefineRet extends BaseSimpleRet {

    @Data
    static class Node {
        /** id **/
        private String id;
        /** 名称 **/
        private String label;
        /** 节点类型 **/
        private String type;
        /** flowId **/
        private String flowId;
        /** 子流程名 **/
        private String subFlowName;
        /** 执行类型 **/
        private String executeType;
        /** 数据源名称 **/
        private String sourceName;
        /** 层高 **/
        private String zIndex;
        /** 坐标 **/
        private String position;
        /** 前置脚本 **/
        private String beforeContent;
        /** 脚本内容 **/
        private String content;
        /** 执行内容 **/
        private String afterContent;
        /** 大小写 **/
        private String columnLabelCase;
        /** 表单值 **/
        private Object exeData = new HashMap<>();
    }


    @Data
    static class Link {
        /** id **/
        private String id;
        /** 名称 **/
        private String label;
        /** 起始节点 **/
        private String source;
        /** 起始连接点 **/
        private String sourcePort;
        /** 到达节点 **/
        private String target;
        /** 到达连接点 **/
        private String targetPort;
        /** 表达式 **/
        private String expr;
        /** 异常处理标识 **/
        private String catchException;
    }

    /** 流程id **/
    private String id;
    /** 名称 **/
    private String name;
    /** 描述 **/
    private String description = "";
    /** app id **/
    private String applicationId;
    /** 标签 **/
    private String tags;
    /** 输入参数 **/
    private String inArgv;
    /** 输出参数 **/
    private String outArgv;
    /** 输入参数示例 **/
    private String inExample;
    /** 是否开始事务 **/
    private String tranCtrl;
    /** 默认数据源 **/
    private String defaultSourceName;
    /** v3以上版本逻辑编排流程图 **/
    private String newFlowJson;
    /** 节点列表 **/
    private List<Node> nodes = new ArrayList<>();
    /** 连续 **/
    private List<Link> links = new ArrayList<>();

    /** 设置 **/
    public void addNode(String id, String label, String type,  String executeType, String sourceName, String zIndex,
                        String position, String beforeContent, String content, String afterContent,
                        String flowId, String subFlowName, String columnLabelCase, Object exeData) {
        Node node = new Node();
        node.setId(id);
        node.setType(type);
        node.setLabel(label);
        node.setExecuteType(executeType);
        node.setSourceName(sourceName);
        node.setZIndex(zIndex);
        node.setPosition(position);
        node.setBeforeContent(beforeContent);
        node.setContent(content);
        node.setAfterContent(afterContent);
        node.setFlowId(flowId);
        node.setSubFlowName(subFlowName);
        node.setColumnLabelCase(columnLabelCase);
        node.setExeData(exeData);
        this.nodes.add(node);
    }

    /**
     * 增加link
     * @param id        id
     * @param label     标签
     * @param from      起始节点
     * @param to        到达节点
     * @param expr      表达式
     */
    public void addLink(String id, String label, String from, String to, String expr, String catchException) {
        Link link = new Link();
        link.setId(id);
        link.setLabel(label);
        link.setSource(from);
        link.setTarget(to);
        link.setExpr(expr);
        link.setCatchException(catchException);
        this.links.add(link);
    }
}
