package com.kingsware.kdev.core.orm.channel;

import java.util.List;

/**
 * KBD的http通道
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 9:07 上午
 */
public class KDBHttpChannel implements DbChannel{

    @Override
    public <T> T execute(String sql, Class<T> tClass, Object... objects) {
        return null;
    }

    public <T> List<T> executeList(String sql, Class<T> tClass,  Object... objects) {
        return null;
    }
}
