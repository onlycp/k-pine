package com.kingsware.kdev.core.kflow.define;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 *  单元测试
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 7:12 下午
 */
class FlowDefinitionTest {

    @SneakyThrows
    @Test
    void start() {

        // 创建实例
        FlowDefinition flowDefinition = FlowDefinition
                // 启动
                .start("机构管理-新增")
                // 成功支线
                .toSql("通过名称查询数量", "MySql2", "select count(1) as cnt from kw_mechanism where deleted=0 and bank_name=#{bankName} and id != #{id}", "")
                .toDecision("银行名称唯一性校验")
                .toSql("通过代码查询数量", "MySql2", "select count(1) as cnt from kw_mechanism where deleted=0 and bank_number=#{bankNumber} and id != #{id}","compare(${result[0].cnt}=0)")
                .toDecision("银行代码唯一性校验")
                .toSql("通过简称查询数量", "MySql2", "select count(1) as cnt from kw_mechanism where deleted=0 and bank_short=#{bankShort} and id != #{id}", "compare(${result[0].cnt}=0)")
                .toDecision("银行简称唯一性校验")
                .toSql("保存", "MySql2", "update kw_mechanism set bank_name=#{bankName}, bank_number=#{bankNumber}, bank_short=#{bankShort}, bank_type=#{bankType}, who_modified=#{sys.who}, when_modified=#{sys.when} where id=#{id}", "compare(${result[0].cnt}=0)")
                .toEnd("")
                // 名称不唯一
                .resetCurrentNode("银行名称唯一性校验").toEnd("compare(${result[0].cnt}>0)")
                // 代码不唯一
                .resetCurrentNode("银行代码唯一性校验").toEnd("compare(${result[0].cnt}>0)")
                // 代码不唯一
                .resetCurrentNode("银行简称唯一性校验").toEnd("compare(${result[0].cnt}>0)");
        System.out.println(flowDefinition.toJson());

    }
}
