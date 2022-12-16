package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * 事务缓存
 * @author chenp
 * @date 2022-12-13
 */
@Data
public class TransactionCache {
    /** 事务id **/
    private String id;
    /** 事务回滚class **/
    private Class<? extends Throwable>[] throwables;
    /** 事务签名 **/
    private String signName;

}
