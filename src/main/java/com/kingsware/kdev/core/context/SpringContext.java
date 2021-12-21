package com.kingsware.kdev.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * Spring上下文
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 3:48 下午
 */
@Component
public class SpringContext implements ApplicationContextAware {
    /** 上下文 **/
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取bean
     * @param name  bean名称
     * @param <T>   泛型
     * @return      bean
     */
    public static <T> T getBean(String name) {
        return (T)applicationContext.getBean(name);
    }

    /**
     * 获取Bean
     * @param clazz     类名
     * @param <T>       泛型
     * @return          实体
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 注册Bean
     * @param beanName  Bean名称
     * @param bean      Bean对象
     * @param <T>       泛型
     */
    public static <T> void registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
        context.getBeanFactory().registerSingleton(beanName, bean);
    }

}
