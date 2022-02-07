package com.kingsware.kdev.core.kflow;

import lombok.Data;

/**
 * 提示信息
 * @author  chen peng
 * @version 1.0.0
 * @date    2022/1/20 11:18 上午
 */
@Data
public class TipResult {

    private String message;

    public TipResult(String message) {
        this.message = message;
    }
}
