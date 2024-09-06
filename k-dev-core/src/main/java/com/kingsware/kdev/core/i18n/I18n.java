package com.kingsware.kdev.core.i18n;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 判断是否存在key
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        return i18nData.containsKey(key);
    }


    /**
     * 创建国际化信息
     *
     * 该方法用于在系统中创建一个新的国际化（i18n）条目它接收一个关键字和一个默认消息，
     * 并将它们存储为一个SysI18n对象SysI18n对象包含国际化键和一个消息映射，映射支持多种语言的消息，
     * 此处只添加了中文默认消息之后，该对象被序列化并存储到数据库中
     *
     * @param key 要创建的国际化信息的键值这个键值用于在代码中引用相应的国际化消息
     * @param defaultMessage 默认消息这是在没有找到特定语言消息时回退的消息它被存储为中文（zh_CN）消息
     */
    public static void create(String key, String defaultMessage) {
        // 创建一个SysI18n对象，用于存储国际化信息
        SysI18n sysI18n = new SysI18n();
        // 设置国际化信息的键值
        sysI18n.setI18nKey(key);
        // 创建一个HashMap，用于存储不同语言的消息
        Map<String, String> i18nMap = new HashMap<>();
        // 将默认消息作为中文消息添加到映射中
        i18nMap.put("zh_CN", defaultMessage);
        i18nData.put(key, i18nMap);
        // 将消息映射序列化为JSON字符串，并设置为SysI18n对象的消息
        sysI18n.setMessage(JsonUtil.toJson(i18nMap));
        // 将SysI18n对象保存到数据库
        DB.save(sysI18n);
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
        if (lang.toLowerCase().startsWith("zh")) {
            lang = "zh_CN";
        }
        else if (lang.toLowerCase().startsWith("en")) {
            lang = "en_US";
        }
        return lang;
    }

    /**
     * 解析脚本中的国际化函数 t('i18nkey', '默认值') 或 t("i18nkey", "默认值")
     *
     * @param script 包含 t('i18nkey', '默认值') 或 t("i18nkey", "默认值") 的脚本字符串
     * @return 替换后的字符串
     */
    public static String parseScript(String script) {
        // 匹配 t('key', 'default') 或 t("key", "default") 的正则表达式
        Pattern pattern = Pattern.compile("t\\((['\"])(.*?)\\1,\\s*(['\"])(.*?)\\3\\)");
        Matcher matcher = pattern.matcher(script);
        if (!matcher.find()) {
            // 如果没有找到 t() 调用，直接将 script 作为 key 进行翻译
            if (!I18n.hasKey(script)) {
                I18n.create(script, script);
            }
            return I18n.t(script, script);
        }

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(2);         // 获取 i18n key
            String defaultValue = matcher.group(4); // 获取默认值

            // 查找国际化翻译
            String translatedValue = I18n.t(key, defaultValue);

            // 将匹配的 t('key', 'default') 或 t("key", "default") 替换为翻译结果
            matcher.appendReplacement(result, translatedValue);
        }
        matcher.appendTail(result);
        return result.toString();
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
