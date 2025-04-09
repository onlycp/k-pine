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
     * 此方法通过查询系统配置来开启开发模式，如果配置不存在，则创建新配置
     * 开发模式下，会忽略referer，以便于开发和测试
     */
    public void openDevMode() {
        // 构造查询系统配置的URL
        String url = copyParam.getHost() + "/api/v1/sys-config/query";

        // 调用API获取系统配置信息
        PageDataRet<SysConfigRet> pageDataRet = this.call(url, HttpMethod.GET, new HashMap<>(), new ParameterizedTypeReference<BaseRet<PageDataRet<SysConfigRet>>>() {});
        // 定义需要查询的配置键
        String key = "app.ignore.referer";
        // 获取系统配置列表
        List<SysConfigRet> list = pageDataRet.getList();
        // 检查是否存在指定的配置项
        Optional<SysConfigRet> hasRef = list.stream().filter(item -> item.getCode().equals(key)).findFirst();
        SysConfigRet configRet = null;
        // 如果存在，则获取该配置项
        if (hasRef.isPresent()) {
            configRet = hasRef.get();
        }
        // 如果配置项不存在，则创建新的配置项并设置为忽略referer
        if (configRet == null) {
            SysConfigArgv argv = new SysConfigArgv();
            argv.setCode(key);
            argv.setName("忽略referer");
            argv.setValue("devpos");
            argv.setValueType(1);
            argv.setNote("生产环境忽略referer");
            // 调用API创建新的系统配置项
            this.call(copyParam.getHost() + "/api/v1/sys-config", HttpMethod.POST, argv , new ParameterizedTypeReference<BaseRet<Object>>() {});
        }
        // 如果配置项已存在，但需要更新其值
        else {
            String value = configRet.getValue();
            // 如果当前值非空，则追加新的值
            if (StringUtils.isNotEmpty(configRet.getValue())) {
                value += (";" + "devpos");
            }
            configRet.setValue(value);
            // 将现有的配置项复制到新的参数对象中
            SysConfigArgv argv = BeanUtils.copyObject(configRet, SysConfigArgv.class);
            // 调用API更新系统配置项
            this.call(copyParam.getHost() + "/api/v1/sys-config", HttpMethod.PUT, argv,  new ParameterizedTypeReference<BaseRet<Object>>() {});
        }
    }

    /**
     * 关闭开发模式
     * 此方法通过调用API来修改系统配置，以关闭开发模式下的特定设置
     */
    public void closeDevMode() {
        // 构造请求URL以查询系统配置
        String url = copyParam.getHost() + "/api/v1/sys-config/query";

        // 发起GET请求获取系统配置列表
        PageDataRet<SysConfigRet> pageDataRet = this.call(url, HttpMethod.GET, new HashMap<>(), new ParameterizedTypeReference<BaseRet<PageDataRet<SysConfigRet>>>() {});

        // 定义要查找的配置键
        String key = "app.ignore.referer";

        // 提取系统配置列表
        List<SysConfigRet> list = pageDataRet.getList();

        // 使用流处理检查是否存在指定的配置键
        Optional<SysConfigRet> hasRef = list.stream().filter(item -> item.getCode().equals(key)).findFirst();

        // 如果找到了指定的配置项，则准备将其删除
        if (hasRef.isPresent()) {
            // 创建用于删除操作的参数对象
            MultiIdArgv multiIdArgv = new MultiIdArgv();

            // 创建一个集合来存储要删除的配置项ID
            Set<String> ids = new HashSet<>();

            // 将找到的配置项ID添加到集合中
            ids.add(hasRef.get().getId());

            // 设置要删除的ID集合到参数对象
            multiIdArgv.setIds(ids);

            // 发起POST请求删除指定的系统配置项
            this.call(copyParam.getHost() + "/api/v1/sys-config/delete", HttpMethod.POST, multiIdArgv,  new ParameterizedTypeReference<BaseRet<Object>>() {});
        }
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
