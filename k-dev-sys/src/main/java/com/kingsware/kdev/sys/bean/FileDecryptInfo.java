package com.kingsware.kdev.sys.bean;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/11/3
 */
@Data
public class FileDecryptInfo {
    /** id **/
    private String id;
    /** 路径 **/
    private String path;

    public FileDecryptInfo() {
    }

    public FileDecryptInfo(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public FileDecryptInfo(String path) {
        this.path = path;
    }
}
