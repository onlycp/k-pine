package com.kingsware.kdev.core.bean;

import lombok.Data;

import java.util.Set;

/**
 * 关联关系存储
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/7 2:57 下午
 */
@Data
public class BaseRelationArgv {
    /** id **/
    private String id;
    /** 关联的id集合 **/
    private Set<String> relationIds;

}
