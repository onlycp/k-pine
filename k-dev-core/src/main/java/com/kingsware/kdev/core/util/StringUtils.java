package com.kingsware.kdev.core.util;

import org.apache.commons.lang3.CharUtils;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern linePattern = Pattern.compile("_(\\w)");
    private static final Pattern humpPattern = Pattern.compile("[A-Z]");

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
     * 是否可以打印
     * @param ch 字符
     * @return 是否
     */
    public static boolean isAsciiPrintable(final char ch) {
        return ch >= 32 && ch < 127;
    }

    /**
     * 字符串是否可以打印
     * @param cs 字符串
     * @return 是否
     */
    public static boolean isAsciiPrintable(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!CharUtils.isAsciiPrintable(cs.charAt(i))) {
                return false;
            }
        }
        return true;
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


    /**
     * 下划线转驼峰
     * @param str   原始字符串
     * @return      驼峰字符串
     */
    public static String lineToHump(String str) {
        if (!str.contains("_")) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * @param str 驼峰字符串
     * @return    下划线字符串
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 获取字符串长度
     * @param cs    字符串
     * @return      长度
     */
    public static int length(final String cs) {
        return cs == null ? 0 : cs.length();
    }


    /**
     * 首字母大写
     * @param str   字符串
     * @return      首字母大写的字符串
     */
    public static String capitalize(final String str) {
        final int strLen = length(str);
        if (strLen == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint;
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint;
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }

    /**
     * 首字母小写
     * @param str   字符串
     * @return      首字母小写的字符串
     */
    public static String uncapitalize(final String str) {
        final int strLen = length(str);
        if (strLen == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toLowerCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            return str;
        }

        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint;
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint;
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }


    /**
     * 生成uuid并返回
     * @return  id
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }



}
