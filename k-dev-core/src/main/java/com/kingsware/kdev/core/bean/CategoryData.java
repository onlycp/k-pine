package com.kingsware.kdev.core.bean;

import lombok.Data;

/**
 * 分类数据.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/7 5:19 下午
 */
@Data
public class CategoryData {

    private String id;
    private String name;
    private String parentId;
}
