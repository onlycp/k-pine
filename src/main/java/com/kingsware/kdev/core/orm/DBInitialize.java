package com.kingsware.kdev.core.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 数据库配置类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 4:48 下午
 */
@Component
public class DBInitialize {
    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(DBInitialize.class);

    @Resource
    private DataBaseProperties dataBaseProperties;

    @PostConstruct
    public void initConnections() {
        // 初始化连接
        Map<String, DBConnectConfig> connectConfigs = dataBaseProperties.getAllConnectConfig();
        if (!connectConfigs.isEmpty()) {
            for (Map.Entry<String, DBConnectConfig> entry: connectConfigs.entrySet()) {
                DbContext.getInstance().createDataBase(entry.getKey(), entry.getValue());
                logger.info("初始化数据库连接成功，连接名称:" + entry.getKey());
            }
        }
    }
}
