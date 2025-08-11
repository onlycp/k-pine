package com.kingsware.kdev.core.encrypt;

import com.kingsware.kdev.core.encrypt.config.AlgorithmConfig;

/**
 *
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:39 下午
 */
public interface EncryptInterface {


    /**
     *  加密数据
     * @param source    源串
     * @param config     配置
     * @return          加密后的数据
     */
    String encrypt(String source, AlgorithmConfig config, String slat);

    /**
     *  校验结果
     * @param source    源串
     * @param encrypted 已加密的字符串
     * @param config     配置
     * @return          加密后的数据
     */
    boolean validate(String source, String encrypted, AlgorithmConfig config, String slat);

}
