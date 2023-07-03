package com.kingsware.kdev.core.i18n;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 国际化工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 10:26 上午
 */
public class I18n {

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
        if (params.length == 0) {
            return defaultMessage;
        }
        else {
            return MessageFormat.format(defaultMessage, params);
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

        HttpServletRequest request = KClientContext.getContext().getRequest();
        return lang(request);

    }

}
