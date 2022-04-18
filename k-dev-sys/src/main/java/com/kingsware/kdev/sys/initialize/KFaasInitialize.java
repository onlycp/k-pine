package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * k-faas初始化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/28 4:37 下午
 */
@Component
@Slf4j
public class KFaasInitialize implements SystemInitialize {

    @Value("${database.initDatasourcePath:.}")
    private String initDatasourcePath;

    @Override
    public void execute() {

        // 1. 初始化数据源
        // 读取文件
        String dbConfigFilePath = initDatasourcePath + "/db.json";
        File dbConfigFile = new File(dbConfigFilePath);
        if (dbConfigFile.exists()) {
            String text = FileUtils.readFileText(dbConfigFile);
            if (StringUtils.isNotEmpty(text)) {
                List<DataSourceInfo> dataSourceFromFile = JsonUtil.toListBean(text, DataSourceInfo.class);
                if (dataSourceFromFile != null) {
                    // 获取所有的数据源
                    List<DataSourceInfo> dataSourceInfos = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());
                    for (DataSourceInfo fileSource: dataSourceFromFile) {
                        // 查看是否已存在
                        Optional<DataSourceInfo> optional = dataSourceInfos.stream().filter(it -> it.getSourceName().equals(fileSource.getSourceName())).findFirst();
                        // 如果已存储，则修改
                        if (optional.isPresent()) {
                            DB.kdbApi().editDataSource(fileSource);
                            log.info("数据源初始化修改: {}" , fileSource);
                        }
                        else {
                            DB.kdbApi().addDataSource(fileSource);
                            log.info("数据源初始化新增: {}" , fileSource);
                        }
                    }

                }
            }
        }


        // 2. 修改基础流程
        FlowInfo flowInfo = DB.kdbApi().get("base_flow");
        // 转为流程定义
        FlowDefinition flowDefinition = JsonUtil.toBean(flowInfo.getContent(), FlowDefinition.class);
        if (flowDefinition != null) {
            // 获取所有的数据源
            List<DataSourceInfo> dataSourceInfos = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());
            // 根据数据源生成流程
            for (DataSourceInfo info: dataSourceInfos) {
                // 判断是否已存在，不存在才会加进去
                boolean has = flowDefinition.getNodeDefinitions().stream().anyMatch(it -> it.getId().equals(info.getSourceName()));
                if (!has) {
                    String expr = "compare(${nodeId}=" + info.getSourceName() + ")";
                    flowDefinition.resetCurrentNode("数据源分支节点").toSqlWithId(info.getSourceName(), info.getSourceName(), info.getSourceName(), "select version()", expr).toEnd();
                }
            }
            // 重新保存
            EditFlowInfo editFlowInfo = new EditFlowInfo();
            editFlowInfo.setFlowId(flowInfo.getFlowId());
            editFlowInfo.setName("基础流程");
            editFlowInfo.setContent(flowDefinition.toJson());
            editFlowInfo.setDescription(flowInfo.getDescription());
            DB.kdbApi().editFlow(editFlowInfo);
        }



    }

    @Override
    public int sort() {
        return 0;
    }
}
