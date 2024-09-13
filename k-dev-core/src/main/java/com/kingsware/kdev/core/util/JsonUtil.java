package com.kingsware.kdev.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.*;

/**
 * Json操作类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 4:02 下午
 */
public class JsonUtil {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /** 默认构造函数 **/
    private JsonUtil() {};

    /**
     * 将字符串转为对象
     * @param jsonString    json字符串
     * @param tClass        目标类
     * @param <T>           目标泛型
     * @return              目标对象
     */
    public static <T> T toBean(String jsonString, Class<T> tClass) {

        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException(I18n.t("JsonUtil.error1", "字符串转为对象失败, 源串:{0}", jsonString));
        }
    }

    /**
     * 读取json node
     * @param json json 字符串
     * @return  json node
     */
    public static JsonNode toTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            logger.warn("字符串转为对象失败, 源串:{}", json);
            return null;
        }
    }

    /**
     * 将字符串转为对象
     * @param jsonString    json字符串
     * @param tClass        目标类
     * @param <T>           目标泛型
     * @return              目标对象
     */
    public static <T,R> T toBean(String jsonString, Class<T> tClass, Class<R> pClass) {

        JavaType javaType =  objectMapper.getTypeFactory().constructParametricType(tClass, pClass);
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            logger.warn("字符串转为List对象失败, 源串:{}", jsonString);
            return null;
        }
    }


    /**
     * 将字符串转为对象
     * @param bean         源对象
     * @param <T>          源泛型
     * @return             json字符串
     */
    public static  <T> String toJson(T bean) {

        try {
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            logger.warn("对象转为字符串失败, 对象:{}", bean);
            return null;
        }
    }
    /**
     * 将字符串转为对象
     * @param bean         源对象
     * @param <T>          源泛型
     * @return             json字符串
     */
    public static  <T> String toJsonWithoutNull(T bean) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            logger.warn("对象转为字符串失败, 对象:{}", bean);
            return null;
        }
    }



    /**
     * 将map转为对象
     * @param map       源map
     * @param tClass    目标类
     * @param <T>       泛型
     * @return          目标对象
     */
    public static <T> T mapToBean(Map<?,?> map, Class<T> tClass) {
        // 简单起见，直接采用jackson将map转为json字符串
        String json = toJson(map);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        // 再简单起见，直接用jackson将json字符串转为对象
        return toBean(json, tClass);
    }

    /**
     * 将
     * @param bean  源bean
     * @param tClass    源class
     * @param <T>       泛型
     * @return          map
     */
    public static <T> Map<?,?> beanToMap(T bean, Class<T> tClass) {
        String json = toJson(bean);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return toBean(json, Map.class);
    }

    /**
     * 将对象转为map
     * @param bean  对象
     * @return      map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        // 获取所有Fields
        Field[] fields = BeanUtils.getAllFields(bean.getClass());
        for (Field field: fields) {
            map.put(field.getName(), BeanUtils.getFieldValue(field, bean));
        }
        return map;
    }

    /**
     * 转为map
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            //logger.warn("字符串转map失败, 源串:{}", json);
        }
        return null;
    }
    /**
     * 将 json转为list对象
     * @param json          json字符串
     * @param tClass        class
     * @param <T>           泛型
     * @return              list对象
     */
    public static <T> List<T> snakeCaseToListBean(String json, Class<T> tClass) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            JavaType javaType =  objectMapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);

            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            logger.warn("字符串转为List对象失败, 源串:{}", json);
            return null;
        }


    }

    /**
     * 将 json转为list对象
     * @param json          json字符串
     * @param tClass        class
     * @param <T>           泛型
     * @return              list对象
     */
    public static <T> List<T> toListBean(String json, Class<T> tClass) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JavaType javaType =  objectMapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);

            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            logger.warn("字符串转为List对象失败, 源串:{}", json);
            return null;
        }


    }

    /**
     * 将map转为实体
     * @param tClass    实体class
     * @param map       map对象
     * @param <T>       T
     * @return          实例化对象
     */
    public static <T> T transformMap2Entity(Class<T> tClass, Map<String, Object> map)  throws Exception{
        // 实体化

        T entity = tClass.newInstance();
        // 由于postgresql会自动将大写转为小写，这里需要一个将实体属性名称转为小写处理
        Map<String, Field> fieldMap = new HashMap<>();
        Field[] fields = BeanUtils.getAllFields(tClass);
        for (Field field: fields) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
            // 如果是布尔型，就将is_也加进来
            if (field.getType().isAssignableFrom(Boolean.class)) {
                String boolName = "is" + StringUtils.capitalize(field.getName());
                fieldMap.put(boolName, field);
            }
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (StringUtils.isNotEmpty(column.name())) {
                    fieldMap.put(StringUtils.lineToHump(column.name()), field);
                }
            }
        }
        //  遍历map，将值写入到实体
        for (Map.Entry<String, Object> entry: map.entrySet()) {
            String key = entry.getKey().toLowerCase();
            key = StringUtils.lineToHump(key);
            // 非空的才写入
            if (entry.getValue() != null) {
                if (fieldMap.containsKey(key)) {
                    Field field = fieldMap.get(key);
                    BeanUtils.setField(field, entity, entry.getValue());

                }
            }
        }
        return entity;
    }

    /**
     * 将json转为数组
     * @param tClass    目标类
     * @param json      json字符串
     * @param <T>       泛型
     * @return          目标列表
     */
    public static <T> List<T> transformJson2List(String json, Class<T> tClass) {
        JavaType javaType =  objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Map.class);
        try {
            List<Map<String, Object>> list = objectMapper.readValue(json, javaType);
            List<T> result = new ArrayList<>(list.size());
            for (Map<String, Object> map: list) {
                if (Map.class.isAssignableFrom(tClass)) {
                    result.add((T) map);
                }
                else {
                    result.add(transformMap2Entity(tClass, map));
                }

            }
            return result;
        } catch (Exception e) {
            logger.warn("字符串转为List对象失败, 源串:{}", json);
            return null;
        }
    }

    /**
     * 压缩JSON字符串。
     *
     * @param jsonString 待压缩的JSON字符串。
     * @return 压缩后的字节数组。如果出现异常，则返回null。
     */
    public static byte[] compressJSON(String jsonString) {
        try {
            // 将JSON字符串转为字节数组
            byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);

            // 创建 Deflater 对象并设置为最佳压缩级别
            Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
            deflater.setInput(jsonBytes);
            deflater.finish();

            // 使用 Deflater 对象压缩数据，并将结果存入 ByteArrayOutputStream 中
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, deflater)) {
                // 将数据写入压缩输出流
                deflaterOutputStream.write(jsonBytes);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解压缩字节数组为JSON字符串。
     *
     * @param compressedData 待解压缩的字节数组。
     * @return 解压缩后的JSON字符串。如果出现异常，则返回null。
     */
    public static String decompressJSON(byte[] compressedData) {
        try {
            // 创建 Inflater 对象用于解压缩
            Inflater inflater = new Inflater();
            inflater.setInput(compressedData);

            // 使用 Inflater 对象解压缩数据，并将结果存入 ByteArrayOutputStream 中
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
            try (InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream, inflater)) {
                // 读取并解压缩数据到输出流中
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inflaterInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                return outputStream.toString(StandardCharsets.UTF_8.name());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SneakyThrows
    public static String prettyJson(String compressedJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // 解析 JSON 字符串到对象
            Object json = objectMapper.readValue(compressedJson, Object.class);

            // 格式化输出 JSON 字符串
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        }
        catch (Exception e) {
            return compressedJson;
        }


    }




}
