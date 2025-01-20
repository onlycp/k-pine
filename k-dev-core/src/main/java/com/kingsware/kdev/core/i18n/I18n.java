package com.kingsware.kdev.core.i18n;

import com.kingsware.kdev.core.config.SysConst;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.RandomUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.util.MapUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 国际化工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 10:26 上午
 */
@Slf4j
public class I18n {

    /**
     * 国际化数据
     */
    private static Map<String, AppI18n> appI18nData = new HashMap<>();


    /**
     * 获取国际化数据
     * 根据给定的应用ID，获取相应的国际化数据地图如果该应用ID未在appI18nData中注册，则创建并注册
     *
     * @param appId 应用ID，用于获取特定应用的国际化数据
     * @return 国际化数据地图，包含应用内所有国际化字符串的数据结构
     */
    public static Map<String, Map<String, String>> getI18nData(String appId) {
        if (appI18nData.containsKey(appId)) {
            return appI18nData.get(appId).getI18nData();
        }
        return null;

    }

    public static void updateI18Key(String oldKey, String newKey, String appId) {
        if (appI18nData.containsKey(appId)) {
            AppI18n appI18n = appI18nData.get(appId);
            if( appI18n.getI18nData().containsKey(oldKey) ) {
                Map<String, String> map = appI18n.getI18nData().get(oldKey);
                appI18n.getI18nData().put(newKey, map);
                appI18n.getI18nData().remove(oldKey);
            }
        }
    }

    public static void remove(String appId, String key) {
        if (appI18nData.containsKey(appId)) {
            appI18nData.get(appId).getI18nData().remove(key);
        }
    }




