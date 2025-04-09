package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.bean.SqlSegment;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.SqlUtils;
import com.kingsware.kdev.sys.model.DevModelSql;
import com.kingsware.kdev.sys.service.DevModelSqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/3 20:19
 */
@Slf4j
@Service
public class DevModelSqlServiceImpl implements DevModelSqlService {
    /**
     * 执行特定应用程序ID下的所有SQL脚本
     * 此方法首先从数据库中查找与给定应用程序ID关联的所有源名称，然后对每个源名称，
     * 它调用自身来执行该源下的所有SQL脚本此方法确保即使有重复的源名称，也只执行一次
     *
     * @param appId 应用程序ID，标识要执行SQL脚本的应用程序
     */
    @Override
    public void execute(String appId) {
        // 查找与给定应用程序ID关联的所有源名称
        List<String> sources = DB.findList(String.class, "select source_name from dev_model_sql where app_id=?", appId);
        // 对每个源名称执行SQL脚本，确保去重执行
        sources.stream().distinct().forEach(sourceName -> execute(appId, sourceName));
    }

    /**
     * 执行特定应用程序ID和源名称下的所有SQL脚本
     * 此方法针对一个应用程序ID和源名称组合，从数据库中查找所有SQL脚本，
     * 并根据它们的状态决定是否跳过脚本，然后逐个执行它们
     *
     * @param appId 应用程序ID，标识要执行SQL脚本的应用程序
     * @param sourceName 源名称，标识SQL脚本的来源
     */
    @Override
    public void execute(String appId, String sourceName) {
        // 查找特定应用程序ID和源名称下的所有SQL脚本，并按版本号升序排序
        List<DevModelSql> devModelSqlList = DB.findList(DevModelSql.class, "select * from dev_model_sql where app_id=? and source_name=? order by sql_version asc", appId, sourceName);
        // 遍历SQL脚本列表，根据状态决定是否跳过脚本，然后执行
        for (DevModelSql devModelSql : devModelSqlList) {
            // 如果SQL脚本状态为成功（1）或失败但忽略错误（2且ignore_except为1），则跳过执行
            if (devModelSql.getStatus() == 1 || (devModelSql.getStatus() == 2 && devModelSql.getIgnoreExcept() == 1)) {
                continue;
            }
            // 执行当前SQL脚本
            execute(devModelSql);
        }
    }

    @Override
    /**
     * 执行给定的SQL脚本
     * 此方法首先将SQL内容拆分为单独的行，然后解析这些行以找到可执行的SQL段落
     * 随后，它会执行每个SQL段落，并处理执行过程中可能发生的异常
     * 如果所有SQL段落都成功执行，则返回true；如果存在执行错误且未设置为忽略错误，则返回false
     *
     * @param devModelSql 包含SQL内容及其相关元数据的模型对象
     * @return 如果所有SQL段落成功执行或devModelSql配置为忽略错误，则返回true；否则返回false
     */
    public void execute(DevModelSql devModelSql) {
        if (devModelSql.getStatus() == 1) {
            throw BusinessException.serviceThrow(I18n.t("mode.sql.execute.success","SQL已执行成功，请不要重复执行"));
        }
        // 将sql内容拆分为单独的行
        String[] lines = devModelSql.getContent().split("\n");
        // 解析SQL行以找到可执行的SQL段落
        List<SqlSegment> segments = SqlUtils.parseSql(Arrays.asList(lines));
        // 如果是之前执行异常的SQL，则从异常行开始执行
        List<SqlSegment> newSegments = segments.stream().filter(it -> it.getLine() >= devModelSql.getExecErrLine()).collect(Collectors.toList());
        // 遍历并执行每个SQL段落
        for (SqlSegment segment : newSegments) {
            try {
                // 执行SQL段落
                SqlUtils.executeSql(devModelSql.getSourceName(), segment.getSql());
            } catch (Exception e) {
                // 记录执行SQL时发生的错误
                log.error("执行SQL出错，SQL：{}", segment.getSql(), e);
                // 更新执行错误行号
                devModelSql.setExecErrLine(segment.getLine());
                devModelSql.setMessages(e.getMessage());
                devModelSql.setStatus(2);
                devModelSql.setExecTime(DateUtils.getNow());
                devModelSql.setExecUserId((KClientContext.getContext()!=null && KClientContext.getContext().getUserInfo() != null) ? KClientContext.getContext().getUserInfo().getId(): "");
                DB.update(devModelSql);
                throw BusinessException.serviceThrow(I18n.t("mode.sql.exception","执行SQL出错，应用ID:{0}, 数据源：{1}, SQL:{2}, 异常信息:{3} ", devModelSql.getAppId(), devModelSql.getSourceName(), segment.getSql(), e.getMessage()));
            }
        }
        // 更新执行状态
        devModelSql.setExecErrLine(0);
        devModelSql.setStatus(1);
        devModelSql.setExecTime(DateUtils.getNow());
        devModelSql.setExecUserId((KClientContext.getContext()!=null && KClientContext.getContext().getUserInfo() != null) ? KClientContext.getContext().getUserInfo().getId(): "");
        DB.update(devModelSql);
    }

    @Override
    public void executeById(String id) {
        DevModelSql devModelSql = DB.findById(DevModelSql.class, id);
        execute(devModelSql);
    }

}
