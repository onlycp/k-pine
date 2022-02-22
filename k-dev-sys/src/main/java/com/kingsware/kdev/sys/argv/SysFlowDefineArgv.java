package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
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
public class SysFlowDefineArgv extends BasePageArgv {

    @Data
     public static class Node {
        /** id **/
        private String id;
        /** 名称 **/
        private String label;
        /** 节点类型 **/
        private String type;
        /** 执行类型 **/
        private String executeType;
        /** 数据源名称 **/
        private String sourceName;
        /** 脚本内容 **/
        private String content;
        /** 执行内容 **/
        private String afterContent;
    }

    @Data
    public static class Link {
        /** id **/
        private String id;
        /** 名称 **/
        private String label;
        /** 起始节点 **/
        private String source;
        /** 到达节点 **/
        private String target;
        /** 表达式 **/
        private String expr;
    }

    /** 流程id **/
    private String id;
    /** 名称 **/
    private String name;
    /** 描述 **/
    private String description = "";
    /** 节点列表 **/
    private List<Node> nodes = new ArrayList<>();
    /** 连续 **/
    private List<Link> links = new ArrayList<>();

}
