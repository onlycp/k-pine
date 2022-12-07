package com.kingsware.kdev.core.util;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @description: 3des工具类
 **/
public class DesUtil {

    private static final String ALGORITHM = "DESede";
    //格式为：算法/工作模式/填充模式
    private static final String DESEDE_CBC_PADDING = "DESede/CBC/PKCS5Padding";
    private static final String DESEDE_ECB_PADDING = "DESede/ECB/PKCS5Padding";

    private static Key getSecretKey(byte[] key) {//
        try {
            //创建一个DESedeKeySpec密钥规范对象
            DESedeKeySpec spec = new DESedeKeySpec(key);
            //转换指定算法的密钥的SecretKeyFactory对象(这里是:desede)
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DesUtil.ALGORITHM);
            //根据提供的密码密钥规范生成SecretKey对象。
            return keyfactory.generateSecret(spec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("DES获取密钥出现错误,非法key异常");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("DES获取密钥出现错误,算法异常");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("DES获取密钥出现错误,密钥规范异常");
        }
    }

    /**
     * 3Des加密(CBC工作模式)
     *
     * @param key   密钥,key长度必须大于等于 3*8 = 24
     * @param keyIv 初始化向量,keyIv长度必须等于8
     * @param data  明文
     * @return 密文
     * @throws Exception
     */
    public static byte[] encodeByCBC(byte[] key, byte[] keyIv, byte[] data)
            throws Exception {
        //获取SecretKey对象
        Key secretKey = getSecretKey(key);
        //获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(DESEDE_CBC_PADDING);
        //创建向量参数规范也就是初始化向量
        IvParameterSpec ips = new IvParameterSpec(keyIv);
        //用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
        //执行加密操作
        return cipher.doFinal(data);
    }

    /**
     * 3Des加密(ECB工作模式)
     *
     * @param key   密钥,key长度必须大于等于 3*8 = 24
     * @param keyIv 初始化向量,keyIv长度必须等于8
     * @param data  密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] decodeByCBC(byte[] key, byte[] keyIv, byte[] data)
            throws Exception {
        //获取SecretKey对象
        Key secretKey = getSecretKey(key);
        //获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(DESEDE_CBC_PADDING);
        //创建向量参数规范也就是初始化向量
        IvParameterSpec ips = new IvParameterSpec(keyIv);
        //用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);
        //执行加密操作
        return cipher.doFinal(data);
    }

    /**
     * 3Des加密(ECB工作模式),不要IV
     *
     * @param key  密钥,key长度必须大于等于 3*8 = 24
     * @param data 明文
     * @return 密文
     * @throws Exception
     */
    public static byte[] encodeByECB(byte[] key, byte[] data) throws Exception {
        //获取SecretKey对象
        Key secretKey = getSecretKey(key);
        //获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(DESEDE_ECB_PADDING);
        //用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        //执行加密操作
        return cipher.doFinal(data);
    }

    /**
     * 3Des解密(ECB工作模式),不要IV
     *
     * @param key  密钥,key长度必须大于等于 3*8 = 24
     * @param data 密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] decodeByECB(byte[] key, byte[] data) throws Exception {
        //获取SecretKey对象
        Key secretKey = getSecretKey(key);
        //获取指定转换的密码对象Cipher（参数：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(DESEDE_ECB_PADDING);
        //用密钥和一组算法参数规范初始化此Cipher对象（加密模式）
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        //执行加密操作
        return cipher.doFinal(data);
    }
}
