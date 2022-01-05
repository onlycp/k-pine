package com.kingsware.kdev.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
     * 给对象的属性赋值
     * @param field     属性
     * @param target    目标对象
     * @param value     值
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            logger.warn("对象属性赋值失败, 属性名:{}, 属性值:{}", field.getName(), value);
        }
    }

    /**
     * 获取对象属性值
     * @param field     属性
     * @param target    目标对象
     */
    public static Object getField(Field field, Object target) {
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
    public static Object getField(String fieldName, Object target) {

        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException | NoSuchFieldException e) {
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
