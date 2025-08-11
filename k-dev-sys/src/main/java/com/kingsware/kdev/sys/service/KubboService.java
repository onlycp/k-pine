package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.sys.argv.ExecuteFaasArgv;
import com.kingsware.kdev.sys.ret.ApiRequestRet;
import com.kingsware.kdev.sys.ret.AppInfoRet;
import com.kingsware.kdev.sys.ret.HealthRet;

import java.util.List;

/**
 * @author chenp
 * @date 2024/3/13
 */
public interface KubboService {

    /**
     * 执行Faas流程
     * @param argv
     * @return
     */
    KdbRet<?> executeFaas(ExecuteFaasArgv argv);

    /**
     * 健康检查
     * @return
     */
    HealthRet health();

    /**
     * 获取应用程序信息
     *
     * 本方法无需参数，调用后将返回一个包含应用程序详细信息的对象。
     *
     * @return AppInfoRet 返回一个应用程序信息对象，该对象包含了应用程序的版本号、名称、开发者等信息。
     */
    AppInfoRet info();

    /**
     * 获取接口列表
     * @return
     */
    List<ApiRequestRet> apis(String appId);
}
