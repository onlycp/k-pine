package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.sys.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Component
@Order(1)
@DependsOn("springContext")
@Slf4j
public class UserPasswordLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String mode = SpringContext.getProperties("encrypt.mode", "base64");
        if ("base64".equalsIgnoreCase(mode)) {
            SqlWrapper sqlWrapper = new SqlWrapper("select id, username, password from sys_user where 1=1");
            sqlWrapper.addCondition("username", Op.EQ, "chenp");
            sqlWrapper.notlike("password", "enc#");
            List<SysUser> users = DB.findList(SysUser.class, sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
            for (SysUser user : users) {
                String originPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes(StandardCharsets.UTF_8)));
                String newPassword = EncryptWorker.getInstance().encrypt(originPassword, user.getUsername());
                DB.executeUpdateSql("update sys_user set password = ? where id = ?", newPassword, user.getId());
            }
        }


    }
}
