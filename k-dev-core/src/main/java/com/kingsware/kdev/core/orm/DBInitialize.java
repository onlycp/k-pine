package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 数据库配置类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 4:48 下午
 */
@Component
@DependsOn("springContext")
public class DBInitialize {
    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(DBInitialize.class);

    /** 是否加载完成 **/
    public static boolean initCompleted = false;
    @Resource
    private DataBaseProperties dataBaseProperties;

    @PostConstruct
    public void initConnections() {
        // 初始化连接
        Map<String, DBConnectConfig> connectConfigs = dataBaseProperties.getAllConnectConfig();
        if (!connectConfigs.isEmpty()) {
            for (Map.Entry<String, DBConnectConfig> entry: connectConfigs.entrySet()) {
                DbContext.getInstance().createDataBase(entry.getKey(), entry.getValue());
                logger.info("初始化数据库连接成功，连接名称:" + entry.getKey() + "-" + entry.getValue());
            }
        }
        // 初始化系统
        initSystem();
        initCompleted = true;
    }

    /**
     * 初始化k-faas参数
     */
    public void initSystem() {
        List<SystemInitialize> systemInitializes = SpringContext.getBeansOfType(SystemInitialize.class);
        // 按顺序初始化
        sortSystemInitializes(systemInitializes);
        try {
            for (SystemInitialize systemInitialize : systemInitializes) {
                systemInitialize.execute();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void sortSystemInitializes(List<SystemInitialize> systemInitializes) {
        if (systemInitializes == null) {
            return ;
        }
        SystemInitialize tmp = null;
        for (int i = 0; i < systemInitializes.size() - 1; i ++) {
            SystemInitialize initA = systemInitializes.get(i);
            for (int j = i + 1; j < systemInitializes.size(); j ++) {
                SystemInitialize initB = systemInitializes.get(j);
                if (initB.sort() < initA.sort()) {
                    tmp = initA;
                    initA = initB;
                    initB = tmp;
                    systemInitializes.set(j, initB);
                    systemInitializes.set(i, initA);
                }
            }
        }
    }
}
