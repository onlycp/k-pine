package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 *  单元测试
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 7:12 下午
 */
class KwEditionTest {

    @SneakyThrows
    @Test
    void get() {
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start("版本管理-详细")
                // 成功支线
                .toSql("查询详细信息", "MySql2", "select * from kw_edition where deleted=0 and id=#{id}", "")
                .toEnd("");

        System.out.println(flowDefinition.toJson());

    }

    @SneakyThrows
    @Test
    void add() {

        String insertSql = "insert into kw_edition ( mechanism_id,name,password_max_retried,ukey,path,bank_type,status,description,reserve1,reserve2,reserve3,reserve4,reserve5,id,who_created,when_created,who_modified,when_modified,deleted )  values ( #{mechanismId}, #{name},#{passwordMaxRetried},#{ukey},#{path},#{bankType},#{status},#{description},#{reserve1},#{reserve2},#{reserve3},#{reserve4},#{reserve5},#{sys.uuid},#{sys.who},#{sys.when},#{sys.who},#{sys.when},0 )";
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
                .resetCurrentNode("通过机构和版本名称查询数量").toJs("唯一性校验提示", "setResult('result','{\"code\":200, \"message\":\"机构和版本号必须唯一！\"}');","compare(${result[0].cnt}>0)").toEnd();
        System.out.println(flowDefinition.toJson());

    }

    @SneakyThrows
    @Test
    void edit() {

        String updateSql = "update kw_edition set mechanism_id=#{mechanismId},name=#{name},password_max_retried=#{passwordMaxRetried},ukey=#{ukey},path=#{path},bank_type=#{bankType},status=#{status},description=#{description},reserve1=#{reserve1},reserve2=#{reserve2},reserve3=#{reserve3},reserve4=#{reserve5},reserve5=#{reserve5},who_modified=#{whoModified},when_modified=#{whenModified} where id=#{id}";
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start("版本管理-新增")
                // 成功支线
                .toSql("通过机构和版本名称查询数量", "MySql2", "select count(1) as cnt from kw_edition where deleted=0 and mechanism_id=#{mechanismId} and name=#{name} and id=#{id}")
                .toDecision("唯一性校验")
                .toSql("保存", "MySql2", updateSql, "compare(${result[0].cnt}=0)")
                .toEnd()
                // 名称不唯一
                .resetCurrentNode("通过机构和版本名称查询数量").toJs("唯一性校验提示", "setResult('result','{\"code\":200, \"message\":\"机构和版本号必须唯一！\"}');","compare(${result[0].cnt}>0)").toEnd();
        System.out.println(flowDefinition.toJson());

    }

    @SneakyThrows
    @Test
    void delete() {
        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start("机构管理-删除")
                // 成功支线
                .toSql("删除", "MySql2", "update kw_edition set deleted=1 where deleted=0 and id in (#{id})")
                .toEnd("");

        System.out.println(flowDefinition.toJson());

    }


}
