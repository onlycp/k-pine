package com.kingsware.kdev.core.cache.open;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 开放接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/11 11:30 AM
 */
public class OpenApiManager {
    private static OpenApiManager instance;
    /** 接入商以及权限接口 **/
    private Map<String, OpenAccountInfo> accessor = new HashMap<>();

    public static OpenApiManager getInstance() {
        if (instance == null) {
            instance = new OpenApiManager();
        }
        return instance;
    }

    private OpenApiManager() {
    }

    /**
     * 设置
     * @param accessor  接入商
     */
    public void setAccessor(Map<String, OpenAccountInfo> accessor) {
        this.accessor = accessor;
    }

    /**
     * 是否存在接入商
     * @param accessId  接入商id
     * @return  返回是否
     */
    public boolean hasAccess(String accessId) {
        return this.accessor.containsKey(accessId);
    }

    /**
     * 是否存在接口权限
     * @param accessId  接入商id
     * @param apiCode   接口编码
     * @return  是否
     */
    public boolean hasOpenApi(String accessId, String apiCode) {
        if (hasAccess(accessId)) {
            return this.accessor.get(accessId).getApiCodes().contains(apiCode);
        }
        return false;
    }

    /**
     * 获取开放账号
     * @param accessId  接入商id
     * @return
     */
    public OpenAccountInfo getAccount(String accessId) {
        return accessor.get(accessId);
    }


}
