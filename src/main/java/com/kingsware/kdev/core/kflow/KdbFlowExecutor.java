package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.kflow.handler.KFlowResultHandlerFactory;
import com.kingsware.kdev.core.kflow.handler.KObjectHandler;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.List;
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
     * @param context      上下文信息
     * @return             执行结果
     */
    public Object execute(String flowId, Map<String, Object> params, KFlowContext context) {
        // 流程参数
        KdbArgv argv = new KdbArgv();
        // 设置流程id
        argv.setFlowID(flowId);
        // 设备流程参数
        argv.getVariables().putAll(params);
        // 将入系统变量
        context.getSystemContext().forEach((k, v)-> argv.getVariables().put("sys." + k, v));
        // 执行流程
        List<Map<String, Object>> mapObjectList = DB.kdbApi().executeFlow(argv);
        // 数据转换
        String handleClass = context.getHandleClass();
        if (StringUtils.isEmpty(handleClass)) {
            handleClass = KObjectHandler.class.getName();
        }
        // 调用结果处理器
        return KFlowResultHandlerFactory.getHandler(handleClass).execute(mapObjectList);
    }


}
