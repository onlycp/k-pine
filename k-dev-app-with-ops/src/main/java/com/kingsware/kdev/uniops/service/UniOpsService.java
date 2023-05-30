package com.kingsware.kdev.uniops.service;

import com.kingsware.kdev.sys.model.SysMenu;
import com.kingsware.kdev.uniops.argv.DevPublishArgv;
import com.kingsware.kdev.uniops.argv.ToPageArgv;

import java.util.List;

/**
 * @author chenp
 * @date 2023/1/6
 */
public interface UniOpsService {

    /**
     * 跳转到页面
     * @param to    页面
     */
    void page(ToPageArgv to);

    /**
     * 发布应用
     * @param argv  应用参数
     */
    void publish(DevPublishArgv argv);

    /**
     * 发布菜单
     * @param appData
     */
    void publishMenu(String appData);

    /**
     * 卸载
     * @param menus 菜单
     */
    void uninstall(List<SysMenu> menus);

    /**
     * 获取uniops
     * @return
     */
    String getUniOpsToken();


}
