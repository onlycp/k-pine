package com.kingsware.kdev.core.kflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.cache.config.ConfigManager;
import com.kingsware.kdev.core.cache.config.SysConfigInfo;
import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.ComplexValue;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KFlowMessage;
import com.kingsware.kdev.core.kflow.function.Functions;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * 流程专用工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/3 3:43 下午
 */
@Slf4j
public class FlowUtils {

    /**
     * 转换处理列表
     * @param text
     * @return
     */
    public static Object parseList2Object(String text) {
        List<Object> mapObjectList = parseList(text);
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
    public static List<Object> parseList(String text) {
        return JsonUtil.snakeCaseToListBean(text, Object.class);
    }


    @SuppressWarnings("unchecked")
    public static String lineToHumpJson(String str) {
        try {
            // 创建ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            // 设置命名策略：下划线转小写驼峰
            mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy() {
                @Override
                public String translate(String input) {
                    return StringUtils.lineToHump(input);
                }
            });
            // 读取为JsonNode，判断是否为数组
            JsonNode rootNode = mapper.readTree(str);
            // 处理数组
            ArrayNode arrayNode = (ArrayNode) rootNode;
            ArrayNode resultArray = mapper.createArrayNode();

            for (JsonNode node : arrayNode) {
                if (node.isObject()) {
                    // 将对象节点转为Map再转回JSON，以应用命名策略
                    Map<String, Object> map = mapper.convertValue(node, Map.class);
                    ObjectNode newNode = mapper.valueToTree(map);
                    resultArray.add(newNode);
                } else {
                    resultArray.add(node); // 非对象直接保留
                }
            }
            return mapper.writeValueAsString(resultArray);
        }
        catch (Exception e) {
            return str;
        }

    }

    @SuppressWarnings("unchecked")
    public static String lineToHumpObjectString(String str) {
        try {
            // 创建ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            // 设置命名策略：下划线转小写驼峰
            mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy() {
                @Override
                public String translate(String input) {
                    return StringUtils.lineToHump(input);
                }
            });

            // 先将JSON转为Map（通用结构）
            @SuppressWarnings("unchecked")
            Map<String, Object> map = mapper.readValue(str, Map.class);

            // 将Map转回JSON字符串，此时键名会变成驼峰风格
            return mapper.writeValueAsString(map);
        }
        catch (Exception e) {
            return str;
        }

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
                if (value instanceof String && context.getI18nKeys().contains(humpKey)) {
                    String valueStr = (String)value;
                    if (StringUtils.isNotEmpty(valueStr)) {
                        value = I18n.parseScript(context.getAppId(), valueStr);
                        //log.info("国际化处理:{}，原始值: {}, 国际化值：{}" ,humpKey, valueStr, value);
                    }
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
        String json = inArgv == null ? "{}" : inArgv;
        // 根节点定义
        JsonNode rootNode = JsonUtil.toTree(json);
        // 处理
        handleNodeValue(variables, rootNode, false);
    }


