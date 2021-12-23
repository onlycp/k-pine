package com.kingsware.kdev.core.orm.channel;

import com.kingsware.kdev.core.orm.DBConnectConfig;
import com.kingsware.kdev.core.orm.SqlGenerator;
import com.kingsware.kdev.core.orm.kdb.KDBConnectConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * KBD的http通道
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 9:07 上午
 */
public class KDBHttpChannel implements DbChannel{

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(KDBHttpChannel.class);

    /** 配置 **/
    private KDBConnectConfig kdbConnectConfig;

    @Override
    public String name() {
        return "kdbHttp";
    }

    @Override
    public void setConfig(DBConnectConfig config) {
        this.kdbConnectConfig = (KDBConnectConfig)config;
    }

    @Override
    public <T> T execute(String sql, Class<T> tClass, Object... objects) {
        logger.info("KDBHttpChannel，SQL：{}， Params：{}", sql, objects);
        return null;
    }

    public <T> List<T> executeList(String sql, Class<T> tClass,  Object... objects) {
        logger.info("KDBHttpChannel，SQL：{}， Params：{}", sql, objects);
        return null;
    }
}
