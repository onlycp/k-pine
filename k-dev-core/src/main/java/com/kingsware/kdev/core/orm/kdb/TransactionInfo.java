package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * 事务事件
 */
@Data
public class TransactionInfo {
    private String transactionUuid;
    private int timeout;
    private String cmd;

}
