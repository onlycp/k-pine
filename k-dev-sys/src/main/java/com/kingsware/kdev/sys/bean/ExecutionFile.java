package com.kingsware.kdev.sys.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2022/4/19 16:19
 */
@Data
@EqualsAndHashCode
public class ExecutionFile {
    /** 文件名 */
    private String name;
    /** 版本号 */
    private int version;
    /** 是否只执行1次 */
    private boolean isOnce;
//    /** 文件 */
//    private File file;
    /** 资源 **/
    private Resource resource;


}
