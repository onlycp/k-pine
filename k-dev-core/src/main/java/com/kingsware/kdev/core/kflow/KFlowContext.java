package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.kflow.converter.KdbFlowFieldConverter;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
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
    /** 模型列处理器 **/
    private final Map<String, ModelFieldDefine> modelFieldDefineMap = new HashMap<>();
    /** 处理类 **/
    private String handleClass;

    public static KFlowContext createBaseContext() {

        KFlowContext context = new KFlowContext();
        // 处理系统变量
        context.getSystemContext().put("who",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getId() : "");
        context.getSystemContext().put("username",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getUsername() : "");
        context.getSystemContext().put("when", DateUtils.getNow());
        context.getSystemContext().put("uuid", StringUtils.getUUID());
        return context;
    }
}
