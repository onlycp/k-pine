package com.kingsware.kdev.core.base;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.bean.BasePageArgv;
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
    public PageDataRet<? extends BaseManageRet> query(String sql, List<Object> params, BasePageArgv argv, Class<? extends BaseManageModel> inClass,  Class<? extends BaseManageRet> outClass) {
        // 返回结果
        PageDataRet<BaseManageRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argv.isPageQuery()) {
            PagedList<? extends BaseManageModel> pagedList = DB.findPagedList(inClass, argv.getPage(), argv.getPageSize(), sql, params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            // 返回列表
            List<BaseManageRet> baseManageRets = new ArrayList<>();
            for (BaseManageModel model: pagedList.getList()) {
                baseManageRets.add(model2Ret(model, outClass));
            }
            pageDataRet.setList(baseManageRets);
        }
        // 一般查询
        else {
            List<? extends BaseManageModel> models = DB.findList(inClass, sql, params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            // 返回列表
            List<BaseManageRet> baseManageRets = new ArrayList<>();
            for (BaseManageModel model: models) {
                baseManageRets.add(model2Ret(model, outClass));
            }
            pageDataRet.setList(baseManageRets);
        }
        return pageDataRet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<? extends BaseManageRet> query(String sql, List<Object> params, BasePageArgv argv, Class<? extends BaseManageRet> outClass) {
        // 返回结果
        PageDataRet<BaseManageRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argv.isPageQuery()) {
            PagedList<? extends BaseManageRet> pagedList = DB.findPagedList(outClass, argv.getPage(), argv.getPageSize(), sql, params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            pageDataRet.setList((List<BaseManageRet>) pagedList.getList());
        }
        // 一般查询
        else {
            List<? extends BaseManageRet> models = DB.findList(outClass, sql, params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            pageDataRet.setList((List<BaseManageRet>) models);
        }
        return pageDataRet;
    }

    @Override
    public BaseManageRet model2Ret(BaseManageModel model, Class<? extends BaseManageRet> outClass) {
        return BeanUtils.copyObject(model, outClass);
    }
}
