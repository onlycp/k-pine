package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KFlowMessage;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.handler.KResultHandlers;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
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


    public static KdbFlowExecutor getInstance() {
        if (instance == null) {
            instance = new KdbFlowExecutor();
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
    public KdbFlowResult execute(String flowId, String subFlowIds, Map<String, Object> params, KFlowContext context, boolean debug) {

        long t1 = System.currentTimeMillis();
        String statusMessage = "失败";
        KdbFlowResult result = new KdbFlowResult();
        // 流程参数
        KdbArgv argv = new KdbArgv();
        try {

            // 设置流程id
            argv.setFlowID(flowId);
            String saas =  SpringContext.getProperties("app.is-saas", "false");
            if ("true".equals(saas)) {
                if (KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null && StringUtils.isNotEmpty(KClientContext.getContext().getUserInfo().getSysUnitId())) {
                    argv.setInstID(KClientContext.getContext().getUserInfo().getSysUnitId());
                }

            }
            // 设备流程参数
            if (params.containsKey("page") && (params.containsKey("pageSize") || params.containsKey("perPage"))) {
                int page = Integer.parseInt(params.getOrDefault("page", "1").toString());
                int pageSize = Integer.parseInt(params.getOrDefault("pageSize", params.getOrDefault("perPage", "10")).toString());
                params.put("start", (page-1)*pageSize + "") ;
                params.put("limit", pageSize + "");
            }
            if (params.containsKey("pageQuery")) {
                params.put("pageQuery", params.getOrDefault("pageQuery", false).toString());
            }
            argv.getVariables().putAll(params);
            // 将入系统变量
            argv.getVariables().putAll(context.getSystemContext());
            // 将系统配置加入进来
            // argv.getVariables().putAll(SpringContext.getProperties());
            // 通过输入模型处理输入参数
            FlowUtils.handleInArgv(argv.getVariables(), context.getInArgv());
            // 获取子流程
            if (StringUtils.isNotEmpty(subFlowIds)) {
                String[] arr = subFlowIds.split(",");
                // 拼接in ids
                List<String> afterArr = new ArrayList<>();
                for (String s: arr) {
                    afterArr.add("'" +  s + "'");
                }
                String sql = "select in_argv from sys_logic_flow where flow_id in (" + StringUtils.joinToString(afterArr, ",") + ")";
                List<String> inArgvList = DB.findSingleAttributeList(String.class, sql);
                for (String string: inArgvList) {
                    FlowUtils.handleInArgv(argv.getVariables(), string);
                }
            }
            // 执行流程
            KdbRet<String> ret = DB.kdbApi().executeFlow(argv, debug);

            if (ret.getErrorCode() != 0) {
                result.setType(KFlowConstant.RESULT_JSON);
                result.setData(new ErrorResult(ret.getMessage() == null ? "流程处理失败": ret.getMessage()));
            }
            else if (StringUtils.isNotEmpty(ret.getResponseBody())){
                KFlowMessage message = FlowUtils.getHandlerName(ret.getResponseBody());
                result = KResultHandlers.getInstance().getHandler(message.getHandlerName()).parser(message.getData(), context);
            }
            else {
                result.setType(KFlowConstant.RESULT_JSON);
                result.setData(null);
            }
            result.setLog(ret.getKlog());
            return result;
        }
        catch (OrmDbException ormDbException) {

            result.setType(KFlowConstant.RESULT_JSON);
            result.setData(new ErrorResult(ormDbException.getMessage() == null ? "流程处理失败": ormDbException.getMessage()));
            result.setLog(ormDbException.getKlog());
            return result;
        }
        finally {
//            // todo 临时应对万达poc处理， 后面要删除掉
//            if (argv.getVariables().containsKey("withLogTableId")) {
//                try {
//                    long t2 = System.currentTimeMillis();
//                    Map<String, Object> logMap = new HashMap<>();
//                    logMap.put("pocTableId", argv.getVariables().get("withLogTableId"));
//                    logMap.put("taskTime", DateUtils.formatDate(new Date(t1), DateUtils.DATE_TIME));
//                    logMap.put("taskStatus", result.getData() instanceof ErrorResult ? "失败": "成功");
//                    logMap.put("taskUseTime", t2-t1);
//                    KdbArgv logArgv =JsonUtil.toBean(JsonUtil.toJson(argv), KdbArgv.class);
//                    logArgv.setFlowID("f4c29a689b9b4e7e9a49e5967b68d67d");
//                    logArgv.getVariables().putAll(logMap);
//                    DB.kdbApi().executeFlow(logArgv, debug);
//                }
//                catch (Exception e) {
//                    // 什么都不用管
//                }
//
//            }



        }

    }


}