    /**
     * 添加国际化数据
     * @param key
     * @param lang
     * @param message
     */
    public static void putI18n(String appId, String key, String lang, String message) {

        Map<String, Map<String, String>> i18nData = getI18nData(appId);
        if (i18nData == null) {
            appI18nData.put(appId, new AppI18n(appId));
            i18nData = appI18nData.get(appId).getI18nData();
        }
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
    public static boolean hasKey(String appId, String key) {
        Map<String, Map<String, String>> i18nData = getI18nData(appId);
        if (i18nData == null) {
            return false;
        }
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
    public static void create(String appId, String key, String defaultMessage) {
        // 如果应用ID为空，则使用默认的Pine应用ID
        if (StringUtils.isEmpty(appId)) {
            appId = SysConst.pineAppId;
        }
        // 创建一个SysI18n对象，用于存储国际化信息
        SysI18n sysI18n = new SysI18n();
        // 设置应用id
        sysI18n.setAppId(appId);
        // 设置国际化信息的键值
        sysI18n.setI18nKey(key);
        // 创建一个HashMap，用于存储不同语言的消息
        Map<String, String> i18nMap = new HashMap<>();
        // 将默认消息作为中文消息添加到映射中
        i18nMap.put("zh_CN", defaultMessage);
        // 将消息映射序列化为JSON字符串，并设置为SysI18n对象的消息
        sysI18n.setMessage(JsonUtil.toJson(i18nMap));
        // 将SysI18n对象保存到数据库
        DB.save(sysI18n);
        // 将国际化数据添加到全局数据中
        Map<String, Map<String, String>> i18nData = getI18nData(appId);
        if (i18nData == null) {
            appI18nData.put(appId, new AppI18n(appId));
            i18nData = appI18nData.get(appId).getI18nData();
        }
        i18nData.put(key, i18nMap);
    }

    /**
     * 根据应用程序ID、键和语言获取相应的国际化消息
     *
     * @param appId 应用程序的ID，用于区分不同的消息数据源
     * @param key 消息的唯一键，用于查找特定消息
     * @param lang 语言代码，用于获取特定语言的消息
     * @return 返回对应语言的消息字符串，如果找不到则返回null
     */
    public static String getMessage(String appId, String key, String lang) {
        // 从指定的应用程序ID获取国际化消息数据
        Map<String, Map<String, String>> i18nData = getI18nData(appId);
        if (i18nData == null) {
            i18nData = getI18nData(SysConst.pineAppId);
        }

        // 初始化消息变量
        String message = null;

        // 尝试从特定应用程序ID的消息数据中获取消息
        if (i18nData.containsKey(key)) {
            message = i18nData.get(key).get(lang);
        }

        // 如果消息为空且语言代码不为空，则尝试从公共消息数据中获取消息
        if (StringUtils.isEmpty(message)) {
            Map<String, Map<String, String>> publicI18nData = getI18nData(SysConst.pineAppId);
            if (publicI18nData.containsKey(key)) {
                message = publicI18nData.get(key).get(lang);
            }
        }

        // 返回获取到的消息
        return message;
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
    public static String t(String appId, String key, String defaultMessage, Object... params) {
        try {
            // 初始化消息为默认值
            String message = defaultMessage;
            // 如果国际化数据中包含给定的键
            Map<String, Map<String, String>> i18nData = getI18nData(appId);
            if (i18nData == null) {
                i18nData = getI18nData(SysConst.pineAppId);
            }
            if (i18nData.containsKey(key)) {
                // 尝试用特定语言获取消息
                String str = getMessage(appId, key, lang());
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
            if (params == null || params.length == 0) {
                return message;
            }
            // 使用参数格式化最终的消息，并返回
            return MessageFormat.format(message, params);
        }
        // 捕获并处理任何发生的异常，简单返回键作为后备计划
        catch (Exception e) {
            return key;
        }
    }

    /**
     * 根据键、默认消息和参数对象数组进行翻译处理
     * 此方法首先获取当前应用的ID，然后使用该ID、键、默认消息和参数对象数组调用自身进行翻译处理
     * 主要用于在不知道具体应用ID的情况下，从当前上下文中获取应用ID并进行翻译
     *
     * @param key           需要翻译的键
     * @param defaultMessage 默认消息，当键不存在时使用
     * @param params        翻译所需的参数对象数组
     * @return 翻译后的字符串
     */
    public static String t(String key, String defaultMessage, Object... params) {
        // 获取当前应用的ID
        String appId = KClientContext.getCurrentAppId();
        if (StringUtils.isEmpty(appId)) {
            appId = SysConst.pineAppId;
        }
        // 使用获取的应用ID、键、默认消息和参数对象数组进行翻译处理
        return t(appId, key, defaultMessage, params);
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
    public static String parseScript(String appId, String script) {
        if (StringUtils.isEmpty(appId)) {
            appId = SysConst.pineAppId;
        }
        if (StringUtils.isEmpty(script)) {
            return script;
        }
        if (script.contains("已关闭")) {
            System.currentTimeMillis();
        }
        // 适配原先的写法
        if (script.contains("$") && script.contains("}") && script.contains("{") && script.contains("|") && script.contains("i18n")) {
            return script;
        }
        // 匹配 t('key', 'default') 或 t("key", "default") 的正则表达式
        Pattern pattern = Pattern.compile("t\\((['\"])(.*?)\\1,\\s*(['\"])(.*?)\\3\\)");
        Matcher matcher = pattern.matcher(script);
        if (!matcher.find()) {
            // 如果没有找到 t() 调用，直接将 script 作为 key 进行翻译
            String key = script;
            if (script.length() > 20) {
                key = MD5Utils.md5(key);
                if(I18n.hasKey(appId, script) && !I18n.hasKey(appId, key)) {
                    DB.executeUpdateSql("update sys_i18n set i18n_key = ?  where i18n_key=? and app_id=?", key,  script, appId);
                    I18n.updateI18Key(script, key, appId);

                }
                else if(I18n.hasKey(appId, script) && I18n.hasKey(appId, key)) {
                    DB.executeUpdateSql("delete from  sys_i18n  where i18n_key=? and app_id=?", script, appId);
                    I18n.updateI18Key(script, key, appId);

                }

            }
            if (!I18n.hasKey(appId, key)) {
                boolean devMode = SpringContext.getBoolean("app.mode.dev", false);
                // 只要开发模式下才创建
                if (devMode) {
                    I18n.create(appId,key, script);
                }

            }
            String s =  I18n.t(appId, key, script);
            s = s.replace("\\ ${","\\${");
            s = s.replace("$ {","${");
            return s;
        }

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(2);         // 获取 i18n key
            String defaultValue = matcher.group(4); // 获取默认值

            // 查找国际化翻译
            String translatedValue = I18n.t(appId, key, defaultValue);

            // 将匹配的 t('key', 'default') 或 t("key", "default") 替换为翻译结果
            matcher.appendReplacement(result, translatedValue);
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static void translateAll() {
        List<String> i18ns = DB.findSingleAttributeList(String.class, "select id from sys_i18n where message not like '%en_US%' or message like '%\"en_US\":\"\",%' order by when_created asc");
        for (int i = 0; i < i18ns.size(); i++) {
            translate(i18ns.get(i));
            log.info("translate i18n: {}, 进度:{}/{}", i18ns.get(i), i+1, i18ns.size());
        }
    }

    public static SysI18n translate(String i18nId) {
        SysI18n sysI18n = DB.findById(SysI18n.class, i18nId);
        boolean changed = false;
        if (StringUtils.isNotEmpty(sysI18n.getMessage())) {
            try {
                Map<String, Object> datas = JsonUtil.toMap(sysI18n.getMessage());
                Object zh = datas.get("zh_CN");
                Object en = datas.get("en_US");
                Object cht = datas.get("zh_HK");
                if (zh == null) {
                    return sysI18n;
                }
                String zhStr = zh.toString();
                if (StringUtils.isEmpty(zhStr)) {
                    return sysI18n;
                }
                if (cht == null || StringUtils.isEmpty(cht.toString())) {
                    String result = BaiduTranslate.getTransResult(zhStr, "zh", "cht");
                    datas.put("zh_HK", result);
                    changed = true;
                }
                if (en == null || StringUtils.isEmpty(en.toString())) {
                    String result = BaiduTranslate.getTransResult(zhStr, "zh", "en");
                    datas.put("en_US", result);
                    changed = true;
                }
                if (changed) {
                    sysI18n.setMessage(JsonUtil.toJson(datas));
                }
            }
            catch (Exception ignored) {

            }
        }
        if (changed) {
            DB.update(sysI18n);
        }
        return sysI18n;

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

    public static void translateApp(String appId) {
        List<String> i18ns = DB.findSingleAttributeList(String.class, "select id from sys_i18n where message not like '%en_US%' or message like '%\"en_US\":\"\",%' and app_id=? order by when_created asc", appId);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < i18ns.size(); i++) {
            int finalI = i;
            int finalI1 = i;
            executorService.submit(() -> {
                translate(i18ns.get(finalI));
                log.info("translate i18n: {}, 进度:{}/{}", i18ns.get(finalI1), finalI1 +1, i18ns.size());
            });

        }
    }
}
