package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 文件查询
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFileQueryArgv extends BasePageArgv {

    /** 字典名 */
    private String fileName;
    /** 文件来源 */
    private String fileFrom;
    /** 文件名 */
    private String fileExt;
    /** 存储方式 **/
    private Integer saveType;
    /** 上传时间 */
    private String uploadTimes;

}
