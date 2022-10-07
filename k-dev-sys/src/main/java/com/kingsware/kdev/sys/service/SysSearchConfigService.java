package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysSearchConfigQueryArgv;
import com.kingsware.kdev.sys.ret.DevPageRet;

/**
 * 接口业务类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface SysSearchConfigService extends BaseService {

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<?> query(SysSearchConfigQueryArgv argv);

}
