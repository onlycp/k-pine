package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.cache.logic.LogicFlowManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.DebugNode;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KFlowMessage;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.handler.KResultHandlers;
import com.kingsware.kdev.core.kflow.tcp.TcpClientContext;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.exception.TransactionException;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.orm.kdb.TransactionManager;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public KdbFlowResult execute(String flowId, String subFlowIds, Map<String, Object> params, KFlowContext context, boolean debug, boolean sync, List<DebugNode> debugger) {
        long t1 = System.currentTimeMillis();
        String statusMessage = I18n.t("common.fail", "失败") ;
        KdbFlowResult result = new KdbFlowResult();
        boolean executeResult = true;
        // 生成会话id
        String sessionId = null;
        String sessionArgv = null;
        // 只有是网页请求发起的才生成
        if (KClientContext.getContext() != null && StringUtils.isNotEmpty(KClientContext.getContext().getToken())) {
            String windowId = params.getOrDefault("_logWindowId", "system").toString();
            String token = KClientContext.getContext().getToken();
            sessionArgv = token + ";" + windowId;
            sessionId = StringUtils.getUUID();
        }
        // 流程参数
        KdbArgv argv = new KdbArgv();
        try {

            // 设置流程id
            argv.setFlowID(flowId);
            // 设置会话id
            if (StringUtils.isNotEmpty(sessionId)) {
                argv.setSessionID(sessionId);
                TcpClientContext.getInstance().putSession(sessionId, sessionArgv);
            }

            // 调试参数
            argv.setDebugger(debugger);
            String saas = SpringContext.getProperties("app.is-saas", "false");
            if ("true".equals(saas)) {
                if (KClientContext.getContext() != null && KClientContext.getContext().getUserInfo() != null && StringUtils.isNotEmpty(KClientContext.getContext().getUserInfo().getSysUnitIds())) {
                    argv.setInstID(KClientContext.getContext().getUserInfo().getSysUnitIds());
                }

            }
            // 设备流程参数
            if (params.containsKey("page") && (params.containsKey("pageSize") || params.containsKey("perPage"))) {
                try {
                    int page = Integer.parseInt(params.getOrDefault("page", "1").toString());
                    int pageSize = Integer.parseInt(params.getOrDefault("pageSize", params.getOrDefault("perPage", "10")).toString());
                    params.put("start", (page - 1) * pageSize + "");
                    params.put("limit", pageSize + "");
                    params.put("end", page * pageSize);
                    params.put("pageSize", pageSize);
                }
                catch (Exception e) {
                    throw BusinessException.serviceThrow(I18n.t("KdbFlowExecutor.params.fail", "查询参数输入不合法"));
                }

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
                for (String s : arr) {
                    afterArr.add("'" + s + "'");
                }
                String sql = "select in_argv from sys_logic_flow where flow_id in (" + StringUtils.joinToString(afterArr, ",") + ")";
                List<String> inArgvList = DB.findSingleAttributeList(String.class, sql);
                for (String string : inArgvList) {
                    FlowUtils.handleInArgv(argv.getVariables(), string);
                }
            }
            if (LogicFlowManager.getInstance().isTranCtrl(argv.getFlowID())) {
                // 发起事务
                TransactionManager.getInstance().begin(60, null, flowId);
                argv.setTransactionUuid(TransactionManager.getInstance().getTransactionCache().getId());
            }

            // 执行流程
            KdbRet<String> ret = DB.kdbApi().executeFlow(argv, debug, sync);
            if (LogicFlowManager.getInstance().isTranCtrl(argv.getFlowID())) {
                if (ret.getErrorCode() == 0) {
                    // 提交事务
                    TransactionManager.getInstance().commit();
                }
                else {
                    try {
                        TransactionManager.getInstance().rollback();
                    } catch (TransactionException ex) {
                        log.warn("error", ex);
                    }
                }

            }
            if (ret.getErrorCode() != 0) {
                result.setType(KFlowConstant.RESULT_JSON);
                if (StringUtils.isNotEmpty(ret.getStackTrace()) && ret.getStackTrace().contains("#@") && ret.getStackTrace().contains("@#")) {
                    int startIndex = ret.getStackTrace().indexOf("#@") + 2;
                    int endIndex = ret.getStackTrace().indexOf("@#");
                    String businessMessage = ret.getStackTrace().substring(startIndex, endIndex);
                    result.setData(new ErrorResult(businessMessage));
                    if (LogicFlowManager.getInstance().isTranCtrl(argv.getFlowID())) {
                        try {
                            TransactionManager.getInstance().rollback();
                        } catch (TransactionException ex) {
                            log.warn("error", ex);
                        }
                    }

                }
                else {
                    result.setData(new ErrorResult(ret.getMessage() == null ? I18n.t("KdbFlowExecutor.execute.fail", "流程处理失败")  : ret.getMessage()));
                }

            } else if (StringUtils.isNotEmpty(ret.getResponseBody())) {
                if (argv.getFlowID().equalsIgnoreCase("a20fd82c126947f9ab3b599001df6126")) {
                    log.info("用时：7");
                }
                KFlowMessage message = FlowUtils.getHandlerName(ret.getResponseBody());
                result = KResultHandlers.getInstance().getHandler(message.getHandlerName()).parser(message.getData(), context);
                if (argv.getFlowID().equalsIgnoreCase("a20fd82c126947f9ab3b599001df6126")) {
                    log.info("用时：8");
                }
            } else {
                result.setType(KFlowConstant.RESULT_JSON);
                result.setData(null);
            }
            result.setLog(ret.getKlog());
            result.setExceptionStack(ret.getStackTrace());
            return result;
        } catch (OrmDbException ormDbException) {
            if (LogicFlowManager.getInstance().isTranCtrl(argv.getFlowID())) {
                try {
                    TransactionManager.getInstance().rollback();
                } catch (TransactionException ex) {
                    log.warn("error", ex);
                }
            }
            result.setType(KFlowConstant.RESULT_JSON);
            result.setData(new ErrorResult(ormDbException.getMessage() == null ? I18n.t("KdbFlowExecutor.execute.fail", "流程处理失败")  : ormDbException.getMessage()));
            result.setLog(ormDbException.getKlog());
            result.setExceptionStack(ormDbException.getExceptionTrace());
            return result;
        }
        finally {
            if (StringUtils.isNotEmpty(sessionId)) {
                TcpClientContext.getInstance().removeSession(sessionId);
            }

        }
    }


    /**
     *  执行流程
     * @param flowId        流程id
     * @param params       参数对
     * @param context      上下文信息
     * @return             执行结果
     */
    public KdbFlowResult execute(String flowId, String subFlowIds, Map<String, Object> params, KFlowContext context, boolean debug, boolean sync) {
        return this.execute(flowId, subFlowIds, params, context, debug, sync, new ArrayList<>());
    }





}
