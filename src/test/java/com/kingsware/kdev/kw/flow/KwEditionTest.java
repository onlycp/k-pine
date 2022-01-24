package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.web.KwEditionController;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.kflow.define.NodeTypeEnum;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.ApiUtils;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.service.SysApiService;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 *  单元测试
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 7:12 下午
 */
@SpringBootTest(classes = LcdApplication.class)
class KwEditionTest {

    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysKdbFlowService sysKdbFlowService;
    /** 模块名称 **/
    private String moduleName = "版本管理";

    /**
     * 扫描controller，自动自动api
     */
    @Test
    void registerApis() {
        // 获取所有的接口
        List<SysApiArgv> argvs = ApiUtils.scanApis(KwEditionController.class);
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
                .toSql("查询详细信息", "MySql2", "select * from kw_edition where deleted=0 and id=#{id}", "")
                .toEnd("");
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @Test
    void add() {

        String flowName = moduleName + "-新增";

        String insertSql = "insert into kw_edition ( mechanism_id,name,password_max_retried,ukey,path,bank_type,status,description,reserve1,reserve2,reserve3,reserve4,reserve5,id,who_created,when_created,who_modified,when_modified,deleted )  values ( #{mechanismId}, #{name},#{passwordMaxRetried},#{ukey},#{path},#{bankType},#{status},#{description},'','','','','',#{sys.uuid},#{sys.who},#{sys.when},#{sys.who},#{sys.when},0 )";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start("版本管理-新增")
                // 成功支线
                .toSql("通过机构和版本名称查询数量", "MySql2", "select count(1) as cnt from kw_edition where deleted=0 and mechanism_id=#{mechanismId} and name=#{name}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", insertSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("唯一性校验").toJs("唯一性校验提示", "setResult('result','error|机构和版本号必须唯一！');","compare(${result[0].cnt}>0)").toEnd();
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @Test
    void edit() {

        String flowName = moduleName + "-编辑";

        String updateSql = "update kw_edition set mechanism_id=#{mechanismId},name=#{name},password_max_retried=#{passwordMaxRetried},ukey=#{ukey},path=#{path},bank_type=#{bankType},status=#{status},description=#{description},'','','','','',reserve5=#{reserve5},who_modified=#{whoModified},when_modified=#{whenModified} where id=#{id}";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start(flowName)
                // 成功支线
                .toSql("通过机构和版本名称查询数量", "MySql2", "select count(1) as cnt from kw_edition where deleted=0 and mechanism_id=#{mechanismId} and name=#{name} and id!=#{id}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", updateSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("唯一性校验").toJs("唯一性校验提示", "setResult('result','error|机构和版本号必须唯一！');","compare(${result[0].cnt}>0)").toEnd();
        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }

    @Test
    void delete() {
        String flowName = moduleName + "-删除";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start("机构管理-删除")
                // 成功支线
                .toSql("删除", "MySql2", "update kw_edition set deleted=1 where deleted=0 and id in (#{ids})")
                .toEnd("");

        sysKdbFlowService.addOrUpdate(flowName, flowDefinition.toJson());

    }




}
