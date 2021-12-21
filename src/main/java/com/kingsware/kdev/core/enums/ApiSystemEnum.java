package com.kingsware.kdev.core.enums;

/**
 * 权限系统枚举
 * 用来标志到底是管理后台权限，还是会员权限，或者其他的
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 11:55 上午
 */
public enum ApiSystemEnum {
    // 管理后台
    ADMIN(0);

    private final int value;

    /**
     * 默认构造函数
     * @param value 值
     */
    ApiSystemEnum(int value) {
        this.value = value;
    }

    /**
     * 获取枚举的值
     * @return  枚举值
     */
    public int getValue() {
        return this.value;
    }
}
