package com.kingsware.kdev.core.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 类工具
 *
 * @author chen peng
 * @version %I%, %G%
 * @date 2021/12/17 10:21 下午
 */
@Slf4j
public class ClassUtils {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(ClassUtils.class);

    /** 私有构造 **/
    private ClassUtils() {}

    /**
     * 根据接口去查询所有实现的子类
     * @param basePackage   基础路径
     * @param parentClass   父类
     * @param <T>           泛型
     * @return              所有继承于父类的接口
     */
    public static <T> List<Class<?>> getClassesByParentClass(String basePackage, Class<T> parentClass) {
        // 返回结果列表
        List<Class<?>> result = new ArrayList<>();
        try {
            // 扫描所有的class
            String resourcePattern = "/**/*.class";
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + resourcePattern;
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            // 遍历所有类，查询消费类
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    Class<?> clazz = Class.forName(className);
                    if (parentClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        result.add(clazz);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            logger.warn("扫描Class异常，异常信息：{}" , e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 通过注解查询class
     * @param basePackage   基础路径
     * @param annotationClass   注解类
     * @return              所有继承于父类的接口
     */
    public static List<Class<?>> getClassesByAnnotationClass(String basePackage, Class<? extends Annotation> annotationClass) {
        // 返回结果列表
        List<Class<?>> result = new ArrayList<>();
        try {
            // 扫描所有的class
            String resourcePattern = "/**/*.class";
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + resourcePattern;
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            // 遍历所有类，查询消费类
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    Class<?> clazz = Class.forName(className);
//                    log.info("Class:" + clazz.getName());
                    if (clazz.isAnnotationPresent(annotationClass)) {
                        result.add(clazz);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            logger.warn("扫描Class异常，异常信息：{}" , e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 判断某个类是否存在
     * @param className 要检查的类的名称
     * @return 存在返回 true，否则返回 false
     */
    public static boolean isClassExists(String className) {
        try {
            // 通过反射获取 className 所表示的类的引用
            Class.forName(className);
            // 如果没有抛出 ClassNotFoundException 异常，说明类存在，返回 true
            return true;
        } catch (ClassNotFoundException e) {
            // 如果抛出 ClassNotFoundException 异常，说明类不存在，返回 false
            return false;
        }
    }


}
