package com.kingsware.kdev.core.kflow.converter;

/**
 * KDB数据转换器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 2:27 下午
 */
public interface KdbFlowFieldConverter {

    /**
     * 处理数据转换
     * @param src   源
     * @return      处理后
     */
    Object convert(Object src);
}
