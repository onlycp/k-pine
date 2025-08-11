package com.kingsware.kdev.core.i18n;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.text.MessageFormat;
import java.util.*;

/**
 * 国际化工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 10:26 上午
 */
public class I18n {

    /**
     * 国际化数据
     */
    private static Map<String, Map<String, String>> i18nData = new HashMap<>();


    /**
     * 添加国际化数据
     * @param key
     * @param lang
     * @param message
     */
    public static void putI18n(String key, String lang, String message) {
        if (i18nData.containsKey(key)) {
            i18nData.get(key).put(lang, message);
        }
        else {
            Map<String, String> map = new HashMap<>();
            map.put(lang, message);
            i18nData.put(key, map);
        }
    }


    /**
     * 获取国际化消息
     * 先预留相关接口，后端要求如何提示信息必须按此要求处理返回
     * 示例：
     * <pre>
     *     I18n.t("common.delete", "成功删除{0}条数据", 1)
     * </pre>
     *
     * @param key               国际化key
     * @param defaultMessage    默认消息
     * @param params            参数
     * @return      国际化消息
     */
    public static String t(String key, String defaultMessage, Object... params) {
        try {
            // 初始化消息为默认值
            String message = defaultMessage;
            // 如果国际化数据中包含给定的键
            if (i18nData.containsKey(key)) {
                // 尝试用特定语言获取消息
                String str = i18nData.get(key).get(lang());
                // 如果获取的消息为空或仅含空格，则回退到默认消息
                if (StringUtils.isEmpty(str)) {
                    message = defaultMessage;
                }
                else {
                    // 否则，使用获取到的消息
                    message = str;
                }
            }
            // 作为额外的检查，如果此时消息仍然为空或仅含空格，则设置为默认消息
            if (StringUtils.isEmpty(message)) {
                message = defaultMessage;
            }
            // 使用参数格式化最终的消息，并返回
            return MessageFormat.format(message, params);
        }
        // 捕获并处理任何发生的异常，简单返回键作为后备计划
        catch (Exception e) {
            return key;
        }
    }

    public static String lang(HttpServletRequest request) {
        // 判断请求参数里有无
        String queryLang = request.getParameter("lang");
        if (StringUtils.isNotEmpty(request.getParameter("lang")) ) {
            return queryLang.replaceAll("-", "_");
        }

        // 从请求头获取
        String i18n = request.getHeader("lang");
        if (StringUtils.isNotEmpty(i18n)) {
            return i18n;
        }

        List<Locale> LOCALES = Arrays.asList(new Locale("zh-cn"), new Locale("zh-hk"), new Locale("en-us"), new Locale("en"), new Locale("zh"));
        Locale locale = Locale.getDefault();
        if (StringUtils.isNotEmpty(request.getHeader("Accept-Language"))) {
            List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
            locale = Locale.lookup(list, LOCALES);
        }
        if (locale == null) {
            return "zh_CN";
        }
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        if (lang.equalsIgnoreCase("zh_")) {
            lang = "zh_CN";
        }
        else if (lang.equalsIgnoreCase("en_")) {
            lang = "en_US";
        }
        return lang;
    }

    /**
     * 获取语言
     * @return  返回语言
     */
    public static String lang() {

        try {
            HttpServletRequest request = KClientContext.getContext().getRequest();
            String lang =  lang(request);
            if (StringUtils.isNotEmpty(lang)) {
                return lang;
            }
            else {
                return "zh_CN";
            }
        }
        catch (Exception e) {
            return "zh_CN";
        }

    }

}
