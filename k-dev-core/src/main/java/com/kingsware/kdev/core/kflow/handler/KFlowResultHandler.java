package com.kingsware.kdev.core.kflow.handler;

import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;

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
     * @param responseBody  kdb响应字符串
     * @param context  流程上下文
     * @return  结果
     */
    KdbFlowResult parser(String responseBody, KFlowContext context);

    /**
     * 处理器名称
     * @return  返回名称
     */
    String name();
}
