package com.kingsware.kdev.core.util;

import org.mozilla.universalchardet.UniversalDetector;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    // 常见中文相关编码列表
    private static final String[] COMMON_ENCODINGS = {
            "UTF-8", "GBK", "GB2312", "ISO-8859-1", "Windows-1252", "Big5"
    };


    // 中文字符正则表达式（基本范围）
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4E00-\\u9FA5]");

    /**
     * 检测字节数组的编码（使用 juniversalchardet）
     */
    public static String detectEncoding(byte[] bytes) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding;
    }

    /**
     * 将字节数组转换为目标编码的字符串
     */
    public static String convertBytes(byte[] bytes, String targetEncoding) {
        try {
            // 1. 检测原始编码
            String detectedEncoding = detectEncoding(bytes);
            if (detectedEncoding == null) {
                detectedEncoding = "ISO-8859-1"; // 默认回退
            }

            // 2. 按检测到的编码解码
            String decodedString = new String(bytes, detectedEncoding);

            // 3. 重新编码为目标编码
            return new String(decodedString.getBytes(targetEncoding), targetEncoding);

        } catch (UnsupportedEncodingException e) {
            // 编码不支持时回退
            return new String(bytes, Charset.defaultCharset());
        }
    }
    /**
     * 检测字节数组的可能编码并转换为目标编码（默认为 UTF-8）
     */
    public static String autoConvert(byte[] bytes, String targetEncoding) {
        Map<String, Integer> encodingScores = new HashMap<>();

        // 遍历所有编码，计算每种编码的中文字符数量
        for (String encoding : COMMON_ENCODINGS) {
            try {
                String decoded = new String(bytes, encoding);
                int chineseCount = countChineseCharacters(decoded);
                encodingScores.put(encoding, chineseCount);
            } catch (UnsupportedEncodingException e) {
                // 忽略不支持的编码
            }
        }

        // 找到中文字符最多的编码
        String bestEncoding = encodingScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("ISO-8859-1"); // 默认回退

        try {
            // 用最佳编码解码，再转换为目标编码
            String decoded = new String(bytes, bestEncoding);
            return new String(decoded.getBytes(targetEncoding), targetEncoding);
        } catch (UnsupportedEncodingException e) {
            return new String(bytes, Charset.defaultCharset());
        }
    }

    /**
     * 检测字符串是否为乱码（简易版）
     */
    public static boolean isLikelyCorrupted(String text) {
        return !CHINESE_PATTERN.matcher(text).find()
                && containsInvalidSequences(text);
    }

    // 统计中文字符数量
    private static int countChineseCharacters(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (CHINESE_PATTERN.matcher(String.valueOf(c)).find()) {
                count++;
            }
        }
        return count;
    }

    // 检测非法字符（如替换字符 �）
    private static boolean containsInvalidSequences(String text) {
        return text.contains("�") || text.contains("?")
                || text.matches(".*[\\x00-\\x08\\x0B-\\x0C\\x0E-\\x1F].*");
    }


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

    // 示例用法
    public static void main(String[] args) throws Exception {
        // 模拟乱码：UTF-8 字节被错误地用 ISO-8859-1 解码
        String original = "你好，世界！";
        byte[] utf8Bytes = original.getBytes("UTF-8");
        String corrupted = new String(utf8Bytes, "ISO-8859-1"); // 错误编码

        // 自动修复
        String fixed = convertBytes(corrupted.getBytes(), "UTF-8");
        System.out.println("修复前: " + corrupted); // 乱码输出
        System.out.println("修复后: " + fixed);     // 正确输出 "你好，世界！"
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
    // 判断字符串中是否包含中文字符
    public static boolean containsChinese(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        // 正则表达式匹配中文字符
        String regex = "[\\u4E00-\\u9FFF]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        // 查找字符串中是否包含中文字符
        return matcher.find();
    }

    /**
     * 将字符串的第一个字母大写
     * 如果输入字符串为空或长度为0，返回原字符串
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串，如果第一个字母不是大写，则将其转为大写；否则返回原字符串
     */
    public static String capitalizeFirstLetter(String str) {
        // 检查字符串是否为空或长度为0，如果是，则直接返回原字符串
        if (str == null || str.isEmpty()) {
            return str;
        }
        // 将字符串的第一个字母大写，然后与剩余部分拼接
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    /**
     * 检查给定的字符串是否为XML格式
     *
     * @param text 待检查的字符串
     * @return 如果字符串是XML格式，则返回true；否则返回false
     */
    public static Document parseXml(String text) {
        try {
            // 创建文档构建工厂的实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 使用工厂创建一个新的文档构建器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 创建一个输入源，使用字符串读取器将给定的字符串作为输入
            InputSource source = new InputSource(new StringReader(text));
            // 使用构建器解析输入源，生成文档对象
            Document doc = builder.parse(source);
            return doc;
        } catch (Exception e) {
            return null; // 解析失败，不是XML格式
        }
    }

    // HTML5 标准自闭合标签列表（Void Elements）
    private static final Set<String> SELF_CLOSING_TAGS;

    static {
        Set<String> strings = new HashSet<>();
        strings.add("area");
        strings.add("base");
        strings.add("br");
        strings.add("col");
        strings.add("embed");
        strings.add("hr");
        strings.add("img");
        strings.add("input");
        strings.add("link");
        strings.add("meta");
        strings.add("param");
        strings.add("source");
        strings.add("track");
        strings.add("wbr");
        SELF_CLOSING_TAGS = new HashSet<>(strings);
    }

    // 预编译正则表达式提升性能
    private static final Pattern TAG_PATTERN = Pattern.compile(
            "(<\\!--.*?-->)|" +                 // 匹配注释
                    "(<!\\[CDATA\\[.*?\\]\\]>)|" +      // 匹配CDATA块
                    "(</?\\s*([a-zA-Z]+)([^>]*)>)",     // 匹配标签（增强版）
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );


    public static String fixUnclosedTags(String html) {
        if (html == null || html.isEmpty()) return html;

        Matcher matcher = TAG_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            // 处理注释和CDATA（直接保留）
            if (matcher.group(1) != null || matcher.group(2) != null) {
                matcher.appendReplacement(sb, matcher.group());
                continue;
            }

            String fullTag = matcher.group(3);    // 完整标签
            String tagName = matcher.group(4).toLowerCase(); // 标签名转小写
            String attributes = matcher.group(5).trim();

            // 判断是否需要处理
            if (shouldCloseTag(fullTag, tagName)) {
                String fixedTag = buildFixedTag(fullTag, tagName, attributes);
                matcher.appendReplacement(sb, fixedTag);
            } else {
                matcher.appendReplacement(sb, fullTag);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static boolean shouldCloseTag(String fullTag, String tagName) {
        // 跳过闭合标签和已正确闭合的标签
        return fullTag.startsWith("<") &&
                !fullTag.startsWith("</") &&
                !fullTag.endsWith("/>") &&
                SELF_CLOSING_TAGS.contains(tagName);
    }

    private static String buildFixedTag(String originalTag, String tagName, String attributes) {
        // 保留原始标签大小写格式
        String originalCaseTag = originalTag.replaceAll("(?i)"+tagName, tagName);

        // 构建新标签（保留原始空格格式）
        return originalCaseTag
                .replaceFirst(">", attributes.isEmpty() ? " />" : " " + attributes + " />");
    }


    public static String documentToString(Document document) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
