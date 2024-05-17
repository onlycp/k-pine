package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MD5Utils;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * ApiResultCache类用于缓存API结果。
 * 它包含了一组令牌、创建与更新时间、查询结果以及查询所耗费的时间。
 */
@Data
public class ApiResultCache {

    // 保存令牌的集合
    private Set<String> tokens = new HashSet<>();
    // 缓存的创建时间
    private long createTime;
    // 缓存的最后更新时间
    private long updateTime;

    // API查询结果
    private KdbFlowResult result;
    // 查询结果所耗费的时间
    private long takeTime;

    /**
     * 创建一个新的ApiResultCache实例并初始化其属性。
     *
     * @param result KdbFlowResult对象，存储API操作的结果。
     * @param takeTime 执行操作所花费的时间，单位为毫秒。
     * @return 返回初始化后的ApiResultCache实例。
     */
    public static ApiResultCache create(KdbFlowResult result, long takeTime) {
        ApiResultCache cache = new ApiResultCache();
        cache.createTime = System.currentTimeMillis(); // 设置创建时间为当前时间
        cache.setUpdateTime(cache.createTime); // 设置更新时间为创建时间
        cache.setResult(result); // 设置操作结果
        cache.setTakeTime(takeTime); // 设置操作耗时
        return cache;
    }

    /**
     * 更新ApiResultCache实例的属性。
     *
     * @param newResult KdbFlowResult对象，更新API操作的结果。
     * @param takeTime 执行操作所花费的时间，单位为毫秒。
     */
    public boolean update(KdbFlowResult newResult, long takeTime) {
        boolean changed = true;
        if (this.result.getData() != null && newResult.getData() != null) {
            String oldMd5 = MD5Utils.md5(JsonUtil.toJson(newResult.getData()));
            String newMd5 = MD5Utils.md5(JsonUtil.toJson(result.getData()));
            changed = !oldMd5.equals(newMd5);

        }
        this.setUpdateTime(System.currentTimeMillis()); // 更新时间为当前时间
        this.setResult(newResult); // 更新操作结果
        this.setTakeTime(takeTime); // 更新操作耗时
        return changed;
    }
}
