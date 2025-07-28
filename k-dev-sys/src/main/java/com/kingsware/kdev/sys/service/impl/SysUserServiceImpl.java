package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.*;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cache.api.ApiResultCacheManager;
import com.kingsware.kdev.core.cache.config.ConfigManager;
import com.kingsware.kdev.core.cache.config.SysConfigInfo;
import com.kingsware.kdev.core.cache.core.SysCacheService;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.cache.permssion.PermissionManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.constants.PropertiesConstant;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.bean.PasswordRuleResult;
import com.kingsware.kdev.sys.model.*;
import com.kingsware.kdev.sys.ret.*;
import com.kingsware.kdev.sys.service.SysUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

    @Resource
    private AppAuthProperties appAuthProperties;
    @Resource
    private EncryptProperties encryptProperties;

    @Value("${encrypt.aes.secret:PsLZlcuUJBUB8yPo}")
    private String aesSecret;

    @Resource
    private SysCacheService sysCacheService;

    private final String VALIDATION_CODE_CACHE_KEY = "VerificationCode:";

    @Override
    public SysUserRet get(String id) {
        // 查询model
        SysUser model = DB.findById(SysUser.class, id);
        // 转换成ret对象
        SysUserRet userRet = (SysUserRet) model2Ret(model, SysUserRet.class);
        // 获取角色信息
        List<String> roleIds = DB.findSingleAttributeList(String.class, "select r.id from sys_user_role ur inner join sys_role r on r.id=ur.sys_role_id where ur.sys_user_id=?", model.getId());
        if (!roleIds.isEmpty()) {
            Set<String> set = new HashSet<>(roleIds);
            userRet.setSysRoleIds(StringUtils.joinToString(new ArrayList<>(set), ","));
        }

        // 查询单位
        List<String> unitIds = DB.findSingleAttributeList(String.class, "select r.id from sys_user_unit ur inner join sys_unit r on r.id=ur.sys_unit_id where ur.sys_user_id=?", model.getId());
        if (!unitIds.isEmpty()) {
            Set<String> set = new HashSet<>(unitIds);
            userRet.setSysUnitIds(StringUtils.joinToString(new ArrayList<>(set), ","));
        }
        return userRet;
    }

    @Override
    public void add(SysUserArgv argv) {
        SysUser model = BeanUtils.copyObject(argv, SysUser.class);
        if(StringUtils.isEmpty(model.getId())) {
            model.setId(StringUtils.getUUID());
        }
        // 把参数里的加密密码解密出来
        model.setPassword(decodeBase64(argv.getPassword()));
        // 设置密码
        model.setPassword(EncryptWorker.getInstance().encrypt(model.getPassword(), model.getUsername()));
        // 唯一性校验
        DBChecker<SysUser> checker = DBChecker.build(model, SysUser.class);
        // 名称唯一
        checker.uni("username", I18n.t("SysUser.username.unique", "用户账户必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.save(model);
        // 保存用户和角色的关系
        saveUserRoles(model.getId(), argv.getSysRoleIds());
        // 保存用户和部门的关系
        saveUserUnits(model.getId(), argv.getSysUnitIds());

    }

    /**
     * 保存用户和角色的关系
     *
     * @param userId     用户id
     * @param sysRoleIds 角色ids
     */
    private void saveUserRoles(String userId, String sysRoleIds) {
        if (StringUtils.isNotEmpty(sysRoleIds)) {
            String[] roleIds = sysRoleIds.trim().split(",");
            // 去重用
            Set<String> idSet = new HashSet<>();
            idSet.addAll(Arrays.asList(roleIds));
            List<SysUserRole> userRoles = new ArrayList<>();
            for (String roleId : idSet) {
                SysUserRole userRole = new SysUserRole();
                userRole.setSysUserId(userId);
                userRole.setSysRoleId(roleId);
                userRoles.add(userRole);
            }
            DB.saveAll(userRoles);
        }
    }

    /**
     * 保存用户和部门的关系
     *
     * @param userId     用户id
     * @param sysUnitIds 部门ids
     */
    private void saveUserUnits(String userId, String sysUnitIds) {
        if (StringUtils.isNotEmpty(sysUnitIds)) {
            String[] unitIds = sysUnitIds.trim().split(",");
            // 去重用
            Set<String> idSet = new HashSet<>();
            idSet.addAll(Arrays.asList(unitIds));
            List<SysUserUnit> userUnits = new ArrayList<>();
            for (String unitId : idSet) {
                SysUserUnit userUnit = new SysUserUnit();
                userUnit.setSysUserId(userId);
                userUnit.setSysUnitId(unitId);
                userUnits.add(userUnit);
            }
            DB.saveAll(userUnits);
        }
    }

    @Override
    public void edit(SysUserArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getId());
        model.setRealName(argv.getRealName());
        model.setMobile(argv.getMobile());
        model.setEmail(argv.getEmail());
        model.setSex(argv.getSex());
        model.setPost(argv.getPost());
        model.setNote(argv.getNote());
        model.setStatus(argv.getStatus());
        // 保存
        DB.update(model);
        // 处理用户和角色的关系
        // 先移除所有关联
        DB.executeUpdateSql("delete from sys_user_role where sys_user_id=?", model.getId());
        saveUserRoles(model.getId(), argv.getSysRoleIds());
        // 处理用户和部门的关系
        // 先移除所有关联
        DB.executeUpdateSql("delete from sys_user_unit where sys_user_id=?", model.getId());
        saveUserUnits(model.getId(), argv.getSysUnitIds());
    }

    /**
     * 工具方法：通过部门id查询出该部门及其下级部门的所有部门id
     *
     * @param sysUnitIds 部门id数组
     */
    @SuppressWarnings("unchecked")
    private Set<Object> getAllUnitIdByUnitId(String[] sysUnitIds) {
        //1.最终结果id的集合
        Set<Object> ids = new HashSet<>();
        //2.一次性查询出所有部门
        List<SysUnit> allUnit = DB.findList(SysUnit.class, "select * from sys_unit");
        //3.如果前端点击包含顶级部门,部门id为null的也应该被查出来
        boolean isRoot = false;
        for (String sysUnitId : sysUnitIds) {
            isRoot = allUnit.stream().anyMatch(s -> s.getId().equals(sysUnitId) && s.getParentId() == null);
        }
        if (isRoot) {
            //直接返回null用于作为不筛选条件
            return null;
        } else {
            //4.否则根据部门id进行筛选(递归)
            for (String sysUnitId : sysUnitIds) {
                getChildUnitIdById(allUnit, ids, sysUnitId);
            }
            return ids;
        }
    }

    /**
     * 工具方法：递归过程
     *
     * @param allUnit 数据库当中的所有部门
     * @param ids     最终结果ids的集合
     * @param id      当前部门id
     */
    private void getChildUnitIdById(List<SysUnit> allUnit, Set<Object> ids, String id) {
        //1.添加当前的部门id
        ids.add(id);
        //2.取下级部门id（递归结束条件）
        List<SysUnit> childUnitList = allUnit.stream().filter(s -> s.getParentId() != null && s.getParentId().equals(id)).collect(Collectors.toList());
        if (childUnitList.size() == 0) {
            return;
        }
        //3.递归子过程
        for (SysUnit unit : childUnitList) {
            getChildUnitIdById(allUnit, ids, unit.getId());
        }
    }

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysUserRet> query(SysUserQueryArgv argv) {
        // 拼装sql
//        SqlWrapper wrapper = new SqlWrapper("select u.*, un.name as sys_unit_name, " +
//                "un.path as sys_unit_path  " +
//                "from sys_user u " +
//                "left join sys_unit un on un.id=u.sys_unit_id " +
////                "left join sys_user_role sur on sur.sys_user_id=u.id " +
//                "where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper("select * from sys_user u where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getUsername())) {
            wrapper.addCondition("u.username", Op.LIKE, "%" + argv.getUsername() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getRealName())) {
            wrapper.addCondition("u.real_name", Op.LIKE, "%" + argv.getRealName() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getMobile())) {
            wrapper.addCondition("u.mobile", Op.LIKE, "%" + argv.getMobile() + "%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("u.status", Op.EQ, argv.getStatus());
        }
        // 支持前端根据部门id过滤
        if (StringUtils.isNotEmpty(argv.getSysUnitIds())) {
            // 切割
            String[] split = argv.getSysUnitIds().split(",");
            // 只想查询出在此部门列表中的用户（部门及所有子部门）
            Set<Object> unitIds = getAllUnitIdByUnitId(split);
            // 使用null作为是否开启过滤的判断条件
            if (unitIds != null) {
                // 想要的用户id列表
                SqlWrapper unitWrapper = new SqlWrapper("select suu.sys_user_id from sys_user_unit suu where 1=1 ");
                unitWrapper.in("suu.sys_unit_id", unitIds);
                List<SysUserUnit> unitList = DB.findList(SysUserUnit.class, unitWrapper.getSql(), unitWrapper.getParams().toArray(new Object[0]));
                List<Object> list = unitList.stream().map(SysUserUnit::getSysUserId).collect(Collectors.toList());
                // 拼接最终sql
                wrapper.in("u.id", list.size() > 0 ? list : Collections.singletonList(""));
            }
        }
//        if (argv.getRoleId() != null) {
//            wrapper.addCondition("sur.sys_role_id", Op.EQ, argv.getRoleId());
//        }
        if (argv.getRoleId() != null) {
            SqlWrapper roleWrapper = new SqlWrapper("select sys_user_id from sys_user_role where 1=1 ");
            roleWrapper.in("sys_role_id", Collections.singletonList(argv.getRoleId()));
            List<SysUserRole> roleList = DB.findList(SysUserRole.class, roleWrapper.getSql(), roleWrapper.getParams().toArray(new Object[0]));
            List<Object> list = roleList.stream().map(SysUserRole::getSysUserId).collect(Collectors.toList());
            wrapper.in("u.id", list.size() > 0 ? list : Collections.singletonList(""));
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (u.app_id = ? or u.app_id is null)", argv.getAppId());
        }
        String orderString = "order by ";
        if (StringUtils.isNotEmpty(argv.getOrderBy())) {
            argv = PageUtil.mergeQueryOrder(argv, SysUserQueryArgv.class);
            orderString += (argv.getOrderBy() + " " + argv.getSort());
        } else {
            orderString += "u.when_created desc";
        }
        wrapper.sortBy(orderString);
        // 用户信息
        PageDataRet<SysUserRet> pageDataRet = (PageDataRet<SysUserRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysUserRet.class);
        // 查询角色信息
        // 先获取用户id
        List<Object> userIds = pageDataRet.getList().stream().map(SysUserRet::getId).distinct().collect(Collectors.toList());
        if (!userIds.isEmpty()) {
            SqlWrapper roleWrapper = new SqlWrapper("select sys_user_id, sr.name from sys_user_role sur inner join sys_role sr on sr.id=sur.sys_role_id where 1=1 ");
            roleWrapper.in("sur.sys_user_id", userIds);
            // 将角色名称查询出来
            List<SysUserRoleName> sysUserRoleNames = DB.findList(SysUserRoleName.class, roleWrapper.getSql(), roleWrapper.getParams().toArray(new Object[0]));
            // 根据用户信息来合并
            for (SysUserRet userRet : pageDataRet.getList()) {
                // 查找用户的角色名称
                List<String> myRoleNames = sysUserRoleNames.stream().filter(it -> it.getSysUserId().equalsIgnoreCase(userRet.getId())).map(SysUserRoleName::getName).collect(Collectors.toList());
                userRet.setSysRoleNames(StringUtils.joinToString(myRoleNames, ","));
            }
        }
        //查询部门信息
        if (!userIds.isEmpty()) {
            SqlWrapper unitWrapper = new SqlWrapper("select suu.sys_user_id, su.id as sys_unit_id, su.name, su.path from sys_user_unit suu inner join sys_unit su on suu.sys_unit_id = su.id where 1=1 ");
            unitWrapper.in("suu.sys_user_id", userIds);
            // 将部门名称和路径查询出来
            List<SysUserUnitName> sysUserUnitNames = DB.findList(SysUserUnitName.class, unitWrapper.getSql(), unitWrapper.getParams().toArray(new Object[0]));
            // 根据用户信息来合并
            for (SysUserRet userRet : pageDataRet.getList()) {
                // 查找用户的部门id、名称和路径
                List<SysUserUnitName> userUnitNameList = sysUserUnitNames.stream().filter(it -> it.getSysUserId().equalsIgnoreCase(userRet.getId())).collect(Collectors.toList());
                List<String> myUnitIds = userUnitNameList.stream().map(SysUserUnitName::getSysUnitId).collect(Collectors.toList());
                List<String> myUnitNames = userUnitNameList.stream().map(SysUserUnitName::getName).collect(Collectors.toList());
                List<String> myUnitPaths = userUnitNameList.stream().map(SysUserUnitName::getPath).collect(Collectors.toList());
                userRet.setSysUnitIds(StringUtils.joinToString(myUnitIds, ","));
                userRet.setSysUnitNames(StringUtils.joinToString(myUnitNames, ","));
                userRet.setSysUnitPaths(StringUtils.joinToString(myUnitPaths, ","));
            }
        }
        return pageDataRet;
    }

    @Override

    public void delete(MultiIdArgv argv) {
        for (String id : argv.getIds()) {
            // 移除关联的部门
            DB.executeUpdateSql("delete from sys_user_unit where sys_user_id=?", id);
            // 移除关联的角色
            DB.executeUpdateSql("delete from sys_user_role where sys_user_id=?", id);
            // 最后移除用户记录
            DB.delete(SysUser.class, id);
        }
    }



    @Override
    @SuppressWarnings("all")
    public SysUserLoginRet login(Map<String, Object> argv) throws Exception {
        try {
            Integer codeShowWhenErrorCount = SpringContext.getInt("app.auth.login-rule.password-error-show-count", 0);

            SysUserLoginRet ret = new SysUserLoginRet();
            Map<String, Object> beforeFlowResultMap = new HashMap<>();
            Map<String, Object> afterParams = new HashMap<>();
            boolean hasValid = false;
            boolean isValid = false;
            boolean useUsernamePassword = true;
            String message = null;
            String userId = null;


            // 获取认证模式
            String authMode = argv.getOrDefault("mode", "pwd").toString();
            if(StringUtils.isEmpty(authMode)) {
                authMode = "pwd";
            }
            // 遍历处理密码字段
            for (Map.Entry<String, Object> entry: argv.entrySet()) {
                String key = entry.getKey();
                String lowerKey = key.toLowerCase();
                if(lowerKey.contains("password")) {
                    Object value = entry.getValue();
                    if (value != null && StringUtils.isNotEmpty(value.toString())) {
                        String loginBySM2 = SpringContext.getProperties("app.loginBySM2", PropertiesConstant.FALSE);
                        String decryptPassword = argv.get(key).toString();
                        if (PropertiesConstant.TRUE.equals(loginBySM2)) {
                            decryptPassword = FaasInvoke.loginDecrypt(decryptPassword);
                        } else {
                            decryptPassword = decodeBase64(argv.get(key).toString());
                        }
                        argv.put(key, decryptPassword);
                    }

                }
            }
            String username = "";
            if(argv.containsKey("username") || argv.get("username") != null) {
                username = argv.get("username").toString();
            }
            // 查找认证源
            if (!"pwd".equalsIgnoreCase(authMode)) {
                SysAuthSource sysAuthSource = DB.findOne(SysAuthSource.class, "select * from sys_auth_source where code=?", authMode);
                if (sysAuthSource == null) {

                    throw BusinessException.serviceThrow(I18n.t("SysUser.tip.modeNotSupport", "认证不支持！") );
                }
                if (sysAuthSource.getStatus() != 1) {
                    throw BusinessException.serviceThrow(I18n.t("SysUser.tip.modeNotEnable","认证未启用！"));
                }
                // 获取视图模型
                KFlowContext context = KFlowContext.createBaseContext( "{}",  "{}");
                // 加入应用id
                Map<String, Object> flowArgvMap = new HashMap<>(argv);
                flowArgvMap.put("authId", sysAuthSource.getId());
                // 调用流程
                log.info("流程执行参数:{}", JsonUtil.toJson(flowArgvMap));
                KdbFlowResult result = KdbFlowExecutor.getInstance().execute(sysAuthSource.getLogicFlowId(), "", flowArgvMap, context, false, false);
                log.info("流程执行参数:{}, 返回结果:{}", JsonUtil.toJson(flowArgvMap), JsonUtil.toJson(result));
                if (result.getData() instanceof ErrorResult) {
                    ErrorResult errorResult = (ErrorResult) result.getData();
                    throw BusinessException.serviceThrow(errorResult.getMessage());
                }
                if(!(result.getData() instanceof Map)) {
                    throw BusinessException.serviceThrow(I18n.t("SysUser.tip.resultCheckFail", "认证逻辑响应结果不符合预期，应返回json对象字符串！"));
                }
                Map<String, Object> authRetMap = (Map<String, Object>)result.getData();
                username = authRetMap.get("username").toString();
                argv.put("username", username);
                // 设置不用用户名和密码
                useUsernamePassword = false;
                KClientContext.getContext().setValidatePassFlag(false);
            }
            afterParams.put("isLogined", false);
            // 调用前置登录处理
            if (!KFlowContext.isDevMode()) {
                SysConfigInfo beforeFlow = ConfigManager.getInstance().getItem("application.customLoginBeforeFlow");
                if (beforeFlow != null && StringUtils.isNotEmpty(beforeFlow.getValue())) {
                    KFlowContext beforeIc = KFlowContext.createBaseContext("{}", "{}");
                    KdbFlowResult beforeFlowResult = KdbFlowExecutor.getInstance().execute(beforeFlow.getValue(), "", argv, beforeIc, false, false);
                    beforeFlowResultMap = (Map<String, Object>) beforeFlowResult.getData();
                }

            }
            if (beforeFlowResultMap != null) {
                hasValid = (boolean) beforeFlowResultMap.getOrDefault("hasValid", false);
                isValid = (boolean) beforeFlowResultMap.getOrDefault("isValid", false);
                useUsernamePassword = (boolean) beforeFlowResultMap.getOrDefault("useUsernamePassword", true);
                message = (String) beforeFlowResultMap.get("message");
                userId = (String) beforeFlowResultMap.getOrDefault("userId", null);
            }
            if (hasValid && !isValid) {
                throw BusinessException.serviceThrow(message != null ? message : I18n.t("SysUser.tip.authFail", "验证未通过！") );
            }


            // 拼装sql
            final Integer ENABLE_STATUS = 1;
            String cacheKey = "";
            String lockCacheKey = "";
            SqlWrapper wrapper = new SqlWrapper("select * from sys_user where 1=1 ");
            if (useUsernamePassword) {
                wrapper.addCondition("username", Op.EQ, argv.get("username").toString());
                cacheKey = "u_lgoin_pwd_fail." +  argv.get("username").toString();
                lockCacheKey = "u_lgoin_pwd_lock." +  argv.get("username").toString();
            } else {
                wrapper.addCondition("id", Op.EQ, userId);
                cacheKey = "u_lgoin_pwd_fail." +  userId;
                lockCacheKey = "u_lgoin_pwd_lock." +  userId;
            }
            // 如果是锁定状态，则不允许登录
            long userLockMinutes = Integer.parseInt(SpringContext.getProperties("user.login.lock-minutes", "10"));
            long userLockMs = userLockMinutes *  60 * 1000;
            String lockCache = sysCacheService.getCache(lockCacheKey);
            if (StringUtils.isNotEmpty(lockCache)) {
                long lockTime = Long.parseLong(lockCache);

                if((lockTime + userLockMs) >= System.currentTimeMillis())  {
                    // 移除当前登录次数缓存
                    sysCacheService.removeCache(cacheKey);
                    throw BusinessException.serviceThrow(I18n.t("SysUser.tip.userLocked", "用户已被锁定，请稍后再试！") );
                }
                // 移除
                sysCacheService.removeCache(lockCacheKey);
            }

            wrapper.addCondition("status", Op.EQ, ENABLE_STATUS);
            SysUser model = DB.findOne(SysUser.class, wrapper.getSql(), wrapper.getParams().toArray());
            String countCache = sysCacheService.getCache(cacheKey);
            int userLoginPasswordErrorCount = countCache == null? 0 : Integer.parseInt(countCache);
            int allowErrorCount = Integer.parseInt(SpringContext.getProperties("user.login.allow-error-count", "5"));
            int countOfLeave = allowErrorCount - userLoginPasswordErrorCount;

            // 校验图片验证码
            String openValidationCode = SpringContext.getProperties("application.openValidateCode", PropertiesConstant.FALSE);
            if (KClientContext.getContext() != null &&KClientContext.getContext().isValidateCodeFlag() && PropertiesConstant.TRUE.equals(openValidationCode)
                    || (codeShowWhenErrorCount > 0 && userLoginPasswordErrorCount > codeShowWhenErrorCount) || (argv.get("code") != null &&  StringUtils.isNotEmpty(argv.get("code").toString()))) {
                String encryptCode = (String) argv.get("encryptCode");
                String verifyUuid = (String) argv.get("verifyUuid");
                String code = (String) argv.get("code");
                boolean codeValid = checkVerifyCode(verifyUuid, code, encryptCode);
                // 默认校验验证码的是为真
                if (!codeValid) {
                    throw BusinessException.serviceThrow(I18n.t("SysUser.tip.codeFail", "验证码有误！"), true);
                }
            }

            String errorMessage = I18n.t("SysUser.tip.fail1","用户名或密码有误，您还有{0}次机会输入，达到次数之后，账户将被锁定{1}分钟", countOfLeave, userLockMinutes);
            if (model == null) {
                Boolean showCode = false;
                if(countOfLeave == 0)   {
                    sysCacheService.setCache(lockCacheKey, System.currentTimeMillis() + "");
                    // 移除当前登录次数缓存
                    sysCacheService.removeCache(cacheKey);
                    throw BusinessException.serviceThrow(I18n.t("SysUser.tip.fail2","由于密码错误连续次数已达到{0}次，用户被锁定{1}分钟", allowErrorCount, userLockMinutes));
                }
                // 记录密码错误
                userLoginPasswordErrorCount++;
                if (codeShowWhenErrorCount > 0 && userLoginPasswordErrorCount > codeShowWhenErrorCount ) {
                    showCode = true;
                }
                sysCacheService.setCache(cacheKey, userLoginPasswordErrorCount+"");
                throw BusinessException.serviceThrow(errorMessage, showCode);
            }
            if (useUsernamePassword && KClientContext.getContext().isValidatePassFlag()) {
                Boolean showCode = false;
                // 把参数里的加密密码解密出来
                Map<String, Object> encryptContext = JsonUtil.beanToMap(model);
                if (!EncryptWorker.getInstance().validate(argv.get("password").toString(), model.getPassword(), model.getUsername())) {
                    if(countOfLeave == 0)   {
                        sysCacheService.setCache(lockCacheKey, System.currentTimeMillis() + "");
                        throw BusinessException.serviceThrow(I18n.t("SysUser.tip.fail3","密码错误连续次数已达到{0}次，用户将被锁定{1}分钟", allowErrorCount, userLockMinutes));
                    }
                    // 记录密码错误
                    userLoginPasswordErrorCount++;
                    if (codeShowWhenErrorCount > 0 && userLoginPasswordErrorCount > codeShowWhenErrorCount ) {
                        showCode = true;
                    }
                    sysCacheService.setCache(cacheKey, userLoginPasswordErrorCount+"");
                    throw BusinessException.serviceThrow(errorMessage, showCode);
                }
            }
            // 移除
            sysCacheService.removeCache(cacheKey);
            // 构建返回数据
            BaseUserInfo userInfo = new BaseUserInfo();
            userInfo = BeanUtils.copyObject(model, BaseUserInfo.class);
            // 获取角色信息
            Map<String, String> roleMap = getRoleIds(getRolesByUserId(model.getId()));
            userInfo.setRoleIds(roleMap.get("roleIds"));
            userInfo.setRoleNames(roleMap.get("roleNames"));
            userInfo.setRoleCodes(roleMap.get("roleCodes"));
            // 获取部门信息
            Map<String, String> unitMap = getUnitIds(getUnitsByUserId(model.getId()));
            userInfo.setSysUnitIds(unitMap.get("unitIds"));
            userInfo.setSysUnitNames(unitMap.get("unitNames"));
            // 获取数据权限id
            String accessSql = "select sys_data_access_id from sys_data_access_user au inner join sys_data_access da on (da.id=au.sys_data_access_id and da.status=1) where au.sys_user_id=? or au.sys_user_id in (select sys_unit_id from sys_user_unit where sys_user_id=?)";
            List<String> accessIds = DB.findSingleAttributeList(String.class, accessSql, model.getId(), model.getId());

            if (userInfo.getRoleIds() == null || userInfo.getRoleIds().isEmpty()) {
                throw new UnauthorizedException(I18n.t("SysUser.tip.notRoles", "你当前没有权限访问系统功能，请联系业务部门授权后，再访问系统。"));
            }
            userInfo.setAccessIds(StringUtils.joinToString(accessIds, ","));
            // 保存登录会话
            // 如果只允许一个登录会话, 那么先将之前的会话删除
            if (appAuthProperties.getLoginSessionOne()) {
                List<SysOnlineUser> onlineUsers = DB.findList(SysOnlineUser.class, "select * from sys_online_user  where user_id =?", model.getId());
                DB.executeUpdateSql("delete from sys_online_user where user_id = ?", model.getId());
                for (SysOnlineUser onlineUser : onlineUsers) {
                    // 发送上线通知
                    InstanceManager.getInstance().broadMessage("session-remove", JsonUtil.toJson(onlineUser));
                }
            }
            // 查找当前会话是否已经有令牌，如果有，就用已有的
            List<SysOnlineUser> onlineUsers = DB.findList(SysOnlineUser.class, "select * from sys_online_user  where user_id =? and login_ip =? order by when_created desc", userInfo.getId(), KClientContext.getContext().getIp());
            // 只取生成时间小于1分钟的- 1000*60
            onlineUsers = onlineUsers.stream().filter(it-> it.getWhenCreated().getTime() > (System.currentTimeMillis() )).collect(Collectors.toList());
            SysOnlineUser existOnlineUser = (onlineUsers != null && onlineUsers.size() > 0) ? onlineUsers.get(0) : null;
            // 当前用户在前端自己清空浏览器缓存或使用无痕模式时，请求
            boolean hasToken = KClientContext.getContext().getToken() != null;
            // 当前用户在前端自己清空浏览器缓存或使用无痕模式时，认为用户不想使用缓存里的onlineUser数据，重新取出
            if (!hasToken || existOnlineUser == null) {
                // 获取会话id
                String kSessionId = ServletUtil.getCookie("k_session_id", "");
                TokenPair tokenPair = TokenUtil.createToken(appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), KClientContext.getContext().getIp(), kSessionId, userInfo);
                String token = tokenPair.getToken();
                if (StringUtils.isEmpty(token)) {
                    throw BusinessException.serviceThrow(message != null ? message : I18n.t("SysUser.tip.loginFail", "登录失败，请检查登录信息是否有误！"));
                }
                // 创建在线用户
                existOnlineUser = new SysOnlineUser();
                existOnlineUser.setId(StringUtils.getUUID());
                existOnlineUser.setUserId(model.getId());
                existOnlineUser.setLoginIp(KClientContext.getContext().getIp());
                existOnlineUser.setLoginTime(new Timestamp(System.currentTimeMillis()));
                existOnlineUser.setLoginToken(token);
                // 区分是jwt还是session
                if (appAuthProperties.getMockSessionExpireMinutes() <= 0) {
                    existOnlineUser.setExpireTime(new Timestamp(System.currentTimeMillis() + ((long) appAuthProperties.getTokenExpireMinutes() * 60 * 1000)));
                } else {
                    existOnlineUser.setExpireTime(new Timestamp(System.currentTimeMillis() + ((long) appAuthProperties.getMockSessionExpireMinutes() * 60 * 1000)));
                }
                // 保存
                DB.save(existOnlineUser);
            }
            else {
                if (appAuthProperties.getMockSessionExpireMinutes() <= 0) {
                    existOnlineUser.setExpireTime(new Timestamp(System.currentTimeMillis() + ((long) appAuthProperties.getTokenExpireMinutes() * 60 * 1000)));
                }
                else {
                    existOnlineUser.setExpireTime(new Timestamp(System.currentTimeMillis() + ((long) appAuthProperties.getMockSessionExpireMinutes() * 60 * 1000)));
                }
                DB.update(existOnlineUser);
            }

            ret.setToken(MD5Utils.md5(existOnlineUser.getLoginToken()));
            // 发送上线通知
            InstanceManager.getInstance().broadMessage("session-add", JsonUtil.toJson(existOnlineUser));
            // 加载权限
            PermissionManager.getInstance().refreshPermissions(userInfo.getRoleIds());
            afterParams.put("isLogined", true);

            // 调用后置登录处理
            if (!KFlowContext.isDevMode()) {
                SysConfigInfo afterFlow = ConfigManager.getInstance().getItem("application.customLoginBeforeFlow");
                if (afterFlow != null && StringUtils.isNotEmpty(afterFlow.getValue())) {
                    KFlowContext afterIc = KFlowContext.createBaseContext("{}", "{}");
                    afterParams.putAll(beforeFlowResultMap);
                    afterParams.putAll(argv);
                    afterParams.put("password", null);
                    KdbFlowResult afterFlowResult = KdbFlowExecutor.getInstance().execute(afterFlow.getValue(), "", afterParams, afterIc, false, false);
                    Object afterFlowResultMap = afterFlowResult.getData();
                    ret.setOtherParams(afterFlowResultMap);
                }
            }
            // 处理登录策略
            String passwordCheckVersion = SpringContext.getProperties("app.auth.password-check-version", "v1");
            if ("v2".equals(passwordCheckVersion)) {
                try {
                    Map<String, Object> map = checkLoginRule(userInfo.getId(), userInfo.getUsername());
                    ret.setAction(Integer.parseInt(map.get("code").toString()));
                    ret.setActionMessage(map.get("tip").toString());
                }
                catch (Exception e) {
                    log.warn("warn", e);
                }
            }

            return ret;
        }
        catch (Exception e) {
            log.error("error",e);
            throw e;
        }

    }

    /**
     * 校验密码
     * @param userId
     * @param username
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> checkLoginRule(String userId, String username) {
        String checkFlowId = "8319fd0d136044a68253978bab24c48a";
        // 判断流程是否存在，兼容旧版本
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("username", username);
        Map<String, Object> variables = new HashMap<>();
        variables.put("variables", JsonUtil.toJson(params));

        KdbFlowResult result = FaasInvoke.callFlow(checkFlowId, variables);
        return (Map<String, Object>) result.getData();
    }

    @Override
    public SysUserLoginRet loginByApiKey(String apiKey) throws Exception {
        ApiKey key = TokenUtil.parseApiKey(appAuthProperties.getTokenSecret(),apiKey);
        // 根据账号查询用户并进行登录
        SysUser user = DB.findOne(SysUser.class, "select username, password from sys_user where username=?", key.getUsername());
        SysUserLoginArgv loginArgv = new SysUserLoginArgv();
        loginArgv.setUsername(user.getUsername());
        loginArgv.setPassword(user.getPassword());
        // 调用登录接口
        if (KClientContext.getContext() != null) {
            KClientContext.getContext().setValidateCodeFlag(false);
        }
        SysUserLoginRet ret = this.login(JsonUtil.beanToMap(loginArgv));
        return ret;
    }

    @Override
    public ApiKeyResult createApiKey(String appId, String host) {

        long expireTime = System.currentTimeMillis() + 7*24*60*60*1000;
        String apiKey = TokenUtil.createApiKey(appAuthProperties.getTokenSecret(), appId, host, KClientContext.getContext().getUserInfo().getId(), KClientContext.getContext().getUserInfo().getUsername(), expireTime);
        ApiKeyResult apiKeyResult = new ApiKeyResult();
        apiKeyResult.setApiKey(apiKey);
        return apiKeyResult;
    }


    @Override
    public void changePassword(SysUserChangePasswordArgv argv, String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
        SysUser model = DB.findById(SysUser.class, userInfo.getId());
        if (model == null) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.loginExpire", "登录凭证已失效，请重新登录！"));
        }
        // 把参数里的加密密码解密出来
        argv.setOldPassword(decodeBase64(argv.getOldPassword()));
        argv.setNewPassword(decodeBase64(argv.getNewPassword()));

        if (!EncryptWorker.getInstance().validate(argv.getOldPassword(), model.getPassword(), model.getUsername())) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.oldPasswordFail", "旧密码有误！") );
        }
        if (EncryptWorker.getInstance().validate(argv.getNewPassword(), model.getPassword(), model.getUsername())) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.passwordSame", "重置的密码不可以与旧密码一样！") );
        }
        model.setPassword(EncryptWorker.getInstance().encrypt(argv.getNewPassword(), model.getUsername()));
        // 保存
        DB.update(model);
        // 保存到密码日志
        this.savePasswordLog(model.getId());
    }

    private void savePasswordLog(String userId) {
        try {
            DB.executeUpdateSql("insert into sys_password_log(id, user_id, when_created) values(?,?,?)", StringUtils.getUUID(), userId, DateUtils.getNow());
        }
        catch (Exception ignored) {
        }
    }

    @Override
    public BaseUserInfo getBaseUserInfo(String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
//        userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //获取角色拥有的权限
        Set<String> permissions = getMenuPermission(userInfo);
        BaseUserInfoExt userInfoExt = BeanUtils.copyObject(userInfo, BaseUserInfoExt.class);
        userInfoExt.setPermissions(permissions);
        return userInfoExt;
    }

    /**
     * 工具方法：userInfo->拿到角色id列表->根据角色id查询角色拥有的菜单权限字符api_codes
     */
    private Set<String> getMenuPermission(BaseUserInfo userInfo) {
        Set<String> perms = new HashSet<>();
        //管理员拥有所有权限
        if (StringUtils.isNotEmpty(userInfo.getRoleCodes())) {
            String[] roleCodes = userInfo.getRoleCodes().split(",");
            if (roleCodes.length > 0) {
                boolean isAdmin = Arrays.stream(roleCodes).anyMatch(s -> s.equalsIgnoreCase("admin"));
                if (isAdmin) {
                    perms.add("*:*:*");
                } else {
                    //多角色菜单权限遍历
                    String[] roleIds = userInfo.getRoleIds().split(",");
                    for (String roleId : roleIds) {
                        String sql = "SELECT u.* FROM sys_role_menu r left JOIN sys_menu u ON r.sys_menu_id = u.id\n" +
                                "where sys_role_id = ?";
                        List<SysMenu> sysMenuList = DB.findList(SysMenu.class, sql, roleId);
                        //获取当前角色的的菜单权限字符api_codes
                        List<String[]> apiCodeList = sysMenuList.stream()
                                .map(SysMenu::getApiCodes)
                                .filter(StringUtils::isNotEmpty)
                                .map(s -> s.split(","))
                                .collect(Collectors.toList());
                        for (String[] apiCode : apiCodeList) {
                            perms.addAll(Arrays.asList(apiCode));
                        }
                    }
                }
            }
        }

        return perms;
    }

    @Override
    public SysUserProfileRet getProfile(String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
        SysUser model = DB.findById(SysUser.class, userInfo.getId());
        SysUserProfileRet ret = BeanUtils.copyObject(model, SysUserProfileRet.class);
        Map<String, String> roleMap = getRoleIds(getRolesByUserId(model.getId()));
        ret.setRoleIds(roleMap.get("roleIds"));
        ret.setRoleNames(roleMap.get("roleNames"));
        Map<String, String> unitMap = getUnitIds(getUnitsByUserId(model.getId()));
        ret.setSysUnitNames(unitMap.get("unitNames"));
        return ret;
    }

    @Override
    public void logout() {
        // 清空登录会话
        SysOnlineUser onlineUser = DB.findOne(SysOnlineUser.class, Expr.builder().add("loginToken", "=", KClientContext.getContext().getToken()).build());
        if (onlineUser != null) {
            DB.delete(onlineUser);
            // 发送上线通知
            InstanceManager.getInstance().broadMessage("session-remove", JsonUtil.toJson(onlineUser));
            SessionManager.getInstance().removeSession(onlineUser.getUserId(), onlineUser.getLoginToken());
        }
        // 清除接口缓存
        ApiResultCacheManager.getInstance().clearMyCache();
    }

    @Override
    public Long onlineCount(String username) {
        // 空用户判断
        if (StringUtils.isEmpty(username)) {
            return 0L;
        }
        // 如果不启用检验，那么直接返回0
        if (!appAuthProperties.getLoginSessionOne()) {
            return 0L;
        }
        String userId = DB.findSingleAttribute(String.class, "select id from sys_user where username=?", username);
        if (StringUtils.isEmpty(userId)) {
            return 0L;
        }
        // 获取会话id
        String kSessionId = ServletUtil.getCookie("k_session_id", "");
        return SessionManager.getInstance().activeCount(userId, kSessionId);

    }

    @Override
    public void encryptChange(String from, String to, String secret) {
        if (StringUtils.isEmpty(secret)) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.aesPasswordEmpty", "AES密码不能为空"));
        }
        if (StringUtils.isEmpty(from)) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.sourceMethodEmpty", "源加密方法不能为空") );
        }
        if (StringUtils.isEmpty(from)) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.dstMethodEmpty", "目标加密方法不能为空"));
        }
        if (!secret.equals(encryptProperties.getAes().getSecret())) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.aesSecretyFail", "AES密钥不正确"));
        }
        Set<String> supportEncrypts = new HashSet<>();
        supportEncrypts.add("aes");
        supportEncrypts.add("base64");
        if (!supportEncrypts.contains(from) || !supportEncrypts.contains(to)) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.aesbase64", "当前只支持aes和base64加解密方式"));
        }
        // 读取所有的用户
        List<SysUser> users = DB.findList(SysUser.class, Collections.emptyList());
        for (SysUser user : users) {
            String originPassword = null;
            if (from.equals("base64")) {
                originPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes(StandardCharsets.UTF_8)));
            } else {
                originPassword = AESUtil.decrypt(user.getPassword(), encryptProperties.getAes().getSecret());
            }
            if (!StringUtils.isAsciiPrintable(originPassword)) {
                throw BusinessException.serviceThrow(I18n.t("SysUser.tip.encryptFail1","解密之后的字符串不可读，请重新确认加解密方法"));
            }
            if (StringUtils.isEmpty(originPassword)) {
                throw BusinessException.serviceThrow(I18n.t("SysUser.tip.encryptFail2", "存在密码无法解密，请重新确认加解密方法"));
            }

            String afterPassword = null;
            if ("base64".equals(to)) {
                afterPassword = new String(Base64.getEncoder().encode(originPassword.getBytes(StandardCharsets.UTF_8)));
            } else {
                afterPassword = AESUtil.encrypt(originPassword, encryptProperties.getAes().getSecret());
            }
            if (StringUtils.isEmpty(afterPassword)) {
                throw BusinessException.serviceThrow(I18n.t("SysUser.tip.encryptFail3", "存在密码无法加密，请重新确认加解密方法"));
            }
            user.setPassword(afterPassword);
        }
        DB.updateAll(users);
    }

    @Override
    public void resetPassword(SysUserResetPasswordArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getUserId());
        if (model == null) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.userNotFound", "用户不存在！"));
        }
        // 把参数里的加密密码解密出来
        argv.setPassword(decodeBase64(argv.getPassword()));
        if (EncryptWorker.getInstance().validate(argv.getPassword(), model.getPassword(), model.getUsername())) {
            throw BusinessException.serviceThrow(I18n.t("SysUser.tip.passwordSame", "重置的密码不可以与旧密码一样！"));
        }
        model.setPassword(EncryptWorker.getInstance().encrypt(argv.getPassword(), model.getUsername()));
        // 保存
        DB.update(model);
        // 保存密码日志
        this.savePasswordLog(model.getId());
    }

    @Override
    public void ping() {
        try {
            SessionManager.getInstance().getByToken(KClientContext.getContext().getUserInfo().getId(), KClientContext.getContext().getToken()).ping();
        } catch (Exception e) {
            log.warn("error", e);
        }

    }

    /**
     * 用户密码校验（及非开发模式的自定义校验）
     *
     * @param password
     * @param appId
     * @return
     */
    @Override
    public Map<String, Object> passwordValidate(String password, String appId) {
        String passwordCheckVersion = SpringContext.getProperties("app.auth.password-check-version", "v1");
        // 判断流程是否存在，兼容旧版本
        if("v1".equals(passwordCheckVersion)) {
            final String passwordValidateKey = "application.user.passwordValidate";
            final String passwordValidateMessageKey = "application.user.passwordValidateMessage";
            SysConfigInfo passwordValidate = ConfigManager.getInstance().getItem(passwordValidateKey);
            SysConfigInfo passwordValidateMessage = ConfigManager.getInstance().getItem(passwordValidateMessageKey);
            String validate = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_-]+$)(?![a-z0-9]+$)(?![a-z\\W_-]+$)(?![0-9\\W_-]+$)[a-zA-Z0-9\\W_-]";
            String validateMessage = I18n.t("SysUser.tip.passwordRule1", "必须包含大写字母，小写字母，数字，特殊符号'_-'中任意3项");
            AppModeProperties appModeProperties = SpringContext.getBean(AppModeProperties.class);
            // 仅非开发模式可用自定义密码校验
            if (!appModeProperties.getDev() && passwordValidate != null) {
                validate = passwordValidate.getValue();
            }
            if (!appModeProperties.getDev() && passwordValidateMessage != null) {
                validateMessage = passwordValidateMessage.getValue();
            }
            Map<String, Object> resultMap = new HashMap<>();
            Pattern p = Pattern.compile(validate);
            resultMap.put("success", p.matcher(password).find());
            resultMap.put("message", validateMessage);

            return resultMap;
        }
        else {
            PasswordRuleResult result = this.checkPasswordRule(KClientContext.getContext().getUserInfo().getId(), password);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("success", result.isSuccess());
            if (!result.isSuccess()) {
                resultMap.put("message", result.getErrorMessages().get(0));
            }
            return resultMap;

        }

    }

    /**
     * 校验密码
     * @param userId
     * @param password
     * @return
     */
    private PasswordRuleResult checkPasswordRule(String userId, String password) {
        String checkFlowId = "6eb9934cff5d4af581920096a49a423e";
        // 判断流程是否存在，兼容旧版本
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);
        params.put("username", KClientContext.getContext().getUserInfo().getUsername());
        Map<String, Object> variables = new HashMap<>();
        variables.put("variables", JsonUtil.toJson(params));

        KdbFlowResult result = FaasInvoke.callFlow(checkFlowId, variables);
        PasswordRuleResult passwordRuleResult = JsonUtil.mapToBean((Map)result.getData(), PasswordRuleResult.class);
        return passwordRuleResult;
    }

    @Override
    public void editProfile(SysUserProfileArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getId());
        model.setRealName(argv.getRealName());
        model.setMobile(argv.getMobile());
        model.setEmail(argv.getEmail());
        model.setAvatar(argv.getAvatar());
        model.setSex(argv.getSex());
        // 保存
        DB.update(model);
    }

    /**
     * 把List<SysRole>
     *
     * @param list
     * @return
     */
    private Map<String, String> getRoleIds(List<SysRole> list) {
        Map<String, String> roleMap = new HashMap<>();
        StringBuilder roleIds = new StringBuilder();
        StringBuilder roleNames = new StringBuilder();
        StringBuilder roleCodes = new StringBuilder();
        list.forEach(item -> {
            if (roleIds.length() != 0) {
                roleIds.append(",");
            }
            roleIds.append(item.getId());
            if (roleNames.length() != 0) {
                roleNames.append(",");
            }
            roleNames.append(item.getName());
            if (roleCodes.length() != 0) {
                roleCodes.append(",");
            }
            roleCodes.append(item.getCode());
        });
        roleMap.put("roleIds", roleIds.toString());
        roleMap.put("roleNames", roleNames.toString());
        roleMap.put("roleCodes", roleCodes.toString());
        return roleMap;
    }


    private List<SysRole> getRolesByUserId(String userId) {
        // 拼装sql
        final Integer ENABLE_STATUS = 1;
        SqlWrapper wrapper = new SqlWrapper("select sr.* from sys_user_role sur left join sys_role sr on sr.id = sur.sys_role_id where 1=1 ");
        wrapper.addCondition("sys_user_id", Op.EQ, userId);
        wrapper.addCondition("sr.status", Op.EQ, ENABLE_STATUS);
        return DB.findList(SysRole.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    private Map<String, String> getUnitIds(List<SysUnit> list) {
        Map<String, String> unitMap = new HashMap<>();
        StringBuilder unitIds = new StringBuilder();
        StringBuilder unitNames = new StringBuilder();
        StringBuilder unitPaths = new StringBuilder();
        list.forEach(item -> {
            if (unitIds.length() != 0) {
                unitIds.append(",");
            }
            unitIds.append(item.getId());
            if (unitNames.length() != 0) {
                unitNames.append(",");
            }
            unitNames.append(item.getName());
            if (unitPaths.length() != 0) {
                unitPaths.append(",");
            }
            unitPaths.append(item.getPath());
        });
        unitMap.put("unitIds", unitIds.toString());
        unitMap.put("unitNames", unitNames.toString());
        unitMap.put("unitPaths", unitPaths.toString());
        return unitMap;
    }

    private List<SysUnit> getUnitsByUserId(String userId) {
        final Integer ENABLE_STATUS = 1;
        SqlWrapper wrapper = new SqlWrapper("select su.* from sys_user_unit suu left join sys_unit su on su.id = suu.sys_unit_id where 1=1 ");
        wrapper.addCondition("sys_user_id", Op.EQ, userId);
        wrapper.addCondition("su.status", Op.EQ, ENABLE_STATUS);
        return DB.findList(SysUnit.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    private String decodeBase64(String source) {
        return new String(Base64.getDecoder().decode(source));
    }

    @Override
    public BaseRet<VerificationCodeRet> getVerificationCode() throws IOException {
        ByteArrayOutputStream outputStream = null;
        try {
            int width = 200;
            int height = 69;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = VerifyCodeUtils.drawRandomText(width, height, verifyImg);
            VerificationCodeRet ret = new VerificationCodeRet();
            //单独的一个类方法，出于代码复用考虑，进行了封装。
            //功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(verifyImg, "jpg", outputStream);
            byte[] bytes = outputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(bytes);
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();//关闭流
            }
            log.info("base64String: ", base64String);
            ret.setEncryptCode(AESUtil.encrypt(randomText, aesSecret));
            log.info("getEncryptCode: ", ret.getEncryptCode());
            ret.setImageBase64String("data:image/jpeg;base64," + base64String);
            ret.setUuid(UUID.randomUUID().toString());
            sysCacheService.setCache(VALIDATION_CODE_CACHE_KEY + ret.getUuid(), randomText);
            return BaseRet.success(ret);
        } catch (IOException e) {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();//关闭流
            }
            log.error("生成验证码有误： ", e);
        }
        return BaseRet.failMessage(I18n.t("SysUser.tip.codeFail1", "生成验证码有误") );
    }

    @Override
    public BaseRet<?> validVerificationCode(String uuid, String code, String encryptCode) {
        return checkVerifyCode(uuid, code, encryptCode) ? BaseRet.success(true) : BaseRet.failMessage(I18n.t("SysUser.tip.codeFail2", "验证码有误！"));
    }

    @Override
    public void addRoles(SysUserArgv argv) {
        String userIds = argv.getSysUserIds();
        if (StringUtils.isNotEmpty(userIds)) {
            String[] userIdArr = userIds.trim().split(",");
            for (String userId : userIdArr) {
                saveUserRoles(userId, argv.getSysRoleIds());
            }
        }
    }

    private boolean checkVerifyCode(String uuid, String code, String encryptCode) {
//        String decodedCode = AESUtil.decrypt(encryptCode, aesSecret);
//        boolean valid = decodedCode.equalsIgnoreCase(code);
        String cacheCode = VALIDATION_CODE_CACHE_KEY + uuid;
        String sysCache = sysCacheService.getCache(cacheCode);
        boolean valid = StringUtils.isNotEmpty(code) && code.equalsIgnoreCase(sysCache);
        sysCacheService.removeCache(cacheCode);
        return valid;
    }
}
