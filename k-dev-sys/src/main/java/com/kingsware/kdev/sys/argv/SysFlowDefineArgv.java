package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新增流程实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFlowDefineArgv extends BasePageArgv {

    @Data
     public static class Node {
        /** id **/
        private String id;
        /** 名称 **/
        private String label;
        /** 节点类型 **/
        private String type;
        /** 子流程流程id **/
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
        private Map<String, Object> exeData = new HashMap<>();
    }

    @Data
    public static class Link {
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
    /** 默认数据源 **/
    private String defaultSourceName;
    /** 节点列表 **/
    private List<Node> nodes = new ArrayList<>();
    /** 连续 **/
    private List<Link> links = new ArrayList<>();
    /** 是否开始事务 **/
    private String tranCtrl;
    /** 是否V3或以上版本 **/
    private boolean v3;

}
