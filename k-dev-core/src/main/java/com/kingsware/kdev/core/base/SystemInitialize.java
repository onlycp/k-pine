package com.kingsware.kdev.core.base;

import java.io.FileNotFoundException;

/**
 * 系统初始化类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/28 4:28 下午
 */
public interface SystemInitialize {
    /**
     * 系统初始化处理
     */
    void execute() throws FileNotFoundException;

    int sort();
}
