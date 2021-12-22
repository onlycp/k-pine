package com.kingsware.kdev.core.orm.channel;

import java.util.List;

/**
 * 数据库通道基类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 8:55 上午
 */
public interface DbChannel {

    public <T> T execute(String sql, Class<T> tClass, Object... objects);

    public <T> List<T> executeList(String sql, Class<T> tClass, Object... objects);
}
