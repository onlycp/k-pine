package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *  数据访问配置参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysViewModelArgv {

    private String id;
    /** 名称 **/
    private String name;
    /** 标签 **/
    private String tag;
    /** 描述 **/
    private String note;
    /** 属性列 **/
    private List<SysViewModelFieldArgv> fields;
    /** 所属应用ID **/
    private String appId;


}
