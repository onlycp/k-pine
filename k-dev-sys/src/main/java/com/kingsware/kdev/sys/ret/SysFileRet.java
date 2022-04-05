package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFileRet extends BaseManageRet {
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 原始文件名
     */
    private String fileOriginalName;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 文件扩展名
     */
    private String fileExt;
    /**
     * 文件md5值
     **/
    private String fileMd5;
    /**
     * 存储方式
     **/
    private Integer saveType;
    /**
     * 文件路径
     **/
    private String filePath;
    /** 文件来源 **/
    private String fileFrom;
    /** 所属应用ID **/
    private String appId;
}
