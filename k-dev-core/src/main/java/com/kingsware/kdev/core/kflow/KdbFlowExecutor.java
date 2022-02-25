package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.NumberUtils;
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
    public Object execute(String flowId, Map<String, Object> params, KFlowContext context) {

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
        context.getSystemContext().forEach((k, v)-> argv.getVariables().put("sys." + k, v));
        // 执行流程
        KdbRet<String> ret = DB.kdbApi().executeFlow(argv);
        // 如果失败
        Object result = null;
        if (ret.getErrorCode() != 0) {
            log.error("kdb响应错误:" + ret.getMessage());
            result = new ErrorResult(ret.getMessage() == null ? "流程处理失败": ret.getMessage());
        }
        else if (ret.getResponseBody() == null) {
            result = null;
        }
        // 如果返回的数字，那么直接将结果返回给前端
        else if (NumberUtils.isParsable(ret.getResponseBody())) {
            result = Long.parseLong(ret.getResponseBody());
        }
         else if (ret.getResponseBody().startsWith("{") && ret.getResponseBody().endsWith("}")) {
            result = JsonUtil.toMap(ret.getResponseBody());
        }
        // 如果是数组
        else if (ret.getResponseBody().startsWith("[") && ret.getResponseBody().endsWith("]")) {
            result = parseObject(ret.getResponseBody());
        }
        else if (ret.getResponseBody().startsWith("list|")) {
            String text = ret.getResponseBody().replaceAll("list\\|", "");
            result = parseList(text);
        }
        else if (ret.getResponseBody().startsWith("object|")) {
            String text = ret.getResponseBody().replaceAll("object\\|", "");
            result = parseObject(text);
        }
        else {
            if (ret.getResponseBody().startsWith("error|")) {
                result = new ErrorResult(ret.getResponseBody().replaceAll("error\\|", ""));
            }
            else {
                result = new TipResult(ret.getResponseBody());
            }
        }
        // 处理并返回结果
        publicModelFieldMap.forEach((k, v) -> {
            if (!context.getModelFieldDefineMap().containsKey(k)) {
                context.getModelFieldDefineMap().put(k, v);
            }
        });

        return processData(result, context);
    }

    /**
     * 转向api格式
     * @param result 源名称
     * @return
     */
    public Object toApiResult(Object result) {
        // 返回前端
        BaseRet<?> ret = new BaseRet<>();
        if (result instanceof TipResult) {
            ret = BaseRet.successMessage(((TipResult) result).getMessage());
        }
        else if (result instanceof ErrorResult) {
            ret = BaseRet.failMessage(((ErrorResult) result).getMessage());
        }
        else if (result instanceof Map && ((Map<?, ?>) result).containsKey("RET_TYPE_KEY")) {

        }
        else {
            ret = BaseRet.success(result);
        }
        return ret;
    }

    /**
     * 转换处理列表
     * @param text
     * @return
     */
    private List<Map<String, Object>> parseList(String text) {
        List<Map<String, Object>> mapObjectList = new ArrayList<>();
        List<Map> list = JsonUtil.snakeCaseToListBean(text, Map.class);
        for (Map<?,?> map: list) {
            Map<String, Object> tmpMap = new HashMap<>();
            map.forEach((k, v) -> tmpMap.put(StringUtils.lineToHump(k.toString().toLowerCase()), v));
            mapObjectList.add(tmpMap);
        }
        return mapObjectList;
    }


    /**
     * 转换处理列表
     * @param text
     * @return
     */
    private Object parseObject(String text) {
        List<Map<String, Object>> mapObjectList = parseList(text);
        if (!mapObjectList.isEmpty()) {
            return mapObjectList.get(0);
        }
        return null;
    }


    /**
     *  处理结果
     *  1. 全部转为骆峰
     *  2. 模型处理
     * @param object
     * @return
     */
    private Object processData(Object object, KFlowContext context) {
        if (object == null ) {
            return null;
        }
        if (object instanceof Map) {
            Map<Object, Object> resultMap = new HashMap<>();
            for (Map.Entry<?,?> entry: ((Map<?, ?>) object).entrySet()) {
                String humpKey = StringUtils.lineToHump(entry.getKey().toString());
                Object value = processData(entry.getValue(), context);
                // 如果不是map和list，再进行属性处理
                if (!(value instanceof Map) && !(value instanceof Collection) && value!= null && StringUtils.isNotEmpty(value.toString()) ) {
                    if (context.getModelFieldDefineMap().containsKey(humpKey)) {
                        ModelFieldDefine modelFieldDefine = context.getModelFieldDefineMap().get(humpKey);
                        if ("Timestamp".equals(modelFieldDefine.getType())) {
                            String strValue = value.toString();
                            if (NumberUtils.isParsable(strValue)) {
                                value = DateUtils.formatDate(new Date(Long.parseLong(strValue)), modelFieldDefine.getFormatPattern());
                            }
                        }
                    }
                }
                resultMap.put(humpKey, value);
            }
            return resultMap;
        }
        else if (object instanceof Collection) {
            List<Object> resultList = new ArrayList<>();
            for (Object item: (Collection)object) {
                resultList.add(processData(item, context));
            }
            return resultList;
        }
        else {
            return object;
        }
    }




}
