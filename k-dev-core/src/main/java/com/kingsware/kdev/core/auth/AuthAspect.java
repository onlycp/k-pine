package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 权限切面
 * <pre>
 *     <li>
 *         1.默认所有的接口都会进行拦截；
 *     </li>
 *     <li>
 *         2. 如果某个接口不需要拦截，可以在控制层的接口加上 @ApiIgnore；
 *     </li>
 *     <li>
 *         3. 如果需要配置具体接口权限，即用户是否具备哪个接口接口权限，请通过@ApiCode进行配置。另外，同时支持配置属于哪个认证体系的，比如管理后台、或会员等等；
 *     </li>
 * </pre>
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 1:35 下午
 * @see com.kingsware.kdev.core.auth.ApiCode
 * @see com.kingsware.kdev.core.auth.ApiIgnore
 */

@Aspect
@Component
@Order(1)
public class AuthAspect {

    @Resource
    private AppAuthProperties appAuthProperties;

    @Around("execution(public * com.kingsware..web.*.*(..))")
    public Object process(ProceedingJoinPoint pjd) throws Throwable {
        // 获取方法注解
        Signature signature = pjd.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        // 如果全局跳过权限
        if (Boolean.FALSE.equals(appAuthProperties.getEnable())) {
            return pjd.proceed();
        }
        // 不验证权限
        if(targetMethod.isAnnotationPresent(ApiIgnore.class)){
            return pjd.proceed();
        }
        // 检查用户是否登录
        BaseUserInfo userInfo =  TokenUtil.getUserInfoByToken(
                KClientContext.getContext().getToken(),
                appAuthProperties.getTokenSecret(),
                appAuthProperties.getIss(),
                KClientContext.getContext().getIp(),
                appAuthProperties.getTokenExpireMinutes()
        );
        // 检查是否只有一个会话
        if (appAuthProperties.getLoginSessionOne()) {
            if (!SessionManager.getInstance().checkSession(userInfo.getId(), KClientContext.getContext().getToken())) {
                throw new UnauthorizedException(I18n.t("auth. unauthorized-e007", "用户已在别处登录"));
            }
        }
        // 将用户信息写到上下文
        KClientContext.getContext().setUserInfo(userInfo);
        return pjd.proceed();
    }
}
