package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.SysDictItemRet;
import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysDictItemArgv;
import com.kingsware.kdev.sys.argv.SysDictItemQueryArgv;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.model.SysDictItem;
import com.kingsware.kdev.sys.service.SysDictItemService;
import com.kingsware.kdev.sys.service.SysDictService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典数据实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/12/27 9:36 上午
 */
@Slf4j
@Service
public class SysDictItemServiceImpl extends BaseServiceImpl implements SysDictItemService {

    @Resource
    private SysDictService sysDictService;

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
        if (argv.getSysDictId() == null) {
            throw new BusinessException("找不到字典类型");
        }
        SysDict dictType = DB.findById(SysDict.class, argv.getSysDictId());
        if (dictType == null) {
            throw new BusinessException("找不到字典类型");
        }
        model.setCode(dictType.getCode());
        DB.save(model);
    }

    @Override
    public void edit(SysDictItemArgv argv) {
        SysDictItem model = DB.findById(SysDictItem.class, argv.getId());
        // 修改
        model = BeanUtils.copyObject(argv, SysDictItem.class);
        if (argv.getSysDictId() == null) {
            throw new BusinessException("找不到字典类型");
        }
        SysDict dictType = DB.findById(SysDict.class, argv.getSysDictId());
        if (dictType == null) {
            throw new BusinessException("找不到字典类型");
        }
        model.setCode(dictType.getCode());
        // 保存
        DB.update(model);
    }

    @Override
    public PageDataRet<SysDictItemRet> query(SysDictItemQueryArgv argv) {
        // 拼装sql
        StringBuilder builder = new StringBuilder();
        builder.append(" select sdi.*, sd.name as sys_dict_name from sys_dict_item  sdi ");
        builder.append(" left join sys_dict sd on sdi.sys_dict_id = sd.id ");
        builder.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(builder.toString());
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getSysDictId())) {
            wrapper.addCondition("sys_dict_id", Op.EQ, argv.getSysDictId());
        }
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("sdi.name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getCode())) {
            wrapper.addCondition("sdi.code", Op.LIKE, "%" +argv.getCode() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getGroupName())) {
            wrapper.addCondition("group_name", Op.LIKE, "%" +argv.getGroupName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ?)", argv.getAppId());
        }
        wrapper.sortBy(" order by sdi.sys_dict_id, sdi.order_num ");
        // 返回结果
        return (PageDataRet<SysDictItemRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysDictItemRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDictItem.class, id);
        }
    }

    @Override
    public Map<String, Object> getAllDict() {
        return DictManager.getInstance().getAllDict();
    }

    private List<Map<String, String>> getDetailByDictId(List<SysDictItemRet> sysDictItemList, String dictId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (SysDictItemRet detail : sysDictItemList) {
            if (detail.getSysDictId().equals(dictId)) {
                Map<String, String> map = new HashMap();
                map.put("KEY", detail.getValue());
                map.put("VALUE", detail.getName());
                list.add(map);
            }
        }
        return list;
    }

    private Map<String, String> getDetailKeyMapByDictId(List<SysDictItemRet> sysDictItemList, String dictId) {
        Map<String, String> map = new HashMap<>();
        for (SysDictItemRet detail : sysDictItemList) {
            if (detail.getSysDictId().equals(dictId) && StringUtils.isNotEmpty(detail.getValue())) {
                map.put(detail.getValue(), detail.getValue());
            }
        }
        return map;
    }

    private Map<String, String> getDetailValueMapByDictId(List<SysDictItemRet> sysDictItemList, String dictId) {
        Map<String, String> map = new HashMap<>();
        for (SysDictItemRet detail : sysDictItemList) {
            if (detail.getSysDictId().equals(dictId) && StringUtils.isNotEmpty(detail.getValue())) {
                map.put(detail.getValue(), detail.getName());
            }
        }
        return map;
    }

}
