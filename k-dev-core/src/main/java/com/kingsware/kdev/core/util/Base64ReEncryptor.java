package com.kingsware.kdev.core.util;

public class Base64ReEncryptor {
    // 加密方法
    public static String encryptBase64(String base64Str, int groupSize, String positionSequence) {
        // 将位置序列转换为整数数组
        int[] posList = new int[positionSequence.length()];
        for (int i = 0; i < positionSequence.length(); i++) {
            posList[i] = Character.getNumericValue(positionSequence.charAt(i));
        }
        int seqLen = posList.length;

        // 将输入字符串转为字符数组
        char[] chars = base64Str.toCharArray();
        int strLen = chars.length;

        // 创建结果数组
        char[] result = chars.clone();

        // 按分组大小处理
        for (int start = 0; start < strLen; start += groupSize) {
            int end = Math.min(start + groupSize, strLen);
            int groupLen = end - start;
            char[] group = new char[groupLen];
            System.arraycopy(result, start, group, 0, groupLen);

            for (int i = 0; i < groupLen; i++) {
                int shift = posList[i % seqLen];
                int newPos = (i + shift) % groupLen;
                result[start + newPos] = group[i];
            }
        }
        return new String(result);
    }

    // 解密方法
    public static String decryptBase64(String encryptedStr, int groupSize, String positionSequence) {
        // 将位置序列转换为整数数组
        int[] posList = new int[positionSequence.length()];
        for (int i = 0; i < positionSequence.length(); i++) {
            posList[i] = Character.getNumericValue(positionSequence.charAt(i));
        }
        int seqLen = posList.length;

        // 将输入字符串转为字符数组
        char[] chars = encryptedStr.toCharArray();
        int strLen = chars.length;

        // 创建结果数组
        char[] result = chars.clone();

        // 按分组大小处理
        for (int start = 0; start < strLen; start += groupSize) {
            int end = Math.min(start + groupSize, strLen);
            int groupLen = end - start;
            char[] group = new char[groupLen];
            System.arraycopy(result, start, group, 0, groupLen);

            // 逆向计算原始位置
            for (int i = 0; i < groupLen; i++) {
                int shift = posList[i % seqLen];
                // 逆向位置：(i - shift + groupLen) % groupLen
                int originalPos = (i - shift + groupLen) % groupLen;
                result[start + originalPos] = group[i];
            }
        }
        return new String(result);
    }

    // 测试方法
    public static void main(String[] args) {
        String base64Sample = "SGVsbG8gV29ybGQ="; // "Hello World" 的base64编码
        String positionSeq = "453120";
        int[] groupSizes = {4, 6, 8};

        for (int groupSize : groupSizes) {
            String encrypted = encryptBase64(base64Sample, groupSize, positionSeq);
            String decrypted = decryptBase64(encrypted, groupSize, positionSeq);
            System.out.println("分组大小: " + groupSize);
            System.out.println("原始base64: " + base64Sample);
            System.out.println("位置序列: " + positionSeq);
            System.out.println("加密结果: " + encrypted);
            System.out.println("解密结果: " + decrypted);
            System.out.println();
        }
    }
}