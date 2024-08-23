package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.DataBaseInstanceArgv;
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

    /** 分隔符 **/
    private final String SPLIT_TAG = "\\\0a01";

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
        if (info.getJson() == null) {
            ret.setJson("{}");
        }
        else {
            ret.setJson(info.getJson());
        }

        // 处理instance
        List<DataBaseInstanceArgv> instances = new ArrayList<>();
        String[] urls = info.getJdbcUrl().split(SPLIT_TAG);
        String[] usernames = info.getUserName().split(SPLIT_TAG);
        String[] passwords = info.getPassword().split(SPLIT_TAG);
        for (int i=0; i<urls.length; i++) {
            DataBaseInstanceArgv instance = new DataBaseInstanceArgv();
            instance.setJdbcUrl(urls[i]);
            instance.setUserName((usernames.length-1 < i)? "":  usernames[i]);
            instance.setPassword((passwords.length-1 < i)? "":  passwords[i]);
            instances.add(instance);
        }
        ret.setInstances(instances);
        return ret;
    }

    @Override
    public void add(SysKdbDataSourceArgv argv) {

        try {
            DataSourceInfo info = new DataSourceInfo();
            info.setSourceName(argv.getId());
            info.setDriverClass(argv.getDriverClass());
            instanceToField(info, argv);
            if (StringUtils.isNotEmpty(argv.getJson())) {
                info.setJson(argv.getJson());
            }
            else {
                info.setJson("{}");
            }
            KdbApi api = (KdbApi)(DB.getDefault());
            api.addDataSource(info);
        }
        catch (Exception e) {
            throw BusinessException.serviceThrow(I18n.t("SysKdbDataSource.tip.addFail", "数据源新增失败，请检查连接信息！"));
        }

    }

    /**
     * 将实例的json展开为属性列表
     * @param info
     * @param argv
     */
    private void instanceToField(DataSourceInfo info, SysKdbDataSourceArgv argv) {
        if (argv.getInstances() != null && !argv.getInstances().isEmpty()) {
            List<DataBaseInstanceArgv> instances = argv.getInstances();
            info.setJdbcUrl(StringUtils.joinFieldToString(instances, DataSourceInfo.Fields.jdbcUrl, SPLIT_TAG));
            info.setUserName(StringUtils.joinFieldToString(instances, DataSourceInfo.Fields.userName, SPLIT_TAG));
            info.setPassword(StringUtils.joinFieldToString(instances, DataSourceInfo.Fields.password, SPLIT_TAG));
        }
        else {
            info.setJdbcUrl(argv.getJdbcUrl());
            info.setUserName(argv.getUsername());
            info.setPassword(argv.getPassword());
        }
    }

    @Override
    public void edit(SysKdbDataSourceArgv argv) {
        try {
            DataSourceInfo info = new DataSourceInfo();
            info.setSourceName(argv.getId());
            info.setDriverClass(argv.getDriverClass());
            instanceToField(info, argv);
            KdbApi api = (KdbApi)(DB.getDefault());
            if (StringUtils.isNotEmpty(argv.getJson())) {
                info.setJson(argv.getJson());
            }
            else {
                info.setJson("{}");
            }
            api.editDataSource(info);
        }
        catch (Exception e) {
            throw BusinessException.serviceThrow(I18n.t("SysKdbDataSource.tip.addFail", "数据源新增失败，请检查连接信息！"));
        }

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
        // 按driverClass过滤
        if (StringUtils.isNotEmpty(argv.getDriverClass())) {
            retList.removeIf(ret -> !ret.getDriverClass().contains(argv.getDriverClass()));
        }
        // 按jdbc url过滤
        if (StringUtils.isNotEmpty(argv.getJdbcUrl())) {
            retList.removeIf(ret -> !ret.getJdbcUrl().contains(argv.getJdbcUrl()));
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

    /**
     * 刷新基础数据源
     *
     * @return
     */
    @Override
    public void refreshBaseFlow() {
        DB.kdbApi().refreshBaseFlow();
    }

}
