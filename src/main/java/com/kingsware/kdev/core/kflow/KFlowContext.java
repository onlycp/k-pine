package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.kflow.converter.KdbFlowFieldConverter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程环境变量
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 2:23 下午
 */
@Data
public class KFlowContext {

    /** 数据处理器 **/
    private final Map<String, KdbFlowFieldConverter> converterMap = new HashMap<>();
    /** 当前系统变量 **/
    private final Map<String, Object> systemContext = new HashMap<>();
}