    /**
     * 解析节点值
     * @param variable  变量值
     * @param jsonNode  节点
     * @return          返回值
     */
    @SuppressWarnings("unchecked")
    public static Object handleNodeValue(Object variable, JsonNode jsonNode, boolean mock) {
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
                // 获取值
                Object varData = null;
                String pType = pNode.get("type").asText();
                if ("array".equalsIgnoreCase(pType)) {
                    varData = new ArrayList<>();
                }
                else if ("object".equalsIgnoreCase(pType)) {
                    varData = new HashMap<>();
                }
                // 只有值不存在的时候，才会使用默认值填充
                if (!mock) {
                    // 获取值
                    if (!map.containsKey(pName)) {
                        Object entryValue = handleNodeValue(varData, pNode, mock);
                        newValueMap.put(pName, entryValue);
                    }
                    // 根据类型进行数据转换
                    else {
                        Object inObject = map.get(pName);
                        if (inObject != null && StringUtils.isNotEmpty(inObject.toString())) {
                            String strValue = inObject.toString();
                            JsonNode pTypeNode = pNode.get("type");
                            try {
                                if ("integer".equalsIgnoreCase(pType)) {
                                    newValueMap.put(pName, Integer.parseInt(strValue));
                                }
                            }
                            catch (Exception e) {
                                throw new BusinessException(I18n.t("FlowUtils.error1","输入参数不合法，定义的类型为:{0}, 传入值为: {1}" , pType, strValue));
                            }

                        }

                    }
                }
                else {
                    Object entryValue = handleNodeValue(varData, pNode, mock);
                    newValueMap.put(pName, entryValue);
                }
            }
            // 覆盖原先的值
            if (map != null) {
                map.putAll(newValueMap);
            }

        }
        // 列表
        else if ("array".equalsIgnoreCase(type)) {
            // 获取list定义
            JsonNode itemNode = jsonNode.get("items");
            // 转为list
            List<Object> objects = JsonUtil.toListBean(JsonUtil.toJson(variable), Object.class);
            if (objects != null) {
                for (int i = 0; i < objects.size() ; i++) {
                    Object itemValue = handleNodeValue(objects.get(i), itemNode, mock);
                    objects.set(i, itemValue);
                }
            }
            retValue = objects;
        }
        else {
            // 获取扩展类型
            JsonNode externTypeNode = jsonNode.get("externType");
            // 如果此有值
            JsonNode externNode = jsonNode.get("extern");
            if (externNode != null) {
                // 扩展类型
                String externType = externTypeNode.asText().trim();
                // 如果扩展类型为空, 只此只处理默认值
                if (StringUtils.isEmpty(externType)) {
                    JsonNode defaultNode = externNode.get("default");
                    if (defaultNode != null) {
                        // 获取默认值
                        String defaultValue = defaultNode.asText().trim();
                        if (defaultValue.startsWith("${") && defaultValue.endsWith("}")) {
                            String key = defaultValue.replace("${", "").replaceAll("}", "").trim();
                            String configDefaultValue = null;
                            String itemKey = key;
                            if (key.contains("|")) {
                                configDefaultValue = key.split("\\|")[1].trim();
                                itemKey = key.split("\\|")[0].trim();

                            }
                            SysConfigInfo configInfo = ConfigManager.getInstance().getItem(itemKey);
                            String itemValue = null;

                            // 从系统配置中读取
                            if (configInfo != null) {
                                itemValue = configInfo.getValue();
                            }
                            // 从环境变量里读取
                            else {
                                itemValue = SpringContext.getProperties(itemKey, configDefaultValue);
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
//                            log.info("系统配置:{}, {}", key, retValue);

                        }
                        else {
                            retValue = defaultValue;
                        }
                    }
                }
                else if ("function".equals(externType)) {
                    // 函数名
                    String methodName = externNode.get("method").asText().trim();
                    // 参数
                    List<Object> params = new ArrayList<>();
                    if(externNode.has("params")) {
                        ArrayNode paramsNode = (ArrayNode)externNode.get("params");
                        String body = paramsNode.toString();
                        try {
                            params = new ObjectMapper().readValue(body, List.class);
                        }
                        catch (JsonProcessingException e) {
                            throw new BusinessException(I18n.t("FlowUtils.error2","函数参数解析异常，函数名: {0}, 参数: {1}", methodName, body)) ;
                        }
                    }
                    retValue = Functions.call(methodName, params);

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
        else if (responseBody.startsWith(KFlowConstant.ARRAY_START) && responseBody.endsWith(KFlowConstant.ARRAY_END)) {
            message.setHandlerName("list");
            message.setData(responseBody);
        }
        else if (responseBody.startsWith(KFlowConstant.LIST_FLAG)) {
            String text = responseBody.substring(KFlowConstant.LIST_FLAG.length());
            message.setHandlerName("list");
            message.setData(text);

        }
        else if (responseBody.startsWith(KFlowConstant.JSON_COMPRESS)) {
            String text = responseBody.substring(KFlowConstant.JSON_COMPRESS.length());
            // 解压
            String origin = JsonUtil.decompressJSON(Base64Utils.encode(text.getBytes()));
            if (origin.startsWith(KFlowConstant.ARRAY_START)) {
                message.setHandlerName("list2object");
                message.setData(origin);
            }
            else {
                message.setHandlerName("object");
                message.setData(origin);
            }

        }
        else if (responseBody.startsWith(KFlowConstant.OBJECT_FLAG)) {
            String text = responseBody.substring(KFlowConstant.OBJECT_FLAG.length());
            // 如果是数组，则调用数组转对象
            if (text.startsWith(KFlowConstant.ARRAY_START)) {
                message.setHandlerName("list2object");
                message.setData(text);
            }
            else {
                message.setHandlerName("object");
                message.setData(text);
            }
        }
        else if (responseBody.startsWith(KFlowConstant.EXCEL_FLAG)) {
            String text = responseBody.substring(KFlowConstant.EXCEL_FLAG.length());
            message.setHandlerName("excel");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.RAW_FLAG)) {
            String text = responseBody.substring(KFlowConstant.RAW_FLAG.length());
            message.setHandlerName("raw");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.KEXCEL_FLAG)) {
            String text = responseBody.substring(KFlowConstant.KEXCEL_FLAG.length());
            message.setHandlerName("kexcel");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.FILE_FLAG)) {
            String text = responseBody.substring(KFlowConstant.FILE_FLAG.length());
            message.setHandlerName("file");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.BASE64_TO_FILE_FLAG)) {
            String text = responseBody.substring(KFlowConstant.BASE64_TO_FILE_FLAG.length());
            message.setHandlerName("base64ToFile");
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
        else if (responseBody.startsWith(KFlowConstant.HTML_FLAG)) {
            String text = responseBody.replace(KFlowConstant.HTML_FLAG, "");
            message.setHandlerName("html");
            message.setData(text);
        }
        else if (responseBody.startsWith(KFlowConstant.CUSTOM_FLAG)) {
            String text = responseBody.replace(KFlowConstant.CUSTOM_FLAG, "");
            message.setHandlerName("custom");
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
    public static BaseRet<?> toJsonResult(Object result, String log) {
        // 返回前端
        BaseRet<?> ret;
        if (result instanceof MessageResult) {
            ret = BaseRet.successMessage(((MessageResult) result).getMessage());
        }
        else if (result instanceof ErrorResult) {
            ret = BaseRet.failMessage(((ErrorResult) result).getMessage());
        }
        else {
            ret = BaseRet.success(result);
        }
        ret.setLog(log);
        return ret;
    }

    /**
     * 转向api格式
     * @param result 源名称
     * @return
     */
    public static Object toJsonResult(Object result, String log, String stackException) {
        // 返回前端
        BaseRet<?> ret = toJsonResult(result, log);
        Map<String, Object> map = new HashMap<>();
        map.put("code", ret.getCode());
        if(ret.getCode() != 200 || ret.getCode() != 0) {
            KClientContext.getContext().setErrorMessage(ret.getMessage());
        }
        map.put("message", ret.getMessage());
        map.put("data", ret.getData());
        // 处理结果适配器
        if (StringUtils.isNotEmpty(KClientContext.getContext().getApiRspAdapter())) {
            try {
                // 执行JavaScript代码
                StringBuilder scripts = new StringBuilder();
                scripts.append("var payBody = " + JsonUtil.toJson(map));
                scripts.append("\n");
                scripts.append("function responseAdapter(payload) { \n");
                scripts.append(KClientContext.getContext().getApiRspAdapter());
                scripts.append("\n");
                scripts.append("}\n");
                scripts.append("JSON.stringify(responseAdapter(payBody));\n");
                KdbRet<String> retBody = DB.kdbApi().executeScript(scripts.toString());
                if (retBody.getErrorCode() == 0) {
                    map = JsonUtil.toMap(retBody.getResponseBody());
                }
                else {
                    map.put("code", 600);
                    map.put("message", I18n.t("FlowUtils.error3", "执行结果适配器异常:{0}",  retBody.getMessage()));
                    map.put("data", null);
                }
            } catch (Exception e) {
                map.put("code", 600);
                map.put("message", I18n.t("FlowUtils.error3", "执行结果适配器异常:{0}",  e.getMessage()));
                map.put("data", null);
            }
        }

        if (StringUtils.isNotEmpty(ret.getLog())) {
            map.put("log", ret.getLog());
        }
        boolean devMode = SpringContext.getBoolean("app.mode.dev", true);
        if (devMode) {
            map.put("exceptionStack", stackException);
        }
        if (StringUtils.isNotEmpty(stackException)) {
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setKlog(ret.getLog());
            exceptionLog.setStackTrace(stackException);
            if (KClientContext.getContext() != null) {
                exceptionLog.setArgv(KClientContext.getContext().getArgv());
            }
            exceptionLog.setId(DateUtils.formatDate(new Date(), "yyyyMMdd")+ "_" + MD5Utils.md5(stackException));
            map.put("exceptionId", exceptionLog.getId());
            InstanceManager.getInstance().broadMessage("exception-write-log", JsonUtil.toJson(exceptionLog));
        }



        return map;

    }

    /**
     * 转换成FAAS不识别#{}, ${}的SQL
     * @param sql
     * @return
     */
    public static String buildCDATASql(String sql) {
        return "<![CDATA[ " + sql + " ]]>";
    }
}
