package com.kingsware.kdev.sys.manager;


import java.util.HashMap;
import java.util.Map;

/**
 * 令牌缓存
 */
public class UniOpsTokenStore {

    /** 实例 **/
    private static UniOpsTokenStore instance;

    /** uniops对pine的令牌缓存 **/
    private final Map<String, String> uniopsToken2PineTokenCache = new HashMap<>();

    public static UniOpsTokenStore getInstance() {
        if (instance == null) {
            synchronized (UniOpsTokenStore.class) {
                if (instance == null) {
                    instance = new UniOpsTokenStore();
                }
            }
        }
        return instance;
    }

    private UniOpsTokenStore() {
    }

    public void put(String uniopsToken, String pineToken) {
        uniopsToken2PineTokenCache.put(uniopsToken, pineToken);
    }

    public void remove(String uniopsToken) {
        uniopsToken2PineTokenCache.remove(uniopsToken);
    }

    public String get(String uniopsToken) {
        return uniopsToken2PineTokenCache.get(uniopsToken);
    }

    public boolean containKey(String key) {
        return uniopsToken2PineTokenCache.containsKey(key);
    }

    public String getUniOpsToken(String pineToken) {
        for (Map.Entry<String, String> entry: uniopsToken2PineTokenCache.entrySet()) {
            if (entry.getValue().equals(pineToken)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
