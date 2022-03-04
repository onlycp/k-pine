package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KFlowMessage;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.NumberUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.*;

/**
 * 流程专用工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/3 3:43 下午
 */
public class FlowUtils {

    /**
     * 转换处理列表
     * @param text
     * @return
     */
    public static Map<String, Object> parseList2Object(String text) {
        List<Map<String, Object>> mapObjectList = parseList(text);
        if (!mapObjectList.isEmpty()) {
            return mapObjectList.get(0);
        }
        return null;
    }

    /**
     * 转换处理列表
     * @param text
     * @return
     */
    public static List<Map<String, Object>> parseList(String text) {
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
     *  处理结果
     *  1. 全部转为骆峰
     *  2. 模型处理
     * @param object 待处理的结果
     * @return
     */
    public static Object processData(Object object, KFlowContext context) {
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

    /**
     * 获取处理器名称
     * @param responseBody  响应体
     * @return    处理器名称, 如果不存在，返回null
     */
    public static KFlowMessage getHandlerName(String responseBody) {
        KFlowMessage message =  new KFlowMessage();
        if (NumberUtils.isParsable(responseBody)) {
            message.setHandlerName("number");
            message.setData(responseBody);
        }
        else if (responseBody.startsWith(KFlowConstant.OBJECT_START) && responseBody.endsWith(KFlowConstant.OBJECT_END)) {
            message.setHandlerName("object");
            message.setData(responseBody);
        }
        // todo 此处不应该这样处理，应该是直接返回list
        else if (responseBody.startsWith(KFlowConstant.ARRAY_START) && responseBody.endsWith(KFlowConstant.ARRAY_END)) {
            message.setHandlerName("list2object");
            message.setData(responseBody);
        }
        else if (responseBody.startsWith(KFlowConstant.LIST_FLAG)) {
            String text = responseBody.substring(KFlowConstant.LIST_FLAG.length());
            message.setHandlerName("list");
            message.setData(text);

        }
        else if (responseBody.startsWith(KFlowConstant.OBJECT_FLAG)) {
            String text = responseBody.substring(KFlowConstant.OBJECT_FLAG.length());
            message.setHandlerName("object");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.EXCEL_FLAG)) {
            String text = responseBody.substring(KFlowConstant.EXCEL_FLAG.length());
            message.setHandlerName("excel");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.ERROR_FLAG)) {
            String text = responseBody.replace(KFlowConstant.ERROR_FLAG, "");
            message.setHandlerName("error");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.MESSAGE_FLAG)) {
            String text = responseBody.replace(KFlowConstant.MESSAGE_FLAG, "");
            message.setHandlerName("message");
            message.setData(text);
        }
        else {
            message.setHandlerName("simple");
            message.setData(responseBody);
        }
        return message;
    }

    /**
     * 转向api格式
     * @param result 源名称
     * @return
     */
    public static BaseRet<?> toJsonResult(Object result) {
        // 返回前端
        BaseRet<?> ret = new BaseRet<>();
        if (result instanceof MessageResult) {
            ret = BaseRet.successMessage(((MessageResult) result).getMessage());
        }
        else if (result instanceof ErrorResult) {
            ret = BaseRet.failMessage(((ErrorResult) result).getMessage());
        }
        else {
            ret = BaseRet.success(result);
        }
        return ret;
    }

}
