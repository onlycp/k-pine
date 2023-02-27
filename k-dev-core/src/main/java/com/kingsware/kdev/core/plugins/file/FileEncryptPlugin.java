package com.kingsware.kdev.core.plugins.file;

import java.io.File;

/**
 * 文件加密插件
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/2/20 17:07
 */
public interface FileEncryptPlugin {


    /**
     * 加密文件
     * @param file  文件
     * @return
     */
    File encrypt(File file);

    /**
     * 名称
     * @return
     */
    String name();


}
