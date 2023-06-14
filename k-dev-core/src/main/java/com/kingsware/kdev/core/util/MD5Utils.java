package com.kingsware.kdev.core.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 3:00 下午
 */
@Slf4j
public class MD5Utils {

    /**
     * 将数据进行 MD5 加密，并以16进制字符串格式输出
     * @param source    源串
     * @return          加密后的结果
     */
    public static String md5(String source) {

        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(source.getBytes(StandardCharsets.UTF_8));
            // 将字节数据转换为十六进制
            for (byte b : md5) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                sb.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("error", e);
        }
        return sb.toString();
    }
}
