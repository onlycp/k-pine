package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

import java.util.List;

@Data
public class KdbDataRet <T> {

    /**
     * 数据
     */
    private List<T> list;


    /**
     * 总数
     */
    private Integer total;

}
