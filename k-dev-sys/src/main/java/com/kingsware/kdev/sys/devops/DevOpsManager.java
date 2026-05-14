package com.kingsware.kdev.sys.devops;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.util.Base64Utils;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysConfigArgv;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceArgv;
import com.kingsware.kdev.sys.ret.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class DevOpsManager {

    // 单实例
    private static DevOpsManager instance;
    private  String prodToken;
    private DataCopyParam copyParam;
    private RestTemplate restTemplate;

    public static DevOpsManager getInstance() {
        if (instance == null) {
            synchronized (DevOpsManager.class) {
                if (instance == null) {
                    instance = new DevOpsManager();
                }
            }
        }
        return instance;
    }

    private DevOpsManager() {
        // 初始化
        restTemplate = SpringContext.getBean(RestTemplate.class);
    }

    /**
     * 获取上下文
     * @return
     */
    public DataCopyParam getContext() {
        return copyParam;
    }

    public void login(DataCopyParam copyParam) {
        // 获取token
        Map<String, Object> loginParams = new HashMap<>();
        loginParams.put("username", copyParam.getUsername());
        loginParams.put("password", Base64Utils.encodeToString(copyParam.getPassword().getBytes()));
        loginParams.put("code", "");
        loginParams.put("encryptCode", "");
        loginParams.put("verifyUuid", "");
        String url = copyParam.getHost() + "/api/v1/sys-users/login";

        SysUserLoginRet userLoginRet = this.call(url, HttpMethod.POST, loginParams, new ParameterizedTypeReference<BaseRet<SysUserLoginRet>>() {});
        this.prodToken = userLoginRet.getToken();
        this.copyParam = copyParam;

    }


    public <T> T call(String url, HttpMethod method, Object body,  ParameterizedTypeReference<BaseRet<T>> typeRef) {

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isNotEmpty(prodToken)) {
            headers.add("Authorization", "Bearer " + prodToken);
            headers.add("referer", "devpos");
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        // 设置请求体
        HttpEntity<String> request = new HttpEntity<>(JsonUtil.toJson(body), headers);
        // 发送 POST 请求
        ResponseEntity<BaseRet<T>> response = restTemplate.exchange(
                url,
                method,
                request,
                typeRef
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            BaseRet<T> responseBody = response.getBody();
            if (responseBody == null) {
                throw new BusinessException("请求调用失败");
            }
            if (responseBody.getCode() != 200) {
                throw new RuntimeException("请求调用异常");
            }
            return responseBody.getData();

        }
        else {
            throw new RuntimeException("请求调用失败:" + response.getStatusCode());
        }

    }

    /**
     * 获取所有的数据源
     * @return
     */
    public List<SysKdbDataSourceRet> dataSourceList() {
        String url = copyParam.getHost() + "/api/v1/sys-kdb-data-sources/query";
        Map<String, Object> params = new HashMap<>();
        params.put("pageQuery", false);
        return this.call(url, HttpMethod.GET, params, new ParameterizedTypeReference<BaseRet<PageDataRet<SysKdbDataSourceRet>>>() {}).getList();
    }

    /**
     * 获取所有的流程
     * @return
     */
    public List<SysKdbFlowRet> flowList() {
        String url = copyParam.getHost() + "/api/v1/sys-kdb-flows/query";
        Map<String, Object> params = new HashMap<>();
        params.put("pageQuery", false);
        return this.call(url, HttpMethod.GET, params, new ParameterizedTypeReference<BaseRet<PageDataRet<SysKdbFlowRet>>>() {}).getList();
    }

    public List<SysKdbFunRet> funList() {
        String url = copyParam.getHost() + "/api/v1/sys-kdb-funs/query";
        Map<String, Object> params = new HashMap<>();
        params.put("pageQuery", false);
        return this.call(url, HttpMethod.GET, params, new ParameterizedTypeReference<BaseRet<PageDataRet<SysKdbFunRet>>>() {}).getList();
    }

    /**
     * 开启开发模式
     * 历史兼容方法：不再通过写入 app.ignore.referer 放行权限
     */
    public void openDevMode() {
        // no-op for security hardening
    }

    /**
     * 关闭开发模式
     * 历史兼容方法：不再通过 app.ignore.referer 控制权限
     */
    public void closeDevMode() {
        // no-op for security hardening
    }

    /**
     * 获取流程信息
     * @param id
     * @return
     */
    public SysKdbFlowRet getFlow(String id) {
        String url = copyParam.getHost() + "/api/v1/sys-kdb-flows/" + id;
        return this.call(url, HttpMethod.GET, null, new ParameterizedTypeReference<BaseRet<SysKdbFlowRet>>() {});
    }




}
