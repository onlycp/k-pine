package com.kingsware.kdev.core.kflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.config.ConfigManager;
import com.kingsware.kdev.core.cache.config.SysConfigInfo;
import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.bean.ComplexValue;
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
    public static Object processData(Object object, KFlowContext context, JsonNode jsonNode) {
        if (object == null ) {
            return null;
        }
        if (object instanceof Map) {
            Map<Object, Object> resultMap = new HashMap<>();
            JsonNode childNode = jsonNode != null ? jsonNode.get("properties") : null;
            for (Map.Entry<?,?> entry: ((Map<?, ?>) object).entrySet()) {
                String humpKey = StringUtils.lineToHump(entry.getKey().toString());
                JsonNode pNode = childNode != null ? childNode.get(humpKey) : null;
                Object value = processData(entry.getValue(), context, pNode);
                // 如果不是map和list，再进行属性处理
                if (!(value instanceof Map) && !(value instanceof Collection) && value!= null && StringUtils.isNotEmpty(value.toString()) ) {
                    value = parserWithSchema(pNode, value);
                }
                // 如果是复合值，需要将label和value同时返回
                if (value instanceof ComplexValue) {
                    ComplexValue complexValue = (ComplexValue)value;
                    // 原始值
                    resultMap.put(humpKey, complexValue.getValue());
                    // 标签
                    resultMap.put(String.format("%s$label", humpKey), complexValue.getLabel());
                }
                else {
                    resultMap.put(humpKey, value);
                }


            }
            return resultMap;
        }
        else if (object instanceof Collection) {
            List<Object> resultList = new ArrayList<>();
            // 获取jsonNode
            JsonNode childNode = jsonNode != null ? jsonNode.get("items") : null;
            for (Object item: (Collection<?>)object) {
                resultList.add(processData(item, context, childNode));
            }
            return resultList;
        }
        else {
            return object;
        }
    }

    /**
     * 处理输入参数
     * @param variables 变量
     * @param inArgv    输入参数定义
     */
    public static void handleInArgv(Map<String, Object> variables, String inArgv) {
        // 根节点定义
        JsonNode rootNode = JsonUtil.toTree(inArgv);
        // 处理
        handleNodeValue(variables, rootNode);
    }

    /**
     * 解析节点值
     * @param variable  变量值
     * @param jsonNode  节点
     * @return          返回值
     */
    @SuppressWarnings("unchecked")
    private static Object handleNodeValue(Object variable, JsonNode jsonNode) {
        if (jsonNode == null) {
            return variable;
        }
        // 获取类型
        JsonNode typeNode = jsonNode.get("type");
        if (typeNode == null) {
            return variable;
        }
        String type = typeNode.asText();
        // 定义返回值
        Object retValue = variable;
        // 根据不同类型进行处理
        // object，对应的是值里的map
        if ("object".equalsIgnoreCase(type)) {

            Map<String, Object> map = (Map<String, Object>) variable;
            // 获取属性
            JsonNode propertiesNode = jsonNode.get("properties");
            Iterator<String> propertyNames = propertiesNode.fieldNames();
            // 新值
            Map<String, Object> newValueMap = new HashMap<>();
            while (propertyNames.hasNext()) {
                String pName = propertyNames.next();
                JsonNode pNode = propertiesNode.get(pName);
                // 只有值不存在的时候，才会使用默认值填充
                if (!map.containsKey(pName)) {
                    // 获取值
                    Object entryValue = handleNodeValue(null, pNode);
                    newValueMap.put(pName, entryValue);
                }

            }
            // 覆盖原先的值
            map.putAll(newValueMap);
        }
        // 列表
        else if ("array".equalsIgnoreCase(type)) {
            // 获取list定义
            JsonNode itemNode = jsonNode.get("items");
            // 转为list
            List<Object> objects = JsonUtil.toListBean(JsonUtil.toJson(variable), Object.class);
            if (objects != null) {
                for (int i = 0; i < objects.size() ; i++) {
                    Object itemValue = handleNodeValue(objects.get(i), itemNode);
                    objects.set(i, itemValue);
                }
            }
            retValue = objects;
        }
        else {
            // 如果此有值
            JsonNode externNode = jsonNode.get("extern");
            if (externNode != null) {
                JsonNode defaultNode = externNode.get("default");
                if (defaultNode != null) {
                    // 获取默认值
                    String defaultValue = defaultNode.asText().trim();
                    if (defaultValue.startsWith("${") && defaultValue.endsWith("}")) {
                        String key = defaultValue.replace("${", "").replaceAll("}", "").trim();
                        String configDefaultValue = null;
                        if (key.contains("|")) {
                            configDefaultValue = key.split("\\|")[1].trim();
                        }
                        SysConfigInfo configInfo = ConfigManager.getInstance().getItem(key);
                        String itemValue = null;
                        // 从系统配置中读取
                        if (configInfo != null) {
                            itemValue = configInfo.getValue();
                        }
                        // 从环境变量里读取
                        else {
                            itemValue = SpringContext.getProperties(key, configDefaultValue);
                        }
                        if ("integer".equalsIgnoreCase(type)) {
                            retValue = Integer.parseInt(itemValue);
                        }
                        else if ("number".equalsIgnoreCase(type)) {
                            retValue = Float.parseFloat(itemValue);
                        }
                        else {
                            retValue = itemValue;
                        }

                    }
                    else {
                        retValue = defaultValue;
                    }
                }
            }
        }

        return retValue;
    }

    private static Object parserWithSchema(JsonNode jsonNode, Object value) {
        if (jsonNode == null) {
            return value;
        }
        // 获取扩展类型
        String externType = jsonNode.get("externType").asText();
        if (StringUtils.isEmpty(externType)) {
            return value;
        }
        // 扩展信息node
        JsonNode externNode = jsonNode.get("extern");
        if (externNode == null) {
            return value;
        }
        // 字典
        if (externType.equalsIgnoreCase(KFlowConstant.EXTERN_TYPE_DICT)) {

            // 获取字典码
            JsonNode codeNode = externNode.get("code");
            if (codeNode == null) {
                return value;
            }
            String dictCode = codeNode.asText();
            // 字典转义
            String label = DictManager.getInstance().getDict(dictCode, value.toString());
            // 复合值返回
            ComplexValue complexValue= new ComplexValue();
            complexValue.setValue(value);
            complexValue.setLabel(label);
            return complexValue;
        }
        // Timestamp
        else if (externType.equalsIgnoreCase(KFlowConstant.EXTERN_TYPE_TIMESTAMP)) {
            // 获取格式化
            JsonNode formatNode = externNode.get("format");
            if (formatNode == null) {
                return value;
            }
            String strValue = value.toString();
            if (NumberUtils.isParsable(strValue)) {
                value = DateUtils.formatDate(new Date(Long.parseLong(strValue)), formatNode.asText());
            }
        }
        return value;
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
