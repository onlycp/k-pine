package com.kingsware.kdev.core.util;

/**
 * 数字工具
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/20 10:42 上午
 */
public class NumberUtils {

    private NumberUtils() {}

    /**
     * 是否数字
     * @param str   原始
     * @return      是否
     */
    public static boolean isParsable(final String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        if (str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            return withDecimalsParsing(str, 1);
        }
        return withDecimalsParsing(str, 0);
    }

    private static boolean withDecimalsParsing(final String str, final int beginIdx) {
        int decimalPoints = 0;
        for (int i = beginIdx; i < str.length(); i++) {
            final boolean isDecimalPoint = str.charAt(i) == '.';
            if (isDecimalPoint) {
                decimalPoints++;
            }
            if (decimalPoints > 1) {
                return false;
            }
            if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
