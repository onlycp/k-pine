package com.kingsware.kdev.core.kflow.define;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流程
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:04 下午
 */
@Data
public class FlowDefinition {
    /** 流程名称 **/
    private String name;
    /** 流程节点 **/
    @JsonProperty("node_definition")
    private List<NodeDefinition> nodeDefinitions = new ArrayList<>();
    /** 流程连线 **/
    @JsonProperty("node_link")
    private List<NodeLink> nodeLinks = new ArrayList<>();
    /** 当前节点 **/
    @JsonIgnore
    private NodeDefinition currentNode;

    public FlowDefinition() {
    }

    private FlowDefinition(String name) {
        this.name = name;
    }


    /**
     * 流程开始
     * @return
     */
    public static FlowDefinition start(String flowName) {
        // 创建流程
        FlowDefinition flow = new FlowDefinition(flowName);
        // 创建开始节点
        NodeDefinition node = new NodeDefinition();
        node.setId("start");
        node.setName("开始");
        node.setAuto(true);
        node.setState(NodeStateEnum.COMPLETED.getValue());
        node.setDebug(false);
        node.setType(NodeTypeEnum.START.getValue());
        node.setExecute(new ExecuteDefinition());
        flow.addNode(node);
        // 设置当前节点
        flow.currentNode = node;
        return flow;
    }

    /**
     * 增加节点，如果已存在的，就不处理
     * @param nodeDefinition  节点
     */
    private void addNode(NodeDefinition nodeDefinition) {
        NodeDefinition node = nodeByName(nodeDefinition.getName());
        if (node == null) {
            this.nodeDefinitions.add(nodeDefinition);
        }


    }

    /**
     * 通过名称获取节点
     * @param name  节点名称
     * @return      节点
     */
    public NodeDefinition nodeByName(String name) {
        Optional<NodeDefinition> optional = nodeDefinitions.stream().filter(it -> it.getName().equals(name)).findFirst();
        return optional.orElse(null);
    }

    /**
     * 流程开始
     * @return
     */
    public FlowDefinition toEnd(String expr) {

        String nodeName = "结束";
        NodeDefinition node = nodeByName(nodeName);
        if (node == null) {
            node = new NodeDefinition();
            node.setId("end");
            node.setName(nodeName);
            node.setAuto(true);
            node.setState(NodeStateEnum.COMPLETED.getValue());
            node.setDebug(false);
            node.setType(NodeTypeEnum.END.getValue());
            node.setExecute(new ExecuteDefinition());
            addNode(node);
        }
        // 创建连接
        createLink(currentNode, node, expr);
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;
    }

    /**
     * 流程开始
     * @return
     */
    public FlowDefinition toEnd() {
        return toEnd("");
    }

    /**
     * 创建SQL节点
     * @param sourceName 数据源名称
     * @param sql        SQL
     * @return           节点
     */
    public FlowDefinition toSql(String name, String sourceName, String sql, String expr) {

        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(String.format("node-%s", StringUtils.getUUID()));
            node.setName(name);
            node.setAuto(true);
            node.setState(NodeStateEnum.COMPLETED.getValue());
            node.setDebug(false);
            node.setType(NodeTypeEnum.TASK.getValue());
            node.setExecute(ExecuteDefinition.createSqlScript(sourceName, sql, ""));
            addNode(node);
        }

