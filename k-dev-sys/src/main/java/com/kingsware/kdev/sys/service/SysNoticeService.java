package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysNoticeArgv;
import com.kingsware.kdev.sys.argv.SysNoticeQueryArgv;
import com.kingsware.kdev.sys.argv.SysNoticeRelationArgv;
import com.kingsware.kdev.sys.ret.SysNoticeRet;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:35
 * @description:
 */
public interface SysNoticeService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysNoticeRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
    void add(SysNoticeArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysNoticeArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysNoticeRet> query(SysNoticeQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 发送通知
     * @param argv  参数
     * @param token  token
     * @param ip  ip
     */
    void sendNotice(SysNoticeRelationArgv argv, String token, String ip);
}
