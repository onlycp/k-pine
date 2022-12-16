package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionManager {
    /** 事务id **/
    private static ThreadLocal<TransactionCache> transactionCache = new ThreadLocal<>();
    private static TransactionManager instance;

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    private TransactionManager() {
    }

    /**
     * 获取事务id
     * @return
     */
    public TransactionCache getTransactionCache() {
        TransactionCache cache = transactionCache.get();
        if (cache == null) {
            return null;
        }
        return cache;
    }

    /**
     * 启动事务
     * @param timeout 超时时间, 单位为秒
     * @param throwables 事务回滚类
     */
    public void begin(int timeout, Class<? extends Throwable>[] throwables, String signName) {
        // 先清空当前线程变量
        if (transactionCache.get() != null) {
            transactionCache.remove();
        }
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTimeout(timeout);
        transactionInfo.setCmd(TransactionActionEnum.BEGIN.name());
        String tid = DB.transaction(transactionInfo);
        log.info("FAAS事务启动，签名:{}, ID:{}", signName, tid);
        // 当前线程缓存
        TransactionCache cache = new TransactionCache();
        cache.setId(tid);
        cache.setThrowables(throwables);
        cache.setSignName(signName);
        transactionCache.set(cache);

    }

    /**
     * 提交事务
     */
    public void commit() {
        TransactionCache cache = transactionCache.get();
        if (cache == null) {
            log.warn("当前无事务可提交!");
            return;
        }
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionUuid(cache.getId());
        transactionInfo.setCmd(TransactionActionEnum.COMMIT.name());
        // 提交事务
        DB.transaction(transactionInfo);
        log.info("FAAS事务提交，签名:{}, ID:{}", cache.getSignName(), cache.getId());
        // 移除事务缓存
        transactionCache.remove();
    }

    public void rollback() {
        TransactionCache cache = transactionCache.get();
        if (cache == null) {
            log.warn("当前无事务可回滚!");
            return;
        }
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionUuid(cache.getId());
        transactionInfo.setCmd(TransactionActionEnum.ROLLBACK.name());
        // 提交事务
        log.info("FAAS事务回滚，签名:{}, ID:{}", cache.getSignName(), cache.getId());
        DB.transaction(transactionInfo);

        // 移除事务缓存
        transactionCache.remove();
    }
}
