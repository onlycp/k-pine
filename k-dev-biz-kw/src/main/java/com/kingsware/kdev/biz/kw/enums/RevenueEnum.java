package com.kingsware.kdev.biz.kw.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述： 收支枚举
 *
 * @author 林贤钦
 * @version 1.00
 * @Date 2020/11/27
 */
@Getter
public enum RevenueEnum {
    INCOME(0,"收入"),
    PAY(1,"支出");
    private final Integer code;
    private final String revenue;

    private static final Map<Integer, RevenueEnum> lookup = new HashMap();

    RevenueEnum(Integer code, String revenue){
        this.code=code;
        this.revenue=revenue;
    }
    public Integer getValue(){
        return code;
    }

    public static RevenueEnum get(int code) {
        return lookup.get(code);
    }

    static {
        Iterator i$ = EnumSet.allOf(RevenueEnum.class).iterator();

        while(i$.hasNext()) {
            RevenueEnum c = (RevenueEnum)i$.next();
            lookup.put(c.code, c);
        }
    }
}
