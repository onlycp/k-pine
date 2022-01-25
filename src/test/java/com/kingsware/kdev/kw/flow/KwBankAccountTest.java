package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.web.KwEditionAccountController;
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
        List<SysApiArgv> argvs = ApiUtils.scanApis(KwEditionAccountController.class);
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
    void get() {
        String flowName = moduleName + "-详细";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("查询详细信息", "MySql2", "select * from kw_edition_account where deleted=0 and id=#{id}", "")
                .toEnd("");
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @Test
    void add() {

        String flowName = moduleName + "-新增";

        String insertSql = "insert into kw_edition_account  ( edition_id,login_type,bank_account,bank_password,cert_number,ukey_password,password_retried,usb_ip,usb_port,usb_group,is_ok_key,usb_ip_ok,usb_port_ok,usb_group_ok,status,id,who_created,when_created,who_modified,when_modified,reserve1,deleted )  values ( #{editionId},#{loginType},#{bankAccount},#{bankPassword},#{certNumber},#{ukeyPassword},#{passwordRetried},#{usbIp},#{usbPort},#{usbGroup},#{isOkKey},#{usbIpOk},#{usbPortOk},#{usbGroupOk},#{status},#{sys.uuid},#{sys.who},#{sys.when},#{sys.who},#{sys.when},#{reserve1},0)";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("通过版本和账号查询数量", "MySql2", "select count(1) as cnt from kw_edition_account where deleted=0 and bank_account=#{bankAccount} and edition_id=#{editionId}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", insertSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("唯一性校验").toJs("唯一性校验提示", "setResult('result','error|账号必须唯一！');","compare(${result[0].cnt}>0)").toEnd();
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @Test
    void edit() {

        String flowName = moduleName + "-编辑";

        String updateSql = "update kw_edition_account set edition_id=#{editionId},login_type=#{loginType},bank_account=#{bankAccount},bank_password=#{bankPassword},cert_number=#{certNumber},ukey_password=#{ukeyPassword},password_retried=#{passwordRetried},usb_ip=#{usbIp},usb_port=#{usbPort},usb_group=#{usbGroup},is_ok_key=#{isOkKey},usb_ip_ok=#{usbIpOk},usb_port_ok=#{usbPortOk},usb_group_ok=#{usbGroupOk},status=#{status},who_modified=#{sys.who},when_modified=#{sys.when},reserve1=#{reserve1} where id=#{id}";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("通过版本和账号查询数量", "MySql2", "select count(1) as cnt from kw_edition_account where deleted=0 and bank_account=#{bankAccount} and edition_id=#{editionId} and id!=#{id}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", updateSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("唯一性校验").toJs("唯一性校验提示", "setResult('result','error|账号必须唯一！');","compare(${result[0].cnt}>0)").toEnd();
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
                .toSql("删除", "MySql2", "update kw_edition_account set deleted=1 where deleted=0 and id in (#{ids})")
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
