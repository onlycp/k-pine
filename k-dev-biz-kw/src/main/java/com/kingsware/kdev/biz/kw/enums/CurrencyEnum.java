package com.kingsware.kdev.biz.kw.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述：  币种枚举类型
 *
 * @author 林贤钦
 * @version 1.00
 * @Date 2020/11/27
 */
@Getter
public enum CurrencyEnum {
    RMB(0,"人民币"),
    USD(1,"美元"),
    EUR(2,"欧元"),
    AUD(3,"澳元"),
    GBP(4,"英元"),
    JPY(5,"日元");
    private final Integer code;
    private final String currency;

    private static final Map<Integer, CurrencyEnum> lookup = new HashMap();

    CurrencyEnum(Integer code, String currency){
        this.code=code;
        this.currency=currency;
    }
    public Integer getValue(){
        return code;
    }

    public static CurrencyEnum get(int code) {
        return lookup.get(code);
    }

    static {
        Iterator i$ = EnumSet.allOf(CurrencyEnum.class).iterator();

        while(i$.hasNext()) {
            CurrencyEnum c = (CurrencyEnum)i$.next();
            lookup.put(c.code, c);
        }
    }
}
