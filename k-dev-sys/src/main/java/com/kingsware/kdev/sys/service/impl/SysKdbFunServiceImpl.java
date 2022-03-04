package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysKdbFunArgv;
import com.kingsware.kdev.sys.argv.SysKdbFunQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFunRet;
import com.kingsware.kdev.sys.service.SysKdbFunService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
public class SysKdbFunServiceImpl extends BaseServiceImpl implements SysKdbFunService {

    @Override
    public SysKdbFunRet get(String id) {
        // 参数
        FunctionQueryArgv argv = new FunctionQueryArgv();
        argv.setId(id);
        // 查询
        List<FunctionInfo> list = DB.kdbApi().queryFunction(argv);
        // 转换成ret对象
        return toRet(list.get(0));
    }

    private SysKdbFunRet toRet(FunctionInfo info) {
        SysKdbFunRet ret = new SysKdbFunRet();
        ret.setId(info.getId());
        ret.setName(info.getName());
        ret.setScript(info.getScript());
        ret.setType(info.getType());
        ret.setDesc(info.getDesc());
        if (info.getCreateTime() !=null ) {
            ret.setWhenCreated(new Timestamp(info.getCreateTime()));
        }
        else {
            ret.setWhenCreated(new Timestamp(0));
        }
        if (info.getUpdateTime() != null) {
            ret.setWhenModified(new Timestamp(info.getUpdateTime()));
        }
        if (StringUtils.isNotEmpty(info.getScript())) {
            String startRegion = "// region sample";
            String endRegion = "// endregion sample";
            if (info.getScript().startsWith(startRegion) && info.getScript().contains(endRegion)) {
                int startIndex = startRegion.length();
                int endIndex = info.getScript().indexOf(endRegion);
                // 获取样例代码
                String sample = info.getScript().substring(startIndex, endIndex);
                String[] lines = sample.split("\r?\n") ;
                StringBuilder buffer = new StringBuilder();
                for (String line: lines) {
                    buffer.append(line.replaceFirst("// ", "")).append("\n");
                }
                ret.setSample(buffer.toString());

            }
        }
        return ret;
    }

    @Override
    public void add(SysKdbFunArgv argv) {

        AddFunctionInfo info = new AddFunctionInfo();
        info.setName(argv.getName());
        info.setScript(argv.getScript());
        info.setType(argv.getType());
        info.setDesc(argv.getDesc());
        DB.kdbApi().addFun(info);
    }

    @Override
    public void edit(SysKdbFunArgv argv) {
        EditFunctionInfo info = new EditFunctionInfo();
        info.setName(argv.getName());
        info.setScript(argv.getScript());
        info.setType(argv.getType());
        info.setDesc(argv.getDesc());
        DB.kdbApi().editFun(info);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysKdbFunRet> query(SysKdbFunQueryArgv argv) {
        FunctionQueryArgv info = new FunctionQueryArgv();
        info.setName(argv.getName());
        info.setType(argv.getType());
        // 查询所有数据
        List<FunctionInfo> list = DB.kdbApi().queryFunction(info);
        // 转为ret类
        List<SysKdbFunRet> retList = new ArrayList<>();
        for (FunctionInfo infoL: list) {
            retList.add(toRet(infoL));
        }
        // 排序
        retList.sort(Comparator.comparing(SysKdbFunRet::getWhenCreated).reversed());
        return PageUtil.memoryPage(argv, retList, SysKdbFunRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.kdbApi().deleteFun(id);
        }
    }

}
