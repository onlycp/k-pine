package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysDictItemArgv;
import com.kingsware.kdev.sys.argv.SysDictItemQueryArgv;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.model.SysDictItem;
import com.kingsware.kdev.sys.ret.SysDictItemRet;
import com.kingsware.kdev.sys.service.SysDictItemService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/12/27 9:36 上午
 */
@Service
public class SysDictItemServiceImpl extends BaseServiceImpl implements SysDictItemService {

    @Override
    public SysDictItemRet get(String id) {
        // 查询model
        SysDictItem model = DB.findById(SysDictItem.class, id);
        // 转换成ret对象
        return BeanUtils.copyObject(model, SysDictItemRet.class);
    }

    @Override
    public void add(SysDictItemArgv argv) {
        SysDictItem model = BeanUtils.copyObject(argv, SysDictItem.class);
        if (model == null) {
            throw new BusinessException("找不到字典类型");
        }
        DB.save(model);
        model = BeanUtils.copyObject(argv, SysDictItem.class);
        DB.update(model);
    }

    @Override
    public void edit(SysDictItemArgv argv) {
        SysDictItem model = DB.findById(SysDictItem.class, argv.getId());
        model.setName(argv.getName());
        model.setNote(argv.getNote());
        DB.update(model);
    }

    @Override
    public PageDataRet<SysDictItemRet> query(SysDictItemQueryArgv argv) {
        // 拼装sql
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        builder.append("select * from sys_DictItem where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            builder.append("and ");
            builder.append("name like '%?%'");
            params.add(argv.getName());
        }
        // 返回结果
        PageDataRet<SysDictItemRet> pageDataRet = new PageDataRet<>();
        // 分页查询
        if (argv.isPageQuery()) {
            PagedList<SysDictItem> pagedList = DB.findPagedList(SysDictItem.class, argv.getPage(), argv.getPageSize(), builder.toString(), params.toArray());
            pageDataRet.setPageSize(pagedList.getPageSize());
            pageDataRet.setPageCount(pagedList.getPageCount());
            pageDataRet.setPage(pagedList.getPageIndex());
            pageDataRet.setTotal(pagedList.getTotalCount());
            pageDataRet.setList(BeanUtils.copyList(pagedList.getList(), SysDictItemRet.class));
        }
        // 一般查询
        else {
            List<SysDictItem> models = DB.findList(SysDictItem.class, builder.toString(), params.toArray());
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(models.size());
            pageDataRet.setTotal(models.size());
            pageDataRet.setPageCount(1);
            pageDataRet.setList(BeanUtils.copyList(models, SysDictItemRet.class));
        }
        return pageDataRet;
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDictItem.class, id);
        }
    }
}
