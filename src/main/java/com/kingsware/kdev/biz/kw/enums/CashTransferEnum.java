package com.kingsware.kdev.biz.kw.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述： 现金转账枚举
 *
 * @author 林贤钦
 * @version 1.00
 * @Date 2020/11/27
 */
@Getter
public enum CashTransferEnum {
    CASH(0,"现金"),
    TRANSFER(1,"转账");
    private final Integer code;
    private final String revenue;

    private static final Map<Integer, CashTransferEnum> lookup = new HashMap();

    CashTransferEnum(Integer code, String revenue){
        this.code=code;
        this.revenue=revenue;
    }
    public Integer getValue(){
        return code;
    }

    public static CashTransferEnum get(int code) {
        return lookup.get(code);
    }

    static {
        Iterator i$ = EnumSet.allOf(CashTransferEnum.class).iterator();

        while(i$.hasNext()) {
            CashTransferEnum c = (CashTransferEnum)i$.next();
            lookup.put(c.code, c);
        }
    }
}
