package com.kingsware.kdev.core.kflow;

import lombok.Data;

/**
 * 提示信息
 * @author  chen peng
 * @version 1.0.0
 * @date    2022/1/20 11:18 上午
 */
@Data
public class MessageResult {

    private String message;

    public MessageResult(String message) {
        this.message = message;
    }
}
