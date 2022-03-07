package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.context.KClientContext;
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

    /** 当前系统变量 **/
    private final Map<String, Object> systemContext = new HashMap<>();
    /** 处理类 **/
    private String handleClass;
    /** 输入参数定义 **/
    private String inArgv;
    /** 输出参数定义 **/
    private String outArgv;

    public static KFlowContext createBaseContext(String inArgv, String outArgv) {

        KFlowContext context = new KFlowContext();
        Map<String, Object> sysMap = new HashMap<>();
        // 处理系统变量
        sysMap.put("who",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getId() : "");
        sysMap.put("username",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getUsername() : "");
        sysMap.put("when", DateUtils.getNow());
        sysMap.put("uuid", StringUtils.getUUID());
        context.getSystemContext().put("sys", sysMap);
        // 设置输入参数
        context.inArgv = inArgv;
        context.outArgv = outArgv;
        return context;
    }
}
