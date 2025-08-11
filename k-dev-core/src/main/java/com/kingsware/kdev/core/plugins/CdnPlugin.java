package com.kingsware.kdev.core.plugins;

import java.io.InputStream;

/**
 * 内容存储插件
 */
public interface CdnPlugin {

    /**
     * 上传文件
     * @param inputStream   文件流
     * @param fileName      文件名称
     * @param path          路径
     */
    void upload(InputStream inputStream, String fileName,  String path);

    /**
     * 下载文件
     * @param path  路径
     * @return  本地文件路径
     */
    String download(String path);

    /**
     * 返回保存类型
     * @return  保存类型
     */
    int saveType();
}
