package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.orm.kdb.KdbFlowQueryArgv;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysKdbFlowServiceImpl extends BaseServiceImpl implements SysKdbFlowService {

    @Override
    public SysKdbFlowRet get(String id) {
        // 参数
        KdbFlowQueryArgv argv = new KdbFlowQueryArgv();
        argv.setFlowId(id);
        // 查询model
        KdbApi api = (KdbApi)(DB.getDefault());
        List<FlowInfo> list = api.query(argv);
        // 转换成ret对象
        return toRet(list.get(0));
    }

    private SysKdbFlowRet toRet(FlowInfo info) {
        SysKdbFlowRet flowRet = new SysKdbFlowRet();
        flowRet.setId(info.getFlowId());
        flowRet.setContent(info.getContent());
        flowRet.setName(info.getName());
        if (info.getCreateTime() !=null ) {
            flowRet.setWhenCreated(new Timestamp(info.getCreateTime()));
        }
        if (info.getUpdateTime() != null) {
            flowRet.setWhenModified(new Timestamp(info.getUpdateTime()));
        }
        flowRet.setParentId(info.getParentId());
        return flowRet;
    }

    @Override
    public void add(SysKdbFlowArgv argv) {

        FlowInfo info = new FlowInfo();
        info.setFlowId(StringUtils.getUUID());
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setParentId("0");
        KdbApi api = (KdbApi)(DB.getDefault());
        api.addFlow(info);
    }

    @Override
    public void edit(SysKdbFlowArgv argv) {
        FlowInfo info = new FlowInfo();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setParentId("0");
        info.setFlowId(argv.getId());
        KdbApi api = (KdbApi)(DB.getDefault());
        api.addFlow(info);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysKdbFlowRet> query(SysKdbFlowQueryArgv argv) {
        KdbFlowQueryArgv info = new KdbFlowQueryArgv();
        info.setContent(argv.getContent());
        info.setName(argv.getName());
        info.setParentId(argv.getParentId());
        // 查询所有数据
        KdbApi api = (KdbApi)(DB.getDefault());
        List<FlowInfo> list = api.query(info);
        // 转为ret类
        List<SysKdbFlowRet> retList = new ArrayList<>();
        for (FlowInfo infoL: list) {
            retList.add(toRet(infoL));
        }
        // 排序
        if (!retList.isEmpty()) {
            retList.sort(((o1, o2) -> {
                Timestamp t1 = o1.getWhenCreated();
                Timestamp t2 = o2.getWhenModified();
                if (t1 == null) {
                    t1 = new Timestamp(0);
                }
                if (t2 == null) {
                    t2 = new Timestamp(0);
                }
                return t2.compareTo(t1);
            }));
        }
        return PageUtil.memoryPage(argv, retList, SysKdbFlowRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        KdbApi api = (KdbApi)(DB.getDefault());
        for (String id: argv.getIds()) {
           api.deleteFlow(id);
        }
    }

}
