package com.kingsware.kdev.sys.task;

import com.kingsware.kdev.core.bean.CategoryData;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.SysDataAccessResource;
import com.kingsware.kdev.sys.model.SysDataResource;
import com.kingsware.kdev.sys.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenp
 * @date 2024/4/17
 */
@Slf4j
public class KUserPasswordTask implements KTask, KRunner {
    /**
     * 马上运行
     */
    @Override
    public void runNow() throws Exception {
        this.execute();
    }

    /**
     * 执行任务
     **/
    @Override
    public void execute() throws Exception {
        String mode = SpringContext.getProperties("encrypt.mode", "base64");
        if ("base64".equalsIgnoreCase(mode)) {
            // 判断是否已经转换
            boolean hasConvert = hasConvertCount();
            boolean enablePasswordTask = SpringContext.getProperties("sys.user.password.task.enable", "true").equalsIgnoreCase("true");
            if (!hasConvert || enablePasswordTask) {
                SqlWrapper sqlWrapper = new SqlWrapper("select id, username, password from sys_user where 1=1");
//            sqlWrapper.addCondition("username", Op.EQ, "chenp");
                sqlWrapper.notlike("password", "enc#");
                List<SysUser> users = DB.findList(SysUser.class, sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
                for (SysUser user : users) {
                    if (StringUtils.isNotEmpty(user.getPassword())) {
                        String originPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes(StandardCharsets.UTF_8)));
                        String newPassword = EncryptWorker.getInstance().encrypt(originPassword, user.getUsername());
                        DB.executeUpdateSql("update sys_user set password = ? where id = ?", newPassword, user.getId());
                    }

                }
            }

        }
    }

    /**
     * 是否已经转换
     * @return
     */
    private boolean hasConvertCount() {
        SqlWrapper sqlWrapper = new SqlWrapper("select count(1) from sys_user where 1=1");
        sqlWrapper.ilike("password", "enc#");
        long count =  DB.findCount(sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
        return count > 0;
    }

    /**
     * 表达式
     **/
    @Override
    public String cron() {
        return "0 0/2 * * * ?";
    }

    /**
     * 名称
     **/
    @Override
    public String name() {
        return "用户密码安全增强";
    }

    @Override
    public String note() {
        return "用户密码安全增强";
    }
}
