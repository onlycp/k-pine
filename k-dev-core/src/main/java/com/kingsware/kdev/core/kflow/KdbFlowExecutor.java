package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KFlowMessage;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.handler.KResultHandlers;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * KDB流程执行器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/17 5:57 下午
 */
@Slf4j
public class KdbFlowExecutor {

    private static KdbFlowExecutor instance;

    /** 公共模型列表定义 **/
    private static Map<String, ModelFieldDefine> publicModelFieldMap = new HashMap<>();

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
        initPublicModelField();
    }


    /**
     * 公共的属性列定义
     */
    private void initPublicModelField() {
        publicModelFieldMap.put("whenCreated", new ModelFieldDefine("whenCreated","创建时间" ,"Timestamp", "time",  DateUtils.DATE_TIME));
        publicModelFieldMap.put("whenModified", new ModelFieldDefine("whenModified","修改时间","Timestamp", "time", DateUtils.DATE_TIME));
        publicModelFieldMap.put("balanceUpdateTime", new ModelFieldDefine("balanceUpdateTime","余额更新时间","Timestamp","time", DateUtils.DATE_TIME));
        publicModelFieldMap.put("createAccountTime", new ModelFieldDefine("createAccountTime","账户建立时间","Timestamp","time", DateUtils.DATE));
        publicModelFieldMap.put("cancelAccountTime", new ModelFieldDefine("cancelAccountTime","账户销户时间","Timestamp", "time", DateUtils.DATE));
    }


    /**
     *  执行流程
     * @param flowId        流程id
     * @param params       参数对
     * @param context      上下文信息
     * @return             执行结果
     */
    public KdbFlowResult execute(String flowId, Map<String, Object> params, KFlowContext context) {

        // 流程参数
        KdbArgv argv = new KdbArgv();
        // 设置流程id
        argv.setFlowID(flowId);
        // 设备流程参数
        if (params.containsKey("page") && params.containsKey("pageSize")) {
            int page = Integer.parseInt(params.getOrDefault("page", "1").toString());
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10").toString());
            params.put("start", (page-1)*pageSize + "") ;
            params.put("limit", pageSize + "");
        }
        if (params.containsKey("pageQuery")) {
            params.put("pageQuery", params.getOrDefault("pageQuery", false).toString());
        }
        argv.getVariables().putAll(params);
        // 将入系统变量
        argv.getVariables().putAll(context.getSystemContext());
        // 执行流程
        KdbRet<String> ret = DB.kdbApi().executeFlow(argv);
        // 如果失败
        KdbFlowResult result = new KdbFlowResult();
        if (ret.getErrorCode() != 0) {
            result.setType(KFlowConstant.RESULT_JSON);
            result.setData(new ErrorResult(ret.getMessage() == null ? "流程处理失败": ret.getMessage()));
        }
        else if (StringUtils.isNotEmpty(ret.getResponseBody())){
            publicModelFieldMap.forEach((k, v) -> {
                if (!context.getModelFieldDefineMap().containsKey(k)) {
                    context.getModelFieldDefineMap().put(k, v);
                }
            });
            KFlowMessage message = FlowUtils.getHandlerName(ret.getResponseBody());
            result = KResultHandlers.getInstance().getHandler(message.getHandlerName()).parser(message.getData(), context);
        }
        return result;
    }


}
