package com.kingsware.kdev.core.orm;

import lombok.Data;

import java.util.Map;

/**
 * Faas失败记录
 * @author chenp
 * @date 2023/2/24
 */
@Data
public class FaasFailRecord {
    private String url;
    private String body;
    private Map<String, String> headerMap;
    private Long time;
}
