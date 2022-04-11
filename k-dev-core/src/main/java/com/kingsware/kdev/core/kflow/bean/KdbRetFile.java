package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

/**
 * kdb响应文件
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/11 5:14 下午
 */
@Data
public class KdbRetFile {

    private String fileName;
    private byte[] data;
}
