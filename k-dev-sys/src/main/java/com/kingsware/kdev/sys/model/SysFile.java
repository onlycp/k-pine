package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysFile extends BaseManageModel {

    /** 文件名称 */
    private String fileName;
    /** 原始文件名 */
    private String fileOriginalName;
    /** 文件大小 */
    private Integer fileSize;
    /** 文件扩展名 */
    private String fileExt;
    /** 文件md5值 **/
    private String fileMd5;
    /** 存储方式 **/
    private Integer saveType;
    /** 文件路径 **/
    private String filePath;
    /** 文件来源 **/
    private String fileFrom;
    /** 文件内容 **/
    private String fileContent;
    /** 所属应用ID **/
    private String appId;

}
