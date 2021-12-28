package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysDictArgv;
import com.kingsware.kdev.sys.argv.SysDictQueryArgv;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.ret.SysDictRet;
import com.kingsware.kdev.sys.service.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/12/27 9:36 上午
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl implements SysDictService {

    @Override
    public SysDictRet get(String id) {
        // 查询model
        SysDict model = DB.findById(SysDict.class, id);
        // 转换成ret对象
        return BeanUtils.copyObject(model, SysDictRet.class);
    }

    @Override
    public void add(SysDictArgv argv) {
        SysDict model = BeanUtils.copyObject(argv, SysDict.class);
        DB.save(model);
    }

    @Override
    public void edit(SysDictArgv argv) {
        SysDict model = DB.findById(SysDict.class, argv.getId());
        if (model == null) {
            throw new BusinessException("找不到字典类型");
        }
        model = BeanUtils.copyObject(argv, SysDict.class);
        DB.update(model);
    }

    @Override
    public PageDataRet<SysDictRet> query(SysDictQueryArgv argv) {
        // 拼装sql
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        builder.append("select * from sys_dict where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            builder.append("and ");
            builder.append("name like '%?%'");
            params.add(argv.getName());
        }
        // 返回结果
        PageDataRet<SysDictRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argv.isPageQuery()) {
            PagedList<SysDict> pagedList = DB.findPagedList(SysDict.class, argv.getPage(), argv.getPageSize(), builder.toString(), params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            pageDataRet.setList(BeanUtils.copyList(pagedList.getList(), SysDictRet.class));
        }
        // 一般查询
        else {
            List<SysDict> models = DB.findList(SysDict.class, builder.toString(), params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            pageDataRet.setList(BeanUtils.copyList(models, SysDictRet.class));
        }
        return pageDataRet;
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDict.class, id);
        }
    }
}
