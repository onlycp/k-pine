package com.kingsware.kdev.kw.flow;

import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.kflow.define.NodeTypeEnum;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

/**
 * //todo 描述当前类是干什么用的.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/25 10:45 上午
 */
public class BaseFlowTest {

    /**
     * 生成通用查询的流程
     * @param moduleName    模块名称
     * @param tableName     表名
     * @return              流程定义文件；
     */
    @SneakyThrows
    String createQueryFlow(String flowName, String moduleName, String tableName) {

        // limit 脚本
        String limitScript = FileUtils.readFileText(ResourceUtils.getFile("classpath:flow/pagedListLimit.html"));
        // 查询数据sql
        String querySql = FileUtils.readFileText(ResourceUtils.getFile(String.format("classpath:flow/%sQuery.html", StringUtils.lineToHump(tableName))));
        // 查询总数sql
        String queryCount = "select count(1) as total from (" + querySql + ") tmp";
        // 创建实例
        // 分页结果处理js， 从文件中读取
        String pagedJs = FileUtils.readFileText(ResourceUtils.getFile("classpath:flow/pageList.js"));
        FlowDefinition flowDefinition = FlowDefinition
                .start(flowName)
                .toNode(NodeTypeEnum.FORK, "并行开始")
                .toSqlWithAfter("查询列表数据", "MySql2", querySql + limitScript, "setResult('list',context.get('result'));" )
                .toNode(NodeTypeEnum.JOIN, "并行结束")
                .toNodeWithAfter(NodeTypeEnum.TASK, "处理结果", pagedJs)
                .toEnd()
                .resetCurrentNode("并行开始")
                .toDecision("是否分页查询")
                .toNode(NodeTypeEnum.JOIN, "并行结束", "compare(${pageQuery}=false)")
                .resetCurrentNode("是否分页查询")
                .toSqlWithAfter("查询总数", "MySql2", queryCount, "compare(${pageQuery}=true)",  "var cntList = eval(context.get('result')); setResult('total',cntList[0]['total']);")
                .toNode(NodeTypeEnum.JOIN, "并行结束");
        return flowDefinition.toJson();
    }
}
