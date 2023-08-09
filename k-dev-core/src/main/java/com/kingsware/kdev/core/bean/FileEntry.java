package com.kingsware.kdev.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * 文件定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/21 3:27 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntry {
    /** 文件 **/
    private File file;
    /** 文件名 **/
    private String fileName;
}
