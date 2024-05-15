package com.kingsware.kdev.core.cache.instance;

import lombok.Data;

/**
 * @author chenp
 * @date 2024/5/6
 */
@Data
public class MessageBytes {
    private int len;
    private byte[] data;
}
