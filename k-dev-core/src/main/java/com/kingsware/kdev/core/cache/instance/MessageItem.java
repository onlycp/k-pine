package com.kingsware.kdev.core.cache.instance;

import lombok.Data;

/**
 * @author chenp
 * @date 2024/4/24
 */
@Data
public class MessageItem {
    /** 主题 **/
    private String topic;
    /** 数据 **/
    private String data;
}
