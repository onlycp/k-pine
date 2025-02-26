package com.kingsware.kdev.sys.bean;

import com.kingsware.kdev.core.model.SysFile;
import lombok.Data;

@Data
public class ResourceFile {
    /** 文件路径 */
    private String path;
    /** 0:静态资源 1:SysFile */
    private Integer type;
    /** SysFile */
    private SysFile sysFile;
}
