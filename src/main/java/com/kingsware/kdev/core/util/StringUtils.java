package com.kingsware.kdev.core.util;

import java.util.List;

/**
 * 字符串处理工具类
 * 示例:
 * <pre>
 *    StringUtils.isEmpty(str);
 * </pre>
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/17 12:35 下午
 */
public class StringUtils {

    /**
     * 私有构建函数
     */
    private StringUtils() {}

    /**
     * 判断字符串为空
     *
     * @param     cs  传入字符串
     * @return    是否为空结果
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符串不为空
     *
     * @param     cs  传入字符串
     * @return    是否为空结果
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     *  将列表转为字符串
     * @param list          列表
     * @param separator     分隔符
     * @param startIndex    起始序号
     * @param endIndex      截止序号
     * @return  拼接后的字符串
     */
    public static String joinToString(List<?> list, String separator, int startIndex, int endIndex) {
        // 列表为空，返回空字符串
        if (list == null) {
            return null;
        }
        // 当startIndex小于0时，或endIndex > （list.size() + 1)时，返回空
        if (startIndex < 0 || endIndex > list.size() + 1) {
            return null;
        }
        // 当传入的startIndex <= endIndex时，返回空
        if (startIndex <= endIndex) {
            return null;
        }
        // 获取子列表
        List<?> subList =  list.subList(startIndex, endIndex);
        // 返回
        return joinToString(subList, separator);
    }

    /**
     *  将列表转为字符串
     * @param list          列表
     * @param separator     分隔符
     * @return  拼接后的字符串
     */
    public static String joinToString(List<?> list, String separator) {
        // 如果为空，返回null
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return "";
        }
        // 遍历拼接字符串
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
            // 如果不是最后一个元素，那么就加上分隔符
            if (i != (list.size() -1)) {
                stringBuffer.append(separator);
            }
        }
        return stringBuffer.toString();
    }


}
