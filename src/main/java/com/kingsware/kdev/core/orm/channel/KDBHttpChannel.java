package com.kingsware.kdev.core.orm.channel;

import com.kingsware.kdev.core.orm.DBConnectConfig;
import com.kingsware.kdev.core.orm.kdb.KDBConnectConfig;

import java.util.List;

/**
 * KBD的http通道
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 9:07 上午
 */
public class KDBHttpChannel implements DbChannel{

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
        return null;
    }

    public <T> List<T> executeList(String sql, Class<T> tClass,  Object... objects) {
        return null;
    }
}
