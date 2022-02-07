package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.web.KwBankAccountController;
import com.kingsware.kdev.biz.kw.web.KwEditionAccountController;
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
class KwBankAccountTest extends BaseFlowTest{

    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysKdbFlowService sysKdbFlowService;
    /** 模块名称 **/
    private String moduleName = "账户管理";
    /** 模块 **/
    private String tableName = "kw_bank_account";

    /**
     * 扫描controller，自动自动api
     */
    @Test
    void registerApis() {
        // 获取所有的接口
        List<SysApiArgv> argvs = ApiUtils.scanApis(KwBankAccountController.class);
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
    void get() {
        String flowName = moduleName + "-详细";
        String sql = FileUtils.readFileText(ResourceUtils.getFile("classpath:flow/kwBankAccountDetail.html"));
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("查询详细信息", "MySql2",  sql, "")
                .toEnd("");
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @SneakyThrows
    @Test
    void add() {

        String flowName = moduleName + "-新增";
        String insertSql = FileUtils.readFileText(ResourceUtils.getFile("classpath:flow/kwBankAccountAdd.html"));
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                .toJs("数据预处理", "var relationType = context.get('relationType'); if(relationType == undefined || relationType=='') {context.set('relationType', 0);}")
                // 成功支线
                .toSql("通过版本和账户查询数量", "MySql2", "select count(1) as cnt from kw_bank_account where deleted=0 and account=#{account} and edition_id=#{editionId}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", insertSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("唯一性校验").toJs("唯一性校验提示", "setResult('result','error|机构账户必须唯一！');","compare(${result[0].cnt}>0)").toEnd();
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @SneakyThrows
    @Test
    void edit() {

        String flowName = moduleName + "-编辑";

        String updateSql = FileUtils.readFileText(ResourceUtils.getFile("classpath:flow/kwBankAccountEdit.html"));        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                .toJs("数据预处理", "var relationType = context.get('relationType'); if(relationType == undefined || relationType=='') {context.set('relationType', 0);}")
                // 成功支线
                .toSql("通过版本和账户查询数量", "MySql2", "select count(1) as cnt from kw_bank_account where deleted=0 and account=#{account} and edition_id=#{editionId} and id != #{id}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", updateSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("唯一性校验").toJs("唯一性校验提示", "setResult('result','error|机构账户必须唯一！');","compare(${result[0].cnt}>0)").toEnd();
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @Test
    void delete() {
        String flowName = moduleName + "-删除";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("删除", "MySql2", "update kw_bank_account set deleted=1 where deleted=0 and id in (#{ids})")
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
