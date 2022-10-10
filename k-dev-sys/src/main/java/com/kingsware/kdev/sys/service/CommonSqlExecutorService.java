package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.sys.argv.CommonSqlExecutorArgv;

/**
 * 通用SQL执行
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface CommonSqlExecutorService extends BaseService {

     Object execute(CommonSqlExecutorArgv argv);

}
