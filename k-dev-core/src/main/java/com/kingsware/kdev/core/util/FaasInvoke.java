package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;

import java.util.Map;

public class FaasInvoke {

    private static String privateKey = "009308ea577dad81c8ef52b5b98c9ed80d0aedef8256914cd31708b6a470d313c3";
    private static String publicKey = "0492073b8f6b9efed4fc75ce6f9dbf0359c451e3d128fff000d5414d6cf50304c71f326854f024b00584abd999a6c0fabb10c95ee21658f5b186678b7c9614f97f";

    /**
     * 使用SM2算法对登录信息进行解密
     *
     * @param data 加密后的登录信息
     * @return 解密后的登录信息
     * @throws Exception 如果解密过程出现错误，则抛出异常
     */
    public static String loginDecrypt(String data) throws Exception {
        // 根据私钥和公钥准备解密脚本
        String script = String.format("kutils.sm2Decode('%s', '%s', '%s');", data, privateKey, publicKey);
        // 调用数据库API执行解密脚本
        KdbRet<String> ret = DB.kdbApi().executeScript(script);
        // 返回解密后的信息
        return ret.getResponseBody();
    }

    /**
     * 使用SM2算法对登录信息进行加密
     *
     * @param data 需要加密的登录信息
     * @return 加密后的登录信息
     * @throws Exception 如果加密过程出现错误，则抛出异常
     */
    public static String loginEncrypt(String data) throws Exception {
        // 根据私钥和公钥准备加密脚本
        String script = String.format("kutils.sm2Encode('%s', '%s', '%s');", data, privateKey, publicKey);
        // 调用数据库API执行加密脚本
        KdbRet<String> ret = DB.kdbApi().executeScript(script);
        // 返回加密后的信息
        return ret.getResponseBody();
    }

    /**
     * 从缓存中获取指定键的值。
     *
     * @param key 缓存中的键。
     * @return 键对应的值，如果键不存在或缓存已过期，则可能返回空字符串。
     */
    public static String getCache(String key) {
        // 构造调用KDB缓存获取脚本的字符串
        String script = String.format("kutils.getCache('k-pine-app','%s');", key);
        // 执行脚本并返回结果
        KdbRet<String> ret = DB.kdbApi().executeScript(script);
        // 返回脚本执行的结果
        return ret.getResponseBody();
    }

    /**
     * 设置缓存项，带过期时间。
     *
     * @param key 缓存中的键。
     * @param value 缓存中的值。
     * @param expire 缓存项的过期时间，以秒为单位。
     */
    public static void setCache(String key, String value, Long expire) {
        // 构造调用KDB缓存设置脚本的字符串，包括过期时间
        String script = String.format("kutils.setCache('k-pine-app','%s','%s', %d);", key, value, expire);
        // 执行脚本
        DB.kdbApi().executeScript(script);
    }

    /**
     * 设置缓存项，不带过期时间。
     *
     * @param key 缓存中的键。
     * @param value 缓存中的值。
     */
    public static void setCache(String key, String value) {
        // 构造调用KDB缓存设置脚本的字符串，不包括过期时间
        String script = String.format("kutils.setCache('k-pine-app','%s','%s');", key, value);
        // 执行脚本
        DB.kdbApi().executeScript(script);
    }

    /**
     * 从缓存中移除指定的键。
     *
     * @param key 要移除的缓存键。
     */
    public static void removeCache(String key) {
        // 构造调用KDB缓存移除脚本的字符串
        String script = String.format("kutils.removeCache('k-pine-app','%s');", key);
        // 执行脚本
        DB.kdbApi().executeScript(script);
    }


    public static KdbFlowResult callFlow(String id, Map<String, Object> variables) {
        KFlowContext context = KFlowContext.createBaseContext("{}", "{}", null);
        variables.put("request", ServletUtil.getRequestData());
        String s = JsonUtil.toJson(variables);
        KdbFlowResult kdbFlowResult = KdbFlowExecutor.getInstance().execute(id, "", variables, context, false, false);
        return kdbFlowResult;
    }

}
