package com.kingsware.kdev.core.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;

import java.util.HashMap;
import java.util.Map;


public class SM2Utils {

    private static String PRIVATE_KEY = "0aead7d94ab32e44a1a4d1db0a6509ea5997ccf94eea4f000247018a2799d4fa";
    private static String PUBLIC_KEY = "046faa39d46d34549a5a19fc2799345942d8afca02ee66ce09628bc5237c5dacd3bf4a1aaa2ec8cbc97fcf10c5f4883c3e8e4bd1665a1b77469c4ed3410497eebf";

    public static void main(String[] args) throws Exception {
        Map<String, String> map = generateKeyPair();
        Console.log("privateKey: {}", map.get("privateKey"));
        Console.log("privateKey: {}", map.get("publicKey"));
    }

    public static Map<String, String> generateKeyPair() {
        Map<String, String> map = new HashMap<>();
        cn.hutool.crypto.asymmetric.SM2 sm2 = SmUtil.sm2();
        // 私钥：这个保存好，切记不要泄漏，真的泄露了就重新生成一下
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        // 公钥：这个是前后端加密用的，不压缩选择带04的，不带04到时候前端会报错
        byte[] publicKey = ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false);
        map.put("privateKey", HexUtil.encodeHexStr(privateKey));
        map.put("publicKey", HexUtil.encodeHexStr(publicKey));
        return map;
    }

    /**
     * sm2明文加密
     * PRIVATE_KEY:生成的私钥
     * PUBLIC_KEY：生成的公钥
     * @param data 加密前的明文
     * @return 加密后的密文
     */
    public static String encryptData(String data, String privateKey, String publicKey) {
        cn.hutool.crypto.asymmetric.SM2 sm2 = SmUtil.sm2(ECKeyUtil.toSm2PrivateParams(privateKey), ECKeyUtil.toSm2PublicParams(publicKey));
        String encryptBcd = sm2.encryptBcd(data, KeyType.PublicKey);
        // 这里的处理前端也可以处理，这个就看怎么约定了，其实都无伤大雅
        if (StrUtil.isNotBlank(encryptBcd)) {
            // 生成的加密密文会带04，因为前端sm-crypto默认的是1-C1C3C2模式，这里需去除04才能正常解密
            if (encryptBcd.startsWith("04")) {
                encryptBcd = encryptBcd.substring(2);
            }
            // 前端解密时只能解纯小写形式的16进制数据，这里需要将所有大写字母转化为小写
            encryptBcd = encryptBcd.toLowerCase();
        }
        return encryptBcd;
    }

    /**
     * sm2密文解密
     * PRIVATE_KEY:生成的私钥
     * PUBLIC_KEY：生成的公钥
     * @param encryptData 加密密文
     * @return 解密后的明文字符串
     */
    public static String decryptData(String encryptData, String privateKey, String publicKey) throws Exception {
        if (StrUtil.isBlank(encryptData)) {
            throw new RuntimeException("解密串为空，解密失败");
        }
        cn.hutool.crypto.asymmetric.SM2 sm2 = SmUtil.sm2(ECKeyUtil.toSm2PrivateParams(privateKey), ECKeyUtil.toSm2PublicParams(publicKey));
        // BC库解密时密文开头必须带04，如果没带04则需补齐
        if (!encryptData.startsWith("04")) {
            encryptData = "04".concat(encryptData);
        }
        byte[] decryptFromBcd = sm2.decryptFromBcd(encryptData, KeyType.PrivateKey);
        if (decryptFromBcd != null && decryptFromBcd.length > 0) {
            return StrUtil.utf8Str(decryptFromBcd);
        } else {
            throw new Exception("密文解密失败");
        }
    }

}

