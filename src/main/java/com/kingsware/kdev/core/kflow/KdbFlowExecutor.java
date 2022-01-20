package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.handler.KFlowResultHandlerFactory;
import com.kingsware.kdev.core.kflow.handler.KObjectHandler;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.NumberUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
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
        KdbRet<String> ret = DB.kdbApi().executeFlow(argv);
        // 如果失败
        if (ret.getErrorCode() != 0) {
            return new ErrorResult(ret.getResponseBody());
        }
        // 如果body为空，则直接返回null即可
        if (StringUtils.isEmpty(ret.getResponseBody())) {
            return null;
        }
        // 如果返回的数字，那么直接将结果返回给前端
        else if (NumberUtils.isParsable(ret.getResponseBody())) {
            return Long.parseLong(ret.getResponseBody());
        }
        // 如果是数组
        else if (ret.getResponseBody().startsWith("[") && ret.getResponseBody().endsWith("]")) {
            // 执行流程
            List<Map<String, Object>> mapObjectList = new ArrayList<>();
            List<Map> list = JsonUtil.snakeCaseToListBean(ret.getResponseBody(), Map.class);
            for (Map<?,?> map: list) {
                Map<String, Object> tmpMap = new HashMap<>();
                map.forEach((k, v) -> tmpMap.put(StringUtils.lineToHump(k.toString().toLowerCase()), v));
                mapObjectList.add(tmpMap);
            }

            // 数据转换
            String handleClass = context.getHandleClass();
            if (StringUtils.isEmpty(handleClass)) {
                handleClass = KObjectHandler.class.getName();
            }
            // 调用结果处理器
            return KFlowResultHandlerFactory.getHandler(handleClass).execute(mapObjectList);
        }
        else {
            return new TipResult(ret.getResponseBody());
        }


    }


}
