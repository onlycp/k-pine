package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysNoticeRecordQueryArgv;
import com.kingsware.kdev.sys.ret.SysNoticeRecordRet;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:35
 * @description:
 */
public interface SysNoticeRecordService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysNoticeRecordRet get(String id);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysNoticeRecordRet> query(SysNoticeRecordQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 我的消息
     * @return
     */
    PageDataRet<SysNoticeRecordRet> myUnRead();

    /**
     * 标志为已读
     */
    void willReadAll();
}
