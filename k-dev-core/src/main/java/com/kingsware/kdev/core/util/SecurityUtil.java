package com.kingsware.kdev.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import java.security.SecureRandom;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * 安全工具类
 * 实现HTTP请求签名验证，支持多种哈希算法和HMAC算法
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/01/01
 */
@Slf4j
public class SecurityUtil {

    /**
     * 默认签名算法
     */
    public static final String DEFAULT_ALGORITHM = "SHA-256";

    /**
     * HMAC签名算法
     */
    public static final String HMAC_ALGORITHM = "HmacSHA256";

    /**
     * 生成HTTP请求签名
     *
     * @param body 请求体内容
     * @param timestamp 时间戳
     * @param secretKey 签名密钥
     * @return 签名字符串
     */
    public static String generateSignature(String body, long timestamp, String secretKey) {
        return generateSignature(body, timestamp, secretKey, DEFAULT_ALGORITHM);
    }

    /**
     * 生成HTTP请求签名（指定算法）
     *
     * @param body 请求体内容
     * @param timestamp 时间戳
     * @param secretKey 签名密钥
     * @param algorithm 签名算法
     * @return 签名字符串
     */
    public static String generateSignature(String body, long timestamp, String secretKey, String algorithm) {
        try {
            // 构建签名字符串：body + timestamp + secretKey
            StringBuilder signString = new StringBuilder();
            signString.append(body != null ? body : "");
            signString.append(timestamp);
            signString.append(secretKey);

            String signature;
            if (HMAC_ALGORITHM.equals(algorithm)) {
                signature = generateHmacSignature(signString.toString(), secretKey);
            } else {
                signature = generateHashSignature(signString.toString(), algorithm);
            }

            log.debug("生成签名 - 算法: {}, 签名字符串: {}, 签名: {}",
                     algorithm, signString.toString(), signature);
            return signature;

        } catch (Exception e) {
            log.error("生成签名失败", e);
            throw new RuntimeException("生成签名失败", e);
        }
    }

