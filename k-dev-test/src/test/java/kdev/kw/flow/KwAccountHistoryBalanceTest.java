package kdev.kw.flow;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.web.KwAccountHistoryBalanceController;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.ApiUtils;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.service.SysApiService;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
class KwAccountHistoryBalanceTest extends BaseFlowTest{

    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysKdbFlowService sysKdbFlowService;
    /** 模块名称 **/
    private String moduleName = "账单历史余额";
    /** 模块 **/
    private String tableName = "kw_account_history_balance";

    /**
     * 扫描controller，自动自动api
     */
    @Test
    void registerApis() {
        // 获取所有的接口
        List<SysApiArgv> argvs = ApiUtils.scanApis(KwAccountHistoryBalanceController.class);
        // 增加系统
        for (SysApiArgv argv: argvs) {
            // 先判断有无
            SysApi api = DB.findOne(SysApi.class, "select * from sys_api where api_name=?", argv.getApiName());
            if (api == null) {
                sysApiService.add(argv);
            }
        }
    }




    @Test
    void query() {
        String flowName = moduleName + "-查询";
        String flowContext = createQueryFlow(flowName, this.moduleName, this.tableName);
        sysKdbFlowService.addOrUpdate(flowName, flowContext);
    }

}
