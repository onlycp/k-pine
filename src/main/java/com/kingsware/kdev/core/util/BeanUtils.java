package com.kingsware.kdev.core.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Bean工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 10:38 上午
 */
public class BeanUtils {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(BeanUtils.class);


    /**
     * 获取所有的Field
     * @param clazz    目标类
     * @return          所有field，包括继承的
     */
    public static Field[] getAllFields(Class<?> clazz) {

        List<Field> allFields = new ArrayList<>();
        //向上循环 遍历父类
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] field = clazz.getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                allFields.add(f);
            }
        }
        return allFields.toArray(new Field[0]);

    }

    /**
     * 获取Field定义
     * @param clazz         目标类
     * @param fieldName     属性名
     * @return              Field
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        Field[] fields = getAllFields(clazz);
        for (Field field: fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 给对象的属性赋值
     * @param field     属性
     * @param target    目标对象
     * @param value     值
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            if (value == null) {
                field.set(target, null);
            }
            // 当field的type不等于value的type时
            Class<?> fieldType = field.getType();
            if (value.getClass().equals(fieldType) || fieldType.isAssignableFrom(value.getClass())) {
                field.set(target, value);
            }
            else {
                // 区分不同的field类型处理
                if (fieldType.isAssignableFrom(Integer.class)) {
                    if (value instanceof Boolean) {
                        field.set(target, (Boolean)value ? 1 : 0);
                    }
                    else if (value instanceof String) {
                        String strValue = (String) value;
                        if ("true".equals(strValue)) {
                            field.set(target, 1);
                        }
                        else if ("false".equals(strValue)) {
                            field.set(target, 0);
                        }
                        else {
                            field.set(target, Integer.parseInt(value.toString()));
                        }
                    }
                    else {
                        field.set(target, Integer.parseInt(value.toString()));
                    }
                }
                else if (fieldType.isAssignableFrom(Long.class)) {
                    field.set(target, Long.parseLong(value.toString()));
                }
                else if (fieldType.isAssignableFrom(Float.class)) {
                    field.set(target, Float.parseFloat(value.toString()));
                }
                else if (fieldType.isAssignableFrom(Double.class)) {
                    field.set(target, Double.parseDouble(value.toString()));
                }
                else if (fieldType.isAssignableFrom(String.class)) {
                    field.set(target, value.toString());
                }
                else if (fieldType.isAssignableFrom(Boolean.class)) {
                    String strValue = value.toString();
                    if (NumberUtils.isParsable(strValue)) {
                        field.set(target, "1".equals(value.toString()));
                    }
                    else if ("true".equals(strValue)) {
                        field.set(target, true);
                    }
                    else if ("false".equals(strValue)) {
                        field.set(target, false);
                    }

                }
                else if (fieldType.isAssignableFrom(BigDecimal.class)) {
                    field.set(target, new BigDecimal(value.toString()));
                }
                else if (fieldType.isAssignableFrom(Timestamp.class)) {
                    // 如果值是整型
                    if (value instanceof Long || NumberUtils.isParsable(value.toString())) {
                        field.set(target, new Timestamp(Long.parseLong(value.toString())));
                    }
                    // 如果值是字符串
                    else  {
                        Date date = DateUtils.toDate(value.toString(), DateUtils.DATE_TIME);
                        if (date != null) {
                            field.set(target, new Timestamp(date.getTime()));
                        }
                    }
                }
                else {
                    field.set(target, value);
                }
            }

        } catch (Exception e) {
            // 此处应抛出异常
            logger.warn("对象属性赋值失败, 属性名:{}, 属性值:{}", field.getName(), value);
        }
    }

    /**
     * 获取对象属性值
     * @param field     属性
     * @param target    目标对象
     */
    public static Object getFieldValue(Field field, Object target) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException e) {
            logger.warn("获取对象属性值失败，获取属性值失败, 属性名:{}, 对象:{}", field.getName(), target);
            return null;
        }
    }

    /**
     * 获取对象属性值
     * @param fieldName     属性
     * @param target    目标对象
     */
    public static Object getFieldValue(String fieldName, Object target) {

        try {
            Field field = getField(target.getClass(), fieldName);
            assert field != null;
            return field.get(target);
        } catch (IllegalAccessException e ) {
            logger.warn("获取对象属性值失败, 属性名:{}, 对象:{}", fieldName, target);
            return null;
        }
    }

    /**
     * 拷贝
     * @param src
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T copyObject(Object src, Class<T> tClass) {
        try {
            T dst = tClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(src, dst);
            return dst;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("对象拷贝失败");
        }
    }

    /**
     * 拷贝列表
     * @param list      列表
     * @param tClass    class
     * @param <T>       泛型
     * @return          转换后的list
     */
    public static <T> List<T> copyList(List<?> list, Class<T> tClass) {
        List<T> tList = new ArrayList<>();
        for (Object obj: list) {
            try {
                T dst = tClass.newInstance();
                org.springframework.beans.BeanUtils.copyProperties(obj, dst);
                tList.add(dst);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("对象拷贝失败");
            }
        }
        return tList;

    }

    /**
     * 同org.springframework.beans.BeanUtils.copyProperties
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
}
