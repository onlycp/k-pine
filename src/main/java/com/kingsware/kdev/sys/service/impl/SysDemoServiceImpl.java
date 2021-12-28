package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysDemoArgv;
import com.kingsware.kdev.sys.argv.SysDemoQueryArgv;
import com.kingsware.kdev.sys.model.SysDemo;
import com.kingsware.kdev.sys.ret.SysDemoRet;
import com.kingsware.kdev.sys.service.SysDemoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysDemoServiceImpl extends BaseServiceImpl implements SysDemoService {

    @Override
    public SysDemoRet get(String id) {
        // 查询model
        SysDemo model = DB.findById(SysDemo.class, id);
        // 转换成ret对象
        return BeanUtils.copyObject(model, SysDemoRet.class);
    }

    @Override
    public void add(SysDemoArgv argc) {
        SysDemo model = BeanUtils.copyObject(argc, SysDemo.class);
        DB.save(model);
    }

    @Override
    public void edit(SysDemoArgv argc) {
        SysDemo model = DB.findById(SysDemo.class, argc.getId());
        model.setName(argc.getName());
        model.setNote(argc.getNote());
        // 唯一性校验
        DBChecker.build(model, SysDemo.class)
                .uni("name", I18n.t("SysDemo.name.unique", "名称必须唯一"))
                .uni(new String[]{"name", "code"}, I18n.t("SysDemo.name.unique", "名称必须唯一"))
                .checkUnique();

        DB.update(model);
    }

    @Override
    public PageDataRet<SysDemoRet> query(SysDemoQueryArgv argc) {
        // 拼装sql
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        builder.append("select * from sys_demo where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argc.getName())) {
            builder.append("and ");
            builder.append("name like '%?%'");
            params.add(argc.getName());
        }
        // 返回结果
        PageDataRet<SysDemoRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argc.isPageQuery()) {
            PagedList<SysDemo> pagedList = DB.findPagedList(SysDemo.class, argc.getPage(), argc.getPageSize(), builder.toString(), params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            pageDataRet.setList(BeanUtils.copyList(pagedList.getList(), SysDemoRet.class));
        }
        // 一般查询
        else {
            List<SysDemo> models = DB.findList(SysDemo.class, builder.toString(), params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            pageDataRet.setList(BeanUtils.copyList(models, SysDemoRet.class));
        }
        return pageDataRet;
    }

    @Override
    public void delete(MultiIdArgv argc) {
        for (String id: argc.getIds()) {
            DB.delete(SysDemo.class, id);
        }
    }
}
