package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.web.KwReceiptController;
import com.kingsware.kdev.biz.kw.web.KwWaterController;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.ApiUtils;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.service.SysApiService;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
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


    @SneakyThrows
    @Test
    void getWaterById() {
        String flowName = moduleName + "-流水";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("回单的流水", "MySql2",  "select * from kw_water where id=#{waterId}", "")
                .toEnd("");
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }



    @Test
    void query() {
        String flowName = moduleName + "-查询";
        String flowContext = createQueryFlow(flowName, this.moduleName, this.tableName);
        sysKdbFlowService.addOrUpdate(flowName, flowContext);
    }

}
