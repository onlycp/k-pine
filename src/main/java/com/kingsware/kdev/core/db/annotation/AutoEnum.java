package com.kingsware.kdev.core.db.annotation;

/**
 * 自动赋值
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:50 上午
 */
public enum AutoEnum {
    // 忽略，不参与数据库操作
    IGNORE,
    // 无
    NONE,
    // 自动ID
    ID,
    // 当前用户
    WHO,
    // 当前时间
    WHEN,
}
