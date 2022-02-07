package com.kingsware.kdev.biz.kw.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 队列任务类型
 */
public enum QueueTaskTypeEnum {
    SINGLE_SCAN(0,"单一扫描"),
    ALL_SCAN(1,"全部扫描"),
    IMPORT_BANK(2,"导入银行单位"),
    IMPORT_YEAR_WATER(3, "导入全年流水"),
    SEND_MBS_WATER(4, "发送MBS流水请求"),
    DELETE_WATER_RECEIPT_MACH(5, "删除指定银行的所有回单流水"),
    IMPORT_BANK_MBS(6, "删除指定银行的所有回单流水"),
    IMPORT_MBS_SEND(7, "更新银行是否发送MBS数据"),
    RESEND_MBS_WATER(8, "重发所有流水到浦发接口"),
    SEND_MBS_BALANCE(9, "发送MBS账户余额");
    private final Integer code;
    private final String type;

    private static final Map<Integer, QueueTaskTypeEnum> lookup = new HashMap();

    QueueTaskTypeEnum(Integer code, String type){
        this.code=code;
        this.type=type;
    }
    public Integer getValue(){
        return code;
    }

    public String getType(){return type;}

    public static QueueTaskTypeEnum get(int code) {
        return lookup.get(code);
    }

    static {
        Iterator i$ = EnumSet.allOf(QueueTaskTypeEnum.class).iterator();

        while(i$.hasNext()) {
            QueueTaskTypeEnum c = (QueueTaskTypeEnum)i$.next();
            lookup.put(c.code, c);
        }
    }

}
