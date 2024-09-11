package com.kingsware.kdev.core.i18n;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用国际化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/7 08:59
 */
@Data
public class AppI18n {


    /**
     * 应用id
     */
    private String appId;
    /**
     * 国际化数据
     */
    private Map<String, Map<String, String>> i18nData = new HashMap<>();

    public AppI18n(String appId) {
        this.appId = appId;
    }
}
