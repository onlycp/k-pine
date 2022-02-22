package com.kingsware.kdev.core.base;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.bean.BasePageArgv;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.util.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务基础实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:41 上午
 */
public class BaseServiceImpl implements BaseService {
    @Override
    public PageDataRet<? extends BaseSimpleRet> query(String sql, List<Object> params, BasePageArgv argv, Class<? extends BaseModel> inClass,  Class<? extends BaseSimpleRet> outClass) {
        return this.query("db", sql, params, argv, inClass, outClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<? extends BaseSimpleRet> query(String sql, List<Object> params, BasePageArgv argv, Class<? extends BaseSimpleRet> outClass) {
        return this.query("db", sql, params, argv, outClass);
    }

    @Override
    public PageDataRet<? extends BaseSimpleRet> query(String dbName, String sql, List<Object> params, BasePageArgv argv, Class<? extends BaseModel> inClass, Class<? extends BaseSimpleRet> outClass) {
        // 返回结果
        PageDataRet<BaseSimpleRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argv.isPageQuery()) {
            PagedList<? extends BaseModel> pagedList = DB.byName(dbName).findPagedList(inClass, argv.getPage(), argv.getPageSize(), sql, params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            // 返回列表
            List<BaseSimpleRet> BaseSimpleRets = new ArrayList<>();
            for (BaseModel model: pagedList.getList()) {
                BaseSimpleRets.add(model2Ret(model, outClass));
            }
            pageDataRet.setList(BaseSimpleRets);
        }
        // 一般查询
        else {
            List<? extends BaseModel> models = DB.findList(inClass, sql, params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            // 返回列表
            List<BaseSimpleRet> BaseSimpleRets = new ArrayList<>();
            for (BaseModel model: models) {
                BaseSimpleRets.add(model2Ret(model, outClass));
            }
            pageDataRet.setList(BaseSimpleRets);
        }
        return pageDataRet;
    }

    @Override
    public PageDataRet<? extends BaseSimpleRet> query(String dbName, String sql, List<Object> params, BasePageArgv argv, Class<? extends BaseSimpleRet> outClass) {
        // 返回结果
        PageDataRet<BaseSimpleRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argv.isPageQuery()) {
            PagedList<? extends BaseSimpleRet> pagedList = DB.byName(dbName).findPagedList(outClass, argv.getPage(), argv.getPageSize(), sql, params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            pageDataRet.setList((List<BaseSimpleRet>) pagedList.getList());
        }
        // 一般查询
        else {
            List<? extends BaseSimpleRet> models = DB.findList(outClass, sql, params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            pageDataRet.setList((List<BaseSimpleRet>) models);
        }
        return pageDataRet;
    }

    @Override
    public BaseSimpleRet model2Ret(BaseModel model, Class<? extends BaseSimpleRet> outClass) {
        return BeanUtils.copyObject(model, outClass);
    }
}
