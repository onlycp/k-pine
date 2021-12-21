package com.kingsware.kdev.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES加密工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:28 下午
 */
public class AESUtil {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(AESUtil.class);

    /**
     * AES加密
     * @param src       源字符串
     * @param secret    密钥
     * @return          加密后的字符串
     */
    public static String encrypt(String src, String secret){
        // 如果密码为空，直接返回null
        if (StringUtils.isEmpty(secret)) {
            return null;
        }
        // 判断Key是否为16位
        if (secret.length() != 16) {
            logger.warn("AES加密失败，原因：密钥长度必须为：16");
            return null;
        }
        try {
            byte[] raw = secret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encrypted));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            logger.warn("AES加密失败，原因：" + e.getMessage());
            return null;
        }
    }

    /**
     *  解密字符串
     * @param src       源
     * @param secret    密钥
     * @return          解密后的字符串
     */
    public static String decrypt(String src, String secret) {
        try {
            // 如果密码为空，直接返回null
            if (StringUtils.isEmpty(secret)) {
                return null;
            }
            // 判断Key是否为16位
            if (secret.length() != 16) {
                logger.warn("解密失败，原因：密钥长度必须为：16");
                return null;
            }
            byte[] raw = secret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.getDecoder().decode(src);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                logger.warn("AES解密失败，原因：" + e.getMessage());
                return null;
            }
        } catch (Exception ex) {
            logger.warn("AES解密失败，原因：" + ex.getMessage());
            return null;
        }
    }
}
