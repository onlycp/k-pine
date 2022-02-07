package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.web.KwReceiptController;
import com.kingsware.kdev.biz.kw.web.KwWaterController;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.util.ApiUtils;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.argv.SysViewModelArgv;
import com.kingsware.kdev.sys.argv.SysViewModelFieldArgv;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.model.SysViewModel;
import com.kingsware.kdev.sys.service.SysApiService;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import com.kingsware.kdev.sys.service.SysViewModelService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  单元测试
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 7:12 下午
 */
@SpringBootTest(classes = LcdApplication.class)
class KwReceiptTest extends BaseFlowTest{

    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysKdbFlowService sysKdbFlowService;
    @Resource
    private SysViewModelService sysViewModelService;
    /** 模块名称 **/
    private String moduleName = "回单查询管理";
    /** 模块 **/
    private String tableName = "kw_receipt";

    /**
     * 扫描controller，自动自动api
     */
    @Test
    void registerApis() {
        // 获取所有的接口
        List<SysApiArgv> argvs = ApiUtils.scanApis(KwReceiptController.class);
        // 增加系统
        for (SysApiArgv argv: argvs) {
            // 先判断有无
            SysApi api = DB.findOne(SysApi.class, "select * from sys_api where api_name=?", argv.getApiName());
            if (api == null) {
                sysApiService.add(argv);
            }
//            else {
//                argv.setId(api.getId());
//                sysApiService.edit(argv);
//            }
        }
    }


    @Test
    void query() {
        String flowName = moduleName + "-查询";
        String flowContext = createQueryFlow(flowName, this.moduleName, this.tableName);
        sysKdbFlowService.addOrUpdate(flowName, flowContext);
    }

    @Test
   void createModel() {
        SysViewModelArgv sysViewModelArgv = new SysViewModelArgv();
        sysViewModelArgv.setName("回单模型");
        sysViewModelArgv.setTag("回单管理");
        sysViewModelArgv.setNote("这个人很懒");
        // 定义属性列
        int index = 0;
        List<SysViewModelFieldArgv> fieldArgvs = new ArrayList<>();
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("ID").setField("id").setType("String").setHidden(1).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("项目名称").setField("proName").setType("String").setHidden(0).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("开户行").setField("bankDeposit").setType("String").setHidden(0).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("付款人账户").setField("draweeAccountNumber").setType("String").setHidden(0).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("收款人账户").setField("payeeAccountNumber").setType("String").setHidden(0).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("交易金额（元)").setField("amount").setType("String").setHidden(0).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("数据来源").setField("source").setType("Integer").setHidden(0).setOrderNum(index++));
        fieldArgvs.add(new SysViewModelFieldArgv().setLabel("流水").setField("hasWater").setType("String").setHidden(0).setOrderNum(index++));
        sysViewModelArgv.setFields(fieldArgvs);
        // 判断模型是否已存在
        SysViewModel old  = DB.findOne(SysViewModel.class, Expr.builder().add("name", "=", sysViewModelArgv.getName()).build());
        if (old == null) {
            sysViewModelService.add(sysViewModelArgv);
        }
        else {
            sysViewModelArgv.setId(old.getId());
            sysViewModelService.edit(sysViewModelArgv);
        }
    }

    @Test
    void export() {
        String flowName = moduleName + "-导出";
        String flowContext = createExportFlow(flowName, this.tableName);
        sysKdbFlowService.addOrUpdate(flowName, flowContext);
    }

}
