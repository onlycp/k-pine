package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwMechanismArgv;
import com.kingsware.kdev.biz.kw.argv.KwMechanismQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwMechanismRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysLoginLogQueryArgv;

/**
 * 行别管理业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface KwWaterService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwWaterRet get(String id);

    /**
     * 新增（暂时保留）
     * @param argv 新增
     */
     void add(KwWaterQueryArgv argv);


    /**
     * 查询
     * @param argv 查询
     * @return 查询结果
     */
     PageDataRet<KwWaterRet> query(KwWaterQueryArgv argv);


    /**
     * 导出导入模板
     */
    void exportImportTemplate();

    /**
     * 导出
     */
    void export(KwWaterQueryArgv argv);
}
