package com.kingsware.kdev.core.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.kingsware.kdev.core.util.StringUtils;

/**
 * @author chenp
 * @date 2024/7/5
 */
public class SnakeToCamelCaseStrategy extends PropertyNamingStrategies.NamingBase {
    @Override
    public String translate(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Leave null and empty fields unchanged
        }

       return StringUtils.lineToHump(input);
    }
}
