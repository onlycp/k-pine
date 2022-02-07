package com.kingsware.kdev.core.kflow.handler;

import java.util.List;
import java.util.Map;

/**
 * 流程执行器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:16 下午
 */
public interface KFlowResultHandler {

    /**
     *  执行流程
     * @param list         流程id
     * @return             执行结果
     */
    Object execute(List<Map<String, Object>> list);
}
