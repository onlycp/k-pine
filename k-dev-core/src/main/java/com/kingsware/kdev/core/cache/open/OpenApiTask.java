package com.kingsware.kdev.core.cache.open;

import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class OpenApiTask implements KTask, KRunner {

    public OpenApiTask() {
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查询所有的账号
        List<OpenAccount> openAccounts = DB.findList(OpenAccount.class, "select * from open_account where status=1");
        // 查询所有的权限接口
        List<OpenAccountApiCode> openAccountApiCodes = DB.findList(OpenAccountApiCode.class,
                "select oa.access_id, sa.api_code from open_account_api oaa\n" +
                "inner join open_account oa on oa.id= oaa.account_id\n" +
                "inner join sys_api sa on sa.id=oaa.api_id");
        Map<String, OpenAccountInfo> accountInfoMap = new HashMap<>();
        for (OpenAccount openAccount: openAccounts) {
            OpenAccountInfo info = new OpenAccountInfo();
            info.setAccessId(openAccount.getAccessId());
            info.setAuthType(openAccount.getAuthType());
            info.setSignKey(openAccount.getSignKey());
            info.setValidateSign(openAccount.getValidateSign());
            info.setValidDate(openAccount.getValidDate());
            info.setInvalidDate(openAccount.getInvalidDate());
            if (StringUtils.isNotEmpty(openAccount.getAuthParams())) {
                Map<String, Object> authMap = JsonUtil.toMap(openAccount.getAuthParams());
                if (authMap != null) {
                    info.setAuthParams(authMap);
                }
            }
            // 设置接口编码
            for (OpenAccountApiCode apiCode: openAccountApiCodes) {
                if (apiCode.getAccessId().equals(openAccount.getAccessId())) {
                    info.getApiCodes().add(apiCode.getApiCode());
                }
            }
            accountInfoMap.put(info.getAccessId(), info);
        }
        OpenApiManager.getInstance().setAccessor(accountInfoMap);
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "系统开放接口配置任务";
    }

    @Override
    public String note() {
        return "定时从数据库加载系统开放接口配置";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}
