package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

/**
 * KdbCustomResource 类用于封装与KDB相关的自定义资源信息
 * 它提供了对资源的字符编码、内容类型以及资源主体的表示
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/11/5 15:28
 */
@Data
public class KdbCustomResource {

    /**
     * 字符编码，用于指定资源的字符集
     */
    private String  characterEncoding;

    /**
     * 内容类型，描述资源的MIME类型
     */
    private String  contentType;

    /**
     * 资源主体，存放实际的资源内容
     */
    private byte[] data;
}
