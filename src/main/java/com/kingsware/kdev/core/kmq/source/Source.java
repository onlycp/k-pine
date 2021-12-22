package com.kingsware.kdev.core.kmq.source;

import java.util.Map;

/**
 * Source基类接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 6:04 下午
 */
public interface Source {

    /**
     * 初始化配置
     * @param context   上下文
     */
    void configure(Map<String, String> context);

    /**
     * 处理事件
     * @return 处理状态
     */
    int process();
}
