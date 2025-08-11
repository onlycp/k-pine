package com.kingsware.kdev.core.util;

import java.util.Map;

/**
 * 模板工具类
 */
public class TemplateUtil {

    private TemplateUtil(){}

    /**
     * 渲染页面
     * @param template
     * @param context
     * @return
     */
    public static String render(String template, Map<String, String> context) {
        if (StringUtils.isEmpty(template)) {
            return template;
        }


        for (Map.Entry<String, String> entry: context.entrySet()) {
            String fromStr = "${" + entry.getKey() + "}";
            String toStr = entry.getValue();
            template = template.replace(fromStr, toStr);
        }
        return template;
    }
}
