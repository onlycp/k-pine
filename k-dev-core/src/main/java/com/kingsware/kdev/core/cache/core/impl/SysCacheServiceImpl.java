package com.kingsware.kdev.core.cache.core.impl;

import com.kingsware.kdev.core.cache.core.SysCacheService;
import com.kingsware.kdev.core.model.SysCache;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.FaasInvoke;
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
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public String getCache(String key) {
        return FaasInvoke.getCache(key);
    }

    @Override
    public void setCache(String key, String value) {
        FaasInvoke.setCache(key, value);
    }

    /**
     * 保存缓存值
     * @param key
     * @param value
     * @param expireTime 单位秒
     */
    @Override
    public void setCache(String key, String value, long expireTime) {
       FaasInvoke.setCache(key, value, expireTime*1000);
    }

    @Override
    public void removeCache(String key) {
        FaasInvoke.removeCache(key);
    }

}
