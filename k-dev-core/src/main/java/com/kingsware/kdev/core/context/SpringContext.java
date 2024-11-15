package com.kingsware.kdev.core.context;

import com.kingsware.kdev.core.bean.GroupProperties;
import com.kingsware.kdev.core.cache.config.ConfigManager;
import com.kingsware.kdev.core.cache.config.SysConfigInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Spring上下文
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 3:48 下午
 */
@Component
@Order(-1)
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

    /**
     * 获取配置项
     * @param key           key
     * @param defaultValue  默认值
     * @return              返回配置项，如果不存在，返回默认值
     */
    public static String getProperties(String key, String defaultValue) {
        // 如果系统配置里，优先读取系统配置的
        SysConfigInfo configInfo = ConfigManager.getInstance().getItem(key);
        if (configInfo != null ) {
            return configInfo.getValue();
        }
        else {
            return applicationContext.getEnvironment().getProperty(key, defaultValue);
        }

    }

    /**
     * 获取配置项
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInt(String key, Integer defaultValue) {
        try {
            return Integer.parseInt(getProperties(key, defaultValue.toString()));
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取配置项
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return Boolean.parseBoolean(getProperties(key, defaultValue.toString()));
    }


    /**
     * 获取组属性
     *
     * @param groupKey 组键
     * @return 组属性
     */
    public static GroupProperties getGroupProperties(String groupKey) {
        GroupProperties groupProperties = new GroupProperties();
        String enable = getProperties(groupKey + ".enable", "false");
        groupProperties.setEnable(Boolean.parseBoolean(enable));
        groupProperties.setGroup(groupKey);
        Map<String, String> properties = getProperties();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            if (entry.getKey().startsWith(groupKey + ".") && !entry.getKey().equals(groupKey + ".enable")) {
                groupProperties.putValue(entry.getKey().substring(groupKey.length() + 1), entry.getValue());
            }
        }
        return groupProperties;
    }


    /**
     * 获取配置项
     * @param key           key
     * @param appId         应用id
     * @param defaultValue  默认值
     * @return              返回配置项，如果不存在，返回默认值
     */
    public static String getProperties(String key, String appId, String defaultValue) {
        // 先读取应用专属的
        String keyOfApp = appId + "." + key;
        SysConfigInfo configInfo = ConfigManager.getInstance().getItem(keyOfApp);
        if (configInfo != null) {
            return configInfo.getValue();
        }
        // 如果系统配置里，优先读取系统配置的
        configInfo = ConfigManager.getInstance().getItem(key);
        if (configInfo != null ) {
            return configInfo.getValue();
        }
        else {
            return applicationContext.getEnvironment().getProperty(key, defaultValue);
        }

    }

    /**
     * 获取配置项
     * @param key           key
     * @param defaultValue  默认值
     * @return              返回配置项，如果不存在，返回默认值
     */
    public static String getBootProperties(String key, String defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, defaultValue);

    }

    /**
     * 获取所有的资源
     *
     * @param pattern 路径
     * @return 资源列表
     */
    public static Resource[] getResources(String pattern) {
        try {
            return applicationContext.getResources(pattern);
        } catch (IOException e) {
            return null;
        }

    }

    /**
     * 获取所有的资源
     *
     * @param resourceName 路径
     * @return 资源列表
     */
    public static Resource getResource(String resourceName) {
        return applicationContext.getResource(resourceName);
    }


    /**
     * 获取所有的配置项
     * @return
     */
    @SuppressWarnings("all")
    public static Map<String, String> getProperties() {

        //  加载环境变量
        StandardServletEnvironment environment = (StandardServletEnvironment) applicationContext.getEnvironment();
        Iterator<PropertySource<?>> iterator = environment.getPropertySources().iterator();
        Map<String, String> properties = new HashMap<>();
        while (iterator.hasNext()) {
            PropertySource<?> propertySource = iterator.next();
            String name = propertySource.getName();
            // 去掉系统配置和环境变量
            if (name.equals(StandardServletEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME) || name.equals(StandardServletEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME)) {
                continue;
            }
            Object o = propertySource.getSource();
            if (o instanceof Map) {
                for(Map.Entry<String, Object> entry: ((Map<String, Object>)o).entrySet()) {
                    String key = entry.getKey();
                    properties.put(key, environment.getProperty(key));
                }
            }
        }
        // 加载系统配置
        Map<String, String> configMap = ConfigManager.getInstance().getAllConfig();
        properties.putAll(configMap);
        return properties;

    }

    public static <T> List<T> getBeansOfType(Class<T> tClass){
        Collection<T> beans = new LinkedList<>(applicationContext.getBeansOfType(tClass).values());
        return new ArrayList<>(beans);
    }

}
