package com.kingsware.kdev.sys.argv;

import lombok.Data;
import java.util.Set;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/21/10:03
 * @description:
 */
@Data
public class SysNoticeRelationArgv {
    /** id **/
    private String noticeId;
    /** 关联的id集合 **/
    private Set<String> relationIds;
}
