package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

import java.util.List;

/**
 * 上传文件实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/8 11:03 上午
 */
@Data
public class KFlowUploadFile {
    /** 名称 **/
    private String name;
    /** 原始文件名 **/
    private String originFileName;
    /** 文件大小 **/
    private Long fileSize;
    /** 文件内容, Base64 **/
    private String fileContent;
    /** 类型 **/
    private String contentType;

    /** 用于支持多文件上传 **/
    private List<KFlowUploadFile> fileList;
}
