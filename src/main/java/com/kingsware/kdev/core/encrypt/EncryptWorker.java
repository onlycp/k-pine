package com.kingsware.kdev.core.encrypt;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.config.AlgorithmConfig;
import com.kingsware.kdev.core.encrypt.inst.AESInstance;
import com.kingsware.kdev.core.encrypt.inst.Base64Instance;
import com.kingsware.kdev.core.encrypt.inst.MD5Instance;

import java.util.Objects;

/**
 * 统一加解密的工作中心
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:34 下午
 */
public class EncryptWorker {

    /** 单实例 **/
    private static EncryptWorker INSTANCE;
    /** 加解密 **/
    private EncryptInterface encryptInterface;
    /** 加解密配置 **/
    private AlgorithmConfig  algorithmConfig;

    /**
     * 默认构造类
     */
    private EncryptWorker() {}

    /** 获取当前实例 **/
    public static EncryptWorker getInstance() {
        // 如果对象为空，需要对storage进行实例
        if (Objects.isNull(INSTANCE)) {
            synchronized (EncryptWorker.class) {
                // 创建实例
                INSTANCE = new EncryptWorker();
                // 创建具体加解密实例
                EncryptProperties encryptProperties = SpringContext.getBean(EncryptProperties.class);
                if ("base64".equalsIgnoreCase(encryptProperties.getMode())) {
                    INSTANCE.algorithmConfig = encryptProperties.getBase64();
                    INSTANCE.encryptInterface = new Base64Instance();
                }
                else if ("md5".equalsIgnoreCase(encryptProperties.getMode())) {
                    INSTANCE.algorithmConfig = encryptProperties.getMd5();
                    INSTANCE.encryptInterface = new MD5Instance();
                }
                else if ("aes".equalsIgnoreCase(encryptProperties.getMode())) {
                    INSTANCE.algorithmConfig = encryptProperties.getAes();
                    INSTANCE.encryptInterface = new AESInstance();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 加密
     * @param source    源串
     * @return          加密后的字符串
     */
    public String encrypt(String source) {
        return encryptInterface.encrypt(source,algorithmConfig);
    }

    /**
     * 对比校验结果
     * @param source        源串
     * @param encrypted     原先加密后的字符串
     * @return              结果
     */
    public boolean validate(String source, String encrypted) {
        return encryptInterface.validate(source, encrypted, algorithmConfig);
    }
}
