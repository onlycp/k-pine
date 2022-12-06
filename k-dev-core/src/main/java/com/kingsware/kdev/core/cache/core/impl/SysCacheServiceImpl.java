package com.kingsware.kdev.core.cache.core.impl;

import com.kingsware.kdev.core.cache.core.SysCacheService;
import com.kingsware.kdev.core.model.SysCache;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class SysCacheServiceImpl implements SysCacheService {

    private final String GET_SQL = "select * from sys_cache where code = ? ";
    private final String LIST_SQL = "select * from sys_cache where when_expired is not null";
    private final String DELETE_SQL = "delete from sys_cache where code = ? ";
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss:SSS");

    @Override
    public SysCache getCache(String key) {
        SysCache sysCache = DB.findOne(SysCache.class, GET_SQL, key);
        if (sysCache != null && StringUtils.isNotEmpty(sysCache.getWhenExpired())) {
            try {
                // 如果过期，取到的值为空
                LocalDateTime whenExpired = LocalDateTime.parse(sysCache.getWhenExpired(), FORMATTER);
                if (whenExpired.isBefore(LocalDateTime.now())) {
                    // 过期自动删除
                    removeCache(key);
                    sysCache = null;
                }
            } catch (Exception e) {
                log.error("获取缓存数据失败，日期格式有误：", e);
                sysCache = null;
            } finally {
                return sysCache;
            }
        }
        return sysCache;
    }

    @Override
    public void setCache(String key, String value) {
        this.setCache(key, value, 1800);
    }

    /**
     * 保存缓存值
     * @param key
     * @param value
     * @param expireTime 单位秒
     */
    @Override
    public void setCache(String key, String value, long expireTime) {
        try {
            SysCache sysCache = DB.findOne(SysCache.class, GET_SQL, key);
            LocalDateTime currentTime = LocalDateTime.now();
            currentTime = currentTime.plusSeconds(expireTime);
            String currentTimeStr = expireTime == -1 ? null : currentTime.format(FORMATTER);
            boolean isNew = false;
            if (sysCache == null) {
                sysCache = new SysCache();
                sysCache.setCode(key);
                isNew = true;
            }
            sysCache.setValue(value);
            sysCache.setWhenExpired(currentTimeStr);
            sysCache.setWhenCreated(LocalDateTime.now().format(FORMATTER));

            if (isNew) {
                DB.save(sysCache);
            } else {
                DB.update(sysCache);
            }

        } catch (Exception e) {
            log.error("缓存数据失败：", e);
        }
    }

    @Override
    public void removeCache(String key) {
        DB.executeUpdateSql(DELETE_SQL, key);
    }

    @Override
    public List<SysCache> listCache() {
        return DB.findList(SysCache.class, LIST_SQL);
    }

}
