package com.kingsware.kdev.core.i18n;

import com.kingsware.kdev.core.util.StringUtils;

import java.text.MessageFormat;

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
        return MessageFormat.format(defaultMessage, params);
    }
}
