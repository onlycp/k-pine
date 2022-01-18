package com.kingsware.kdev.core.orm.kdb;

import java.util.Map;

/**
 * KDB流程执行器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/17 5:57 下午
 */
public class KdbFlowExecutor {

    private static KdbFlowExecutor instance;

    public static KdbFlowExecutor getInstance() {
        if (instance == null) {
            synchronized (KdbFlowExecutor.class) {
                if (instance == null) {
                    instance = new KdbFlowExecutor();
                }
            }
        }
        return instance;
    }

    private KdbFlowExecutor() {
    }

    /**
     *  执行流程
     * @param flowId        流程id
     * @param params       参数对
     * @return             执行结果
     */
    public Object execute(String flowId, Map<String, Object> params) {
        return null;
    }
}