    /**
     * 生成哈希签名
     *
     * @param content 签名字符串
     * @param algorithm 哈希算法
     * @return 签名字符串
     */
    private static String generateHashSignature(String content, String algorithm) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    /**
     * 生成HMAC签名
     *
     * @param content 签名字符串
     * @param secretKey 密钥
     * @return HMAC签名字符串
     */
    private static String generateHmacSignature(String content, String secretKey) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance(HMAC_ALGORITHM);
        javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(
            secretKey.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
        mac.init(secretKeySpec);

        byte[] hmacBytes = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hmacBytes);
    }

    /**
     * 验证HTTP请求签名
     *
     * @param body 请求体内容
     * @param timestamp 时间戳
     * @param secretKey 签名密钥
     * @param signature 待验证的签名
     * @param expireSeconds 签名过期时间（秒）
     * @return 验证结果
     */
    public static boolean verifySignature(String body, long timestamp, String secretKey,
                                        String signature, int expireSeconds) {
        return verifySignature(body, timestamp, secretKey, signature, expireSeconds, DEFAULT_ALGORITHM);
    }

    /**
     * 验证HTTP请求签名（指定算法）
     *
     * @param body 请求体内容
     * @param timestamp 时间戳
     * @param secretKey 签名密钥
     * @param signature 待验证的签名
     * @param expireSeconds 签名过期时间（秒）
     * @param algorithm 签名算法
     * @return 验证结果
     */
    public static boolean verifySignature(String body, long timestamp, String secretKey,
                                        String signature, int expireSeconds, String algorithm) {
        try {
            // 检查时间戳是否过期
            if (!isTimestampValid(timestamp, expireSeconds)) {
                log.warn("签名已过期，当前时间: {}, 签名时间: {}, 过期时间: {}秒",
                        System.currentTimeMillis(), timestamp, expireSeconds);
                return false;
            }

            // 生成期望的签名
            String expectedSignature = generateSignature(body, timestamp, secretKey, algorithm);

            // 比较签名
            boolean isValid = expectedSignature.equalsIgnoreCase(signature);
            if (!isValid) {
                log.warn("签名验证失败，期望签名: {}, 实际签名: {}", expectedSignature, signature);
            }

            return isValid;
        } catch (Exception e) {
            log.error("验证签名失败", e);
            return false;
        }
    }

    /**
     * 检查时间戳是否有效
     *
     * @param timestamp 时间戳
     * @param expireSeconds 过期时间（秒）
     * @return 是否有效
     */
    public static boolean isTimestampValid(long timestamp, int expireSeconds) {
        long currentTime = System.currentTimeMillis();
        return Math.abs(currentTime - timestamp) <= expireSeconds * 1000;
    }

    /**
     * 生成HMAC-SHA256签名
     *
     * @param body 请求体内容
     * @param timestamp 时间戳
     * @param secretKey 签名密钥
     * @return HMAC-SHA256签名
     */
    public static String generateHmacSignature(String body, long timestamp, String secretKey) {
        return generateSignature(body, timestamp, secretKey, HMAC_ALGORITHM);
    }

    /**
     * 验证HMAC-SHA256签名
     *
     * @param body 请求体内容
     * @param timestamp 时间戳
     * @param secretKey 签名密钥
     * @param signature 待验证的签名
     * @param expireSeconds 签名过期时间（秒）
     * @return 验证结果
     */
    public static boolean verifyHmacSignature(String body, long timestamp, String secretKey,
                                            String signature, int expireSeconds) {
        return verifySignature(body, timestamp, secretKey, signature, expireSeconds, HMAC_ALGORITHM);
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

            /**
     * 内容加密
     *
     * @param content 原始内容
     * @param secretKey 密钥
     * @return 加密后的内容
     */
    public static String encrypt(String content, String secretKey) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        byte[] compressData = compressJSON( content);
        return Base64.getEncoder().encodeToString(compressData);
    }

            /**
     * 内容解密
     *
     * @param encryptedContent 加密后的内容
     * @param secretKey 密钥
     * @return 原始内容
     */
    public static String decrypt(String encryptedContent, String secretKey) {
        String decryptedContent = decompressJSON(Base64.getDecoder().decode(encryptedContent));
        return decryptedContent;
    }

    /**
     * 从密钥生成偏移量
     *
     * @param secretKey 密钥
     * @return 偏移量
     */
    private static int generateShiftFromKey(String secretKey) {
        if (secretKey == null || secretKey.isEmpty()) {
            return 13; // 默认偏移量
        }

        int shift = 0;
        for (char c : secretKey.toCharArray()) {
            shift += (int) c;
        }

        // 确保偏移量在合理范围内 (1-25)
        shift = Math.abs(shift) % 25 + 1;
        return shift;
    }

    /**
     * 混淆偏移量信息
     *
     * @param shift 偏移量
     * @return 混淆后的偏移量信息
     */
    private static String obfuscateShift(int shift) {
        // 将偏移量转换为36进制，然后进行简单混淆
        String base36 = Integer.toString(shift, 36).toUpperCase();

        // 添加一些混淆字符
        StringBuilder obfuscated = new StringBuilder();
        obfuscated.append("S");
        obfuscated.append(base36);
        obfuscated.append("H");

        return obfuscated.toString();
    }

    /**
     * 凯撒字符变换
     *
     * @param c 原始字符
     * @param shift 偏移量
     * @return 变换后的字符
     */
    private static char caesarShift(char c, int shift) {
        if (Character.isUpperCase(c)) {
            return (char) ('A' + (c - 'A' + shift) % 26);
        } else if (Character.isLowerCase(c)) {
            return (char) ('a' + (c - 'a' + shift) % 26);
        } else if (Character.isDigit(c)) {
            return (char) ('0' + (c - '0' + shift) % 10);
        } else {
            // 对于特殊字符，保持原样
            return c;
        }
    }

    /**
     * 计算加密内容的校验和
     *
     * @param content 内容
     * @return 校验和
     */
    private static String calculateChecksum(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash).substring(0, 6);
        } catch (Exception e) {
            return "000000";
        }
    }

    /**
     * 生成随机字符
     *
     * @return 随机字符
     */
    private static char generateRandomChar() {
        // 生成字母数字混合的随机字符
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        return chars.charAt(random.nextInt(chars.length()));
    }

        /**
     * 解混淆偏移量信息
     *
     * @param shiftInfo 混淆的偏移量信息
     * @return 原始偏移量
     */
    private static int deobfuscateShift(String shiftInfo) {
        log.info("开始解析偏移量信息: {}", shiftInfo);

        // 尝试直接解析
        if (shiftInfo.startsWith("S") && shiftInfo.endsWith("H")) {
            String base36 = shiftInfo.substring(1, shiftInfo.length() - 1);
            try {
                int result = Integer.parseInt(base36, 36);
                log.info("直接解析成功: {} -> {}", shiftInfo, result);
                return result;
            } catch (NumberFormatException e) {
                log.warn("无法解析偏移量信息: {}", shiftInfo);
            }
        }

        // 尝试查找S...H模式
        int sIndex = shiftInfo.indexOf('S');
        int hIndex = shiftInfo.lastIndexOf('H');
        if (sIndex >= 0 && hIndex > sIndex) {
            String base36 = shiftInfo.substring(sIndex + 1, hIndex);
            try {
                int result = Integer.parseInt(base36, 36);
                log.info("模式解析成功: {} -> {} (S位置:{}, H位置:{})", shiftInfo, result, sIndex, hIndex);
                return result;
            } catch (NumberFormatException e) {
                log.warn("无法解析偏移量信息: {}", shiftInfo);
            }
        }

        // 如果都失败了，尝试一些常见的偏移量
        log.warn("无法解析偏移量信息: {}, 使用默认偏移量", shiftInfo);
        return 13; // 默认偏移量
    }

    /**
     * 演示：HTTP签名
     * 这个方法展示了如何使用HTTP签名
     */
    public static void demonstrateSignature() {
        // 测试数据
        String body = "{\"name\":\"test\",\"data\":\"hello world\"}";
        long timestamp = System.currentTimeMillis();
        String secretKey = "mySecretKey123";

        log.info("=== HTTP签名演示 ===");
        log.info("原始数据: {}", body);
        log.info("时间戳: {}", timestamp);
        log.info("密钥: {}", secretKey);

        // 生成签名
        String signature = generateSignature(body, timestamp, secretKey, "SHA-256");
        log.info("生成的签名: {}", signature);

        // 验证签名
        boolean isValid = verifySignature(body, timestamp, secretKey, signature, 300);
        log.info("签名验证结果: {}", isValid);

        // 测试不同的密钥
        String differentKey = "differentKey456";
        String signature2 = generateSignature(body, timestamp, differentKey, "SHA-256");
        log.info("不同密钥生成的签名: {}", signature2);

        // 验证应该失败
        boolean isValid2 = verifySignature(body, timestamp, secretKey, signature2, 300);
        log.info("使用错误密钥验证结果: {}", isValid2);

        // 测试HMAC算法
        String hmacSignature = generateSignature(body, timestamp, secretKey, "HmacSHA256");
        log.info("HMAC签名: {}", hmacSignature);

        boolean hmacValid = verifySignature(body, timestamp, secretKey, hmacSignature, 300, "HmacSHA256");
        log.info("HMAC签名验证结果: {}", hmacValid);
    }
    /**
     * 压缩JSON字符串。
     *
     * @param jsonString 待压缩的JSON字符串。
     * @return 压缩后的字节数组。如果出现异常，则返回null。
     */
    public static byte[] compressJSON(String jsonString) {
        try {
            // 将JSON字符串转为字节数组
            byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);

            // 使用 DeflaterOutputStream 直接压缩数据
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream)) {
                // 将数据写入压缩输出流
                deflaterOutputStream.write(jsonBytes);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("压缩失败", e);
            return null;
        }
    }

    /**
     * 解压缩字节数组为JSON字符串。
     *
     * @param compressedData 待解压缩的字节数组。
     * @return 解压缩后的JSON字符串。如果出现异常，则返回null。
     */
    public static String decompressJSON(byte[] compressedData) {
        try {
            // 使用 InflaterInputStream 直接解压缩数据
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
            try (InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream)) {
                // 读取并解压缩数据到输出流中
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inflaterInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                return outputStream.toString(StandardCharsets.UTF_8.name());
            }
        } catch (IOException e) {
            log.error("解压缩失败", e);
            return null;
        }
    }



    /**
     * 演示：内容加密解密
     * 这个方法展示了如何使用内容加密解密功能
     */
    public static void demonstrateEncryption() {
        String originalText = "{\"testParam\":\"testValue\",\"timestamp\":1753861610113}";
        String secretKey = "JRc7ciSE2n75sJf4bY3RK56Y";

        log.info("=== 内容加密解密演示 ===");
        log.info("原始文本: {}", originalText);
        log.info("密钥: {}", secretKey);

        // 测试压缩解压缩
        log.info("=== 测试压缩解压缩 ===");
        byte[] compressed = compressJSON(originalText);
        if (compressed != null) {
            String decompressed = decompressJSON(compressed);
            log.info("压缩解压缩测试: {}", originalText.equals(decompressed));
        }

        // 加密
        String encrypted = encrypt(originalText, secretKey);
        log.info("加密后: {}", encrypted);

        // 解密
        String decrypted = decrypt(encrypted, secretKey);
        log.info("解密后: {}", decrypted);

        // 验证
        boolean isCorrect = originalText.equals(decrypted);
        log.info("加密解密验证: {}", isCorrect);

        // 测试不同密钥
        String differentKey = "differentKey456";
        String encrypted2 = encrypt(originalText, differentKey);
        log.info("不同密钥加密: {}", encrypted2);

        // 使用错误密钥解密应该失败
        String wrongDecrypted = decrypt(encrypted, differentKey);
        log.info("错误密钥解密: {}", wrongDecrypted);
        log.info("错误密钥解密验证: {}", originalText.equals(wrongDecrypted));
    }
}
