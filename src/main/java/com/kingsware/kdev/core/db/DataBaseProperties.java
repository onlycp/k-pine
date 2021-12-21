package com.kingsware.kdev.core.db;

import com.kingsware.kdev.core.db.kdb.KDBConnectConfig;
import com.kingsware.kdev.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库配置文件
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 3:30 下午
 */
@Component
@ConfigurationProperties(prefix = "database")
public class DataBaseProperties {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(DataBaseProperties.class);

    /**
     * 数据源配置
     */
    private Map<String, Map<String, String>> sources = new HashMap<>();

    /**
     * 获取所有的配置信息
     * @return  配置信息
     */
    public Map<String,DBConnectConfig> getAllConnectConfig() {
        // 返回结果
        Map<String, DBConnectConfig> dbConnectConfigMap = new HashMap<>();
        // 遍历处理数据连接信息
        for (Map.Entry<String, Map<String, String>> entry: sources.entrySet()) {
            // 连接名称
            String name = entry.getKey();
            // 数据库类型
            String databaseType = entry.getValue().getOrDefault("databaseType", "kdb");
            // 将map配置转为config
            // 如果是kdb
            if (databaseType.equalsIgnoreCase(DataBaseTypeEnum.KDB.getValue())) {
                KDBConnectConfig kdbConnectConfig = JsonUtil.mapToBean(entry.getValue(), KDBConnectConfig.class);
                dbConnectConfigMap.put(name, kdbConnectConfig);
            }

        }
        return dbConnectConfigMap;
    }


    public void setSources(Map<String, Map<String, String>> sources) {
        this.sources = sources;
    }
}
