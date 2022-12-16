package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * 事务事件
 */
@Data
public class TransactionRet {
    /** 事务id **/
    private String transactionUuid;
}
