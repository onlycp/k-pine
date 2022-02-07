package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceArgv;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbDataSourceRet;
import com.kingsware.kdev.sys.service.SysKdbDataSourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * kdb数据源业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysKdbDataSourceServiceImpl extends BaseServiceImpl implements SysKdbDataSourceService {

    @Override
    public SysKdbDataSourceRet get(String id) {
        // 参数
        DataSourceQueryArgv argv = new DataSourceQueryArgv();
        argv.setSourceName(id);
        // 查询model
        KdbApi api = (KdbApi)(DB.getDefault());
        List<DataSourceInfo> list = api.queryDataSource(argv);
        // 转换成ret对象
        return toRet(list.get(0));
    }

    private SysKdbDataSourceRet toRet(DataSourceInfo info) {
        SysKdbDataSourceRet ret = new SysKdbDataSourceRet();
        ret.setId(info.getSourceName());
        ret.setDriverClass(info.getDriverClass());
        ret.setJdbcUrl(info.getJdbcUrl());
        ret.setUsername(info.getUserName());
        ret.setPassword(info.getPassword());
        return ret;
    }

    @Override
    public void add(SysKdbDataSourceArgv argv) {

        DataSourceInfo info = new DataSourceInfo();
        info.setSourceName(argv.getId());
        info.setDriverClass(argv.getDriverClass());
        info.setJdbcUrl(argv.getJdbcUrl());
        info.setUserName(argv.getUsername());
        info.setPassword(argv.getPassword());
        KdbApi api = (KdbApi)(DB.getDefault());
        api.addDataSource(info);
    }

    @Override
    public void edit(SysKdbDataSourceArgv argv) {
        DataSourceInfo info = new DataSourceInfo();
        info.setSourceName(argv.getId());
        info.setDriverClass(argv.getDriverClass());
        info.setJdbcUrl(argv.getJdbcUrl());
        info.setUserName(argv.getUsername());
        info.setPassword(argv.getPassword());
        KdbApi api = (KdbApi)(DB.getDefault());
        api.editDataSource(info);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysKdbDataSourceRet> query(SysKdbDataSourceQueryArgv argv) {
        DataSourceQueryArgv info = new DataSourceQueryArgv();
        info.setSourceName(argv.getName());
        // 查询所有数据
        KdbApi api = (KdbApi)(DB.getDefault());
        List<DataSourceInfo> list = api.queryDataSource(info);
        // 转为ret类
        List<SysKdbDataSourceRet> retList = new ArrayList<>();
        for (DataSourceInfo infoL: list) {
            retList.add(toRet(infoL));
        }
        // 排序
        retList.sort(Comparator.comparing(SysKdbDataSourceRet::getId));
        return PageUtil.memoryPage(argv, retList, SysKdbDataSourceRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        KdbApi api = (KdbApi)(DB.getDefault());
        for (String id: argv.getIds()) {
           api.deleteDataSource(id);
        }
    }

}
