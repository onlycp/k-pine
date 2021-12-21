package com.kingsware.kdev.core.bean;

import java.util.List;

/**
 * 树返回结构
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 11:43 上午
 */
public class TreeDataRet<T> {
    /** 键值 **/
    private String key;
    /** 标签 **/
    private String label;
    /** 附加数据 **/
    private T extraData;
    /** 孩子们 **/
    private List<TreeDataRet<T>> children;
}
