package com.kingsware.kdev.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            return new ObjectMapper().readValue(jsonString, tClass);
        } catch (JsonProcessingException e) {
            logger.warn("字符串转为对象失败, 源串:{}", jsonString);
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
            return new ObjectMapper().writeValueAsString(bean);
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
     * 将 json转为list对象
     * @param json          json字符串
     * @param tClass        class
     * @param <T>           泛型
     * @return              list对象
     */
    public static <T> List<T> toListBean(String json, Class<T> tClass) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            JavaType javaType =  objectMapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            logger.warn("字符串转为List对象失败, 源串:{}", json);
            return null;
        }


    }

}
