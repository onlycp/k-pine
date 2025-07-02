package com.kingsware.kdev.core.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            if (!StringUtils.isAsciiPrintable(cs.charAt(i))) {
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

    // 转义JSON字符串
    public static String escapeString(String input) {
        return input.replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\"", "\\\\\"");
    }

    /**
     * 获取集合的属性并join
     * @param list
     * @param fieldName
     * @param separator
     * @return
     */
    public static String joinFieldToString(List<?> list, String fieldName,  String separator) {
        List<Object> afterList = new ArrayList<>();
        for (Object obj: list) {
            afterList.add(Optional.ofNullable(BeanUtils.getFieldValue(fieldName, obj)).orElse(""));
        }
        return joinToString(afterList, separator);
    }


    /**
     *  将列表转为字符串
     * @param list          列表
     * @param separator     分隔符
     * @return  拼接后的字符串
     */
    public static String joinToString(List<?> list, String separator) {
        return joinToString(list, separator, "", "");
    }
    /**
     *  将列表转为字符串
     * @param list          列表
     * @param separator     分隔符
     * @return  拼接后的字符串
     */
    public static String joinToString(List<?> list, String separator, String prefix, String suffix) {
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
            stringBuffer.append(prefix);
            stringBuffer.append(list.get(i));
            stringBuffer.append(suffix);
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

    /**
     * 是否uuid
     * @param uuid  uuid
     * @return  返回是否
     */
    public static boolean isUuid(final String uuid) {
        String split ="-";
        String str = uuid;
        try {
            if (uuid.indexOf(split) > 0) {
                List<String> arr = new ArrayList<>();
                for (int i=0; i < str.length(); i++) {
                    arr.add(str.charAt(i) + "");
                }
                arr.add(8, split);
                arr.add(14, split);
                arr.add(24, split);
                str = StringUtils.joinToString(arr, "-");
            }

            try {
                UUID.fromString(str);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }


    }

    /**
     * 清理字符串
     * @param sql   sql
     * @return
     */
    public static String clean(String sql) {
        // 正则匹配{空格/换行/回车/制表符/换页符}
        final String regx = "\\s+|\t|\r|\n";
        Pattern patt = Pattern.compile(regx);
        Matcher m = patt.matcher(sql);
        return m.replaceAll(" ").trim();
    }

    /**
     * 分隔字符串
     * @param str
     * @param size
     * @return
     */
    public static List<String> subStringToArray(String str, int size) {
        int index = 0;
        int len = str.length();
        List<String> retList = new ArrayList<>();
        do {
            int startIndex = index;
            int endIndex = index + size;
            if ((index + size) >= len) {
                endIndex = len;
            }
            retList.add(str.substring(startIndex, endIndex));
            index = endIndex;
        }while (index<len);
        return retList;
    }


    /**
     * 精简字符串
     * @param str   原始字符串
     * @param size  字符串长度
     * @return      精简后的字符串
     */
    public static String retrench(String str, int size) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() <= size) {
            return str;
        }
        // 获取平均长度
        int avgSize = (size-4)/2;
        return str.substring(0, avgSize) + "...." + str.substring(str.length()-avgSize);
    }

    /**
     * 通用编码转换方法
     * @param str           原始字符串
     * @param sourceCharset 源编码
     * @param targetCharset 目标编码
     * @return              转换后的字符串
     */
    public static String convertEncoding(String str, String sourceCharset, String targetCharset) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        try {
            byte[] bytes = str.getBytes(sourceCharset);
            return new String(bytes, targetCharset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * UTF-8 转 GBK
     * @param str   UTF-8编码的字符串
     * @return      GBK编码的字符串
     */
    public static String utf8ToGbk(String str) {
        return convertEncoding(str, StandardCharsets.UTF_8.name(), "GBK");
    }

    /**
     * GBK 转 UTF-8
     * @param str   GBK编码的字符串
     * @return      UTF-8编码的字符串
     */
    public static String gbkToUtf8(String str) {
        return convertEncoding(str, "GBK", StandardCharsets.UTF_8.name());
    }

    /**
     * 获取字符串的字节数组（指定编码）
     * @param str       字符串
     * @param charset   编码
     * @return          字节数组
     */
    public static byte[] getBytes(String str, String charset) {
        if (StringUtils.isEmpty(str)) {
            return new byte[0];
        }
        try {
            return str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("获取字节数组失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从字节数组创建字符串（指定编码）
     * @param bytes     字节数组
     * @param charset   编码
     * @return          字符串
     */
    public static String fromBytes(byte[] bytes, String charset) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("从字节数组创建字符串失败: " + e.getMessage(), e);
        }
    }

}
