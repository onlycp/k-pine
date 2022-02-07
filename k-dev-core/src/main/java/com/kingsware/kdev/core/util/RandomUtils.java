package com.kingsware.kdev.core.util;

import java.util.Random;

/**
 * 生成随机数字及字符串
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/17 1:39 下午
 */
public class RandomUtils {

    /** 随机实例 **/
    private static final Random RANDOM = new Random();

    /**
     * 私有构造函数
     */
    private RandomUtils() {}

    /**
     * 生成随机数字字符串
     * 示例：
     * <pre>
     *     String str = RandomUtils.randomNumeric(5);
     * </pre>
     * @param count 数字个数
     * @return  随机数字字符串
     */
    public static String randomNumeric(int count) {
        return rand(0x30, 0x39, count);
    }

    /**
     * 生成随机字符串
     * @param count     数量
     * @return          随机字符串
     */
    public static String randString(int count) {
        // 生成样本字符串
        StringBuilder chars = new StringBuilder();
        // 收集大写字母
        for (int ch = 0x41; ch<=0x5A; ch++ ) {
            chars.append(ch);
        }
        // 收集小写字母
        for (int ch = 0x41; ch<=0x5A; ch++ ) {
            chars.append(ch);
        }
        // 返回随机字符串
        return randString(count, chars.toString());
    }


    /**
     * 生成随机字符串
     * @param count     数量
     * @param chars     样本字符
     * @return          随机字符串
     */
    public static String randString(int count, String chars) {
        // 获取样本字符串的长度，用于随机用
        int length = chars.length();
        // 结果字符串构建器
        StringBuilder builder = new StringBuilder(count);
        // 遍历生成字符串
        for (int i = 0; i < count; i++) {
            int chIndex = RANDOM.nextInt(length);
            int ch = chars.charAt(chIndex);
            builder.append(ch);
        }
        return builder.toString();

    }

    /**
     * 生成随机数字串
     * @param start     assi码开始位
     * @param end       assi码结束位
     * @param count     数量
     * @return      随机字符串
     */
    private static String rand(int start, int end, int count) {
        // 如果开始位大于结束位，返回null
        if (start > end) {
            return null;
        }
        // 如果数量小于或等于0，返回空字符串
        if (count <= 0) {
            return "";
        }
        // 生成随机字符串
        StringBuilder builder = new StringBuilder(count);
        int gap = end - start;
        for (int i = 0; i< gap; i++) {
            int ch = RANDOM.nextInt(gap) + start;
            builder.append(ch);
        }
        return builder.toString();
    }
}
