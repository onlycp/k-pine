package com.kingsware.kdev.core.bean;

import lombok.Data;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/7/12 09:12
 */
@Data
public class DataModified {
    /** 数量 **/
    private int cnt;
    /** 最后更新时间 **/
    private String whenModified;
}