        // 创建连接
        createLink(currentNode, node, expr);
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;

    }

    /**
     * 创建SQL节点
     * @param sourceName 数据源名称
     * @param sql        SQL
     * @return           节点
     */
    public FlowDefinition toSqlWithId(String nodeId, String name, String sourceName, String sql, String expr) {

        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(nodeId);
            node.setName(name);
            node.setAuto(true);
            node.setState(NodeStateEnum.COMPLETED.getValue());
            node.setDebug(false);
            node.setType(NodeTypeEnum.TASK.getValue());
            node.setExecute(ExecuteDefinition.createSqlScript(sourceName, sql, ""));
            addNode(node);
        }

        // 创建连接
        createLink(currentNode, node, expr);
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;

    }



    /**
     * 创建SQL节点
     * @param sourceName 数据源名称
     * @param sql        SQL
     * @return           节点
     */
    public FlowDefinition toSql(String name, String sourceName, String sql) {
        return toSql(name, sourceName, sql, "");
    }

    /**
     * 创建SQL节点
     * @param sourceName 数据源名称
     * @param sql        SQL
     * @return           节点
     */
    public FlowDefinition toSqlWithAfter(String name, String sourceName, String sql, String expr, String afterJavaScript) {
        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(String.format("node-%s", StringUtils.getUUID()));
            node.setName(name);
            node.setAuto(true);
            node.setState(NodeStateEnum.COMPLETED.getValue());
            node.setDebug(false);
            node.setType(NodeTypeEnum.TASK.getValue());
            node.setExecute(ExecuteDefinition.createSqlScript(sourceName, sql, ""));
            node.setListener(FlowNodeLister.createWithAfter(afterJavaScript));
            addNode(node);
        }

        // 创建连接
        createLink(currentNode, node, expr);
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;
    }

    /**
     * 创建SQL节点
     * @param sourceName 数据源名称
     * @param sql        SQL
     * @return           节点
     */
    public FlowDefinition toSqlWithAfter(String name, String sourceName, String sql, String afterJavaScript) {
        return toSqlWithAfter(name, sourceName, sql, "", afterJavaScript);
    }

    /**
     * 创建js节点
     * @param name  名称
     * @param js    js内容
     * @param expr  线表达式
     * @return
     */
    public FlowDefinition toJs(String name, String js, String expr) {
        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(String.format("node-%s", StringUtils.getUUID()));
            node.setName(name);
            node.setAuto(true);
            node.setState(NodeStateEnum.COMPLETED.getValue());
            node.setDebug(false);
            node.setType(NodeTypeEnum.TASK.getValue());
            node.setExecute(ExecuteDefinition.createJsScript(js));
            addNode(node);
        }

        // 创建连接
        createLink(currentNode, node, expr);
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;
    }

    /**
     * 创建js节点
     * @param name  名称
     * @param js    js内容
     * @return
     */
    public FlowDefinition toJs(String name, String js) {
        // 创建节点
        return toJs(name, js, "");
    }

    /**
     * 创建分支L节点
     * @return           节点
     */
    public FlowDefinition toDecision(String name) {

        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(String.format("node-%s", StringUtils.getUUID()));
            node.setName(name);
            node.setType(NodeTypeEnum.DECISION.getValue());
            node.setExecute(new ExecuteDefinition());
            addNode(node);
        }
        // 创建连接
        createLink(currentNode, node, "");
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;
    }

    public FlowDefinition toNodeWithAfter(NodeTypeEnum type, String name, String afterJavascript) {
        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(String.format("node-%s", StringUtils.getUUID()));
            node.setName(name);
            node.setType(type.getValue());
            node.setExecute(new ExecuteDefinition());
            node.setExecute(ExecuteDefinition.createJsScript(afterJavascript));
            addNode(node);
        }
        // 创建连接
        createLink(currentNode, node, "");
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;
    }

    public FlowDefinition toNode(NodeTypeEnum type, String name) {
       return toNode(type, name, "");
    }

    public FlowDefinition toNode(NodeTypeEnum type, String name, String expr) {
        // 创建节点
        NodeDefinition node = nodeByName(name);
        if (node == null) {
            node = new NodeDefinition();
            node.setId(String.format("node-%s", StringUtils.getUUID()));
            node.setName(name);
            node.setType(type.getValue());
            node.setExecute(new ExecuteDefinition());
            addNode(node);
        }
        // 创建连接
        createLink(currentNode, node, expr);
        // 设置当前节点
        this.currentNode = node;
        // 返回
        return this;
    }


    public FlowDefinition resetCurrentNode(String name) {
        NodeDefinition nodeDefinition = nodeByName(name);
        if (nodeDefinition == null) {
            throw new RuntimeException("节点找不到，名称：" + name);
        }
        this.currentNode = nodeDefinition;
        return this;
    }

    @SneakyThrows
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(this);
    }



    private void createLink(NodeDefinition from, NodeDefinition to, String expr) {
        // 创建link
        NodeLink nodeLink = new NodeLink();
        nodeLink.setId(String.format("link-%s", StringUtils.getUUID()));
        nodeLink.setName(String.format("%s->%s", from.getName(), to.getName()));
        nodeLink.setFrom(from.getId());
        nodeLink.setTo(to.getId());
        if (StringUtils.isNotEmpty(expr)) {
            nodeLink.setConditions(ConditionDefinition.createDecisionCondition(expr));
        }
        this.nodeLinks.add(nodeLink);
    }

}
