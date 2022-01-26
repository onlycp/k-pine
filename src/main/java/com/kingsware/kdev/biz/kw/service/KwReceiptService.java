package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwReceiptArgv;
import com.kingsware.kdev.biz.kw.argv.KwReceiptQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwReceiptRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 流水管理业务类
 *
 * @author zxw
 * @version 1.0.0
 * @date 2022/01/04
 */
public interface KwReceiptService extends BaseService {

    /**
     * 通过id查询
     *
     * @param id id
     * @return 返回结果
     */
    KwWaterRet get(String id);

    /**
     * 新增
     *
     * @param argv 新增
     */
    String add(KwReceiptArgv argv);

    /**
     * 编辑
     *
     * @param argv
     */
    void edit(KwReceiptArgv argv);


    /**
     * 查询
     *
     * @param argv 查询
     * @return 查询结果
     */
    PageDataRet<KwReceiptRet> query(KwReceiptQueryArgv argv);


    /**
     * 导出
     */
    void export(KwReceiptQueryArgv argv);
}
