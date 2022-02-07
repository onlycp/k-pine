package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

/**
 * 节点监听器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/21 5:34 下午
 */
@Data
public class FlowNodeLister {
    /** 前 **/
    private ExecuteDefinition before = new ExecuteDefinition();
    /** 后 **/
    private ExecuteDefinition after = new ExecuteDefinition();

    /**
     * 创建监听器
     * @param javascript   脚本
     * @return  返回监听器
     */
    public static FlowNodeLister createWithAfter(String javascript) {
        FlowNodeLister lister = new FlowNodeLister();
        lister.setAfter(ExecuteDefinition.createJsScript(javascript));
        return lister;
    }

}
