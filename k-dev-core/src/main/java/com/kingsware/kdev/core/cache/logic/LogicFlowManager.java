package com.kingsware.kdev.core.cache.logic;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开放接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/11 11:30 AM
 */
public class LogicFlowManager {
    private static LogicFlowManager instance;
    /** 接入商以及权限接口 **/
    private Map<String, Boolean> cacheMap = new HashMap<>();

    public static LogicFlowManager getInstance() {
        if (instance == null) {
            instance = new LogicFlowManager();
        }
        return instance;
    }

    private LogicFlowManager() {
    }


    public void setFlowCache(List<LogicCache> caches) {
        Map<String, Boolean> tempMap = new HashMap<>();
        caches.forEach(it -> tempMap.put(it.getFlowId(), "1".equalsIgnoreCase(it.getTranCtrl())));
        this.cacheMap = tempMap;
    }



    /**
     * 是否存在接口权限
     * @param flowId  流程id
     * @return  是否
     */
    public boolean isTranCtrl(String flowId) {
        if (cacheMap.containsKey(flowId)) {
            return this.cacheMap.get(flowId);
        }
        return false;
    }


}
