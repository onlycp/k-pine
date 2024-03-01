package com.kingsware.kdev.core.orm.kdb;

/**
 * @author chenp
 * @date 2024/3/1
 */
public class SyncValueManager {

    private static SyncValueManager instance;

    private static ThreadLocal<String> syncValueCache = new ThreadLocal<>();
    public static SyncValueManager getInstance() {
        if (instance == null) {
            instance = new SyncValueManager();
        }
        return instance;
    }

    private SyncValueManager() {
    }

    public void setSyncValue(String value) {
        syncValueCache.set(value);
    }

    public String getSyncValue() {
        return syncValueCache.get();
    }

    public void clearSyncValue() {
        syncValueCache.remove();
    }

}
