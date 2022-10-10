package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.argv.CommonSqlExecutorArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysSearchConfigQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.CommonSqlExecutorService;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CommonSqlExecutorServiceImpl extends BaseServiceImpl implements CommonSqlExecutorService {

    @Autowired private SysKdbFlowService sysKdbFlowService;

    private final String FLOW_ID = "ed6a3ca33d414edb8deb28a8a8c10fee";

    @Override
    @SuppressWarnings("unchecked")
    public Object execute(CommonSqlExecutorArgv argv) {
        final String CONTENT = "{\"name\":\"内部专用通用SQL执行接口\",\"node_definition\":[{\"id\":\"start\",\"name\":\"开始\",\"auto\":true,\"state\":\"completed\",\"debug\":false,\"type\":\"start\",\"variables\":{},\"extra\":{\"position\":\"220,80\"}},{\"id\":\"end\",\"name\":\"结束\",\"auto\":true,\"state\":\"completed\",\"debug\":false,\"type\":\"end\",\"variables\":{},\"extra\":{\"position\":\"220,520\"}},{\"id\":\"ac5b697fbcd84cb0aebe41acb956462b\",\"name\":\"通用SQL执行\",\"auto\":true,\"state\":\"completed\",\"debug\":false,\"type\":\"task\",\"execute\":{\"script\":{\"sourceName\":\"{{sourceName}}\",\"content\":\"{{sql}}\",\"type\":\"sql\",\"params\":[],\"columnLabelCase\":\"normal\"}},\"variables\":{},\"listener\":{\"before\":{\"script\":{\"content\":\"var sql = context.get('sql')\\nklog.info(`开始执行通用sql: {{sql}}`)\\n\",\"type\":\"js\",\"params\":[]}},\"after\":{\"script\":{\"content\":\"var result = context.get('result')\\nklog.info(`结束执行通用sql，结果为: ${result}`)\",\"type\":\"js\",\"params\":[]}}},\"extra\":{\"position\":\"160,240\"}}],\"node_link\":[{\"id\":\"6cdd8f94f46e4d338f84bd120f6f9800\",\"name\":\"开始->通用SQL执行\",\"from\":\"start\",\"to\":\"ac5b697fbcd84cb0aebe41acb956462b\",\"catch_exception\":\"false\"},{\"id\":\"36642b1604b84f7fba30f116ba891179\",\"name\":\"通用SQL执行->结束\",\"from\":\"ac5b697fbcd84cb0aebe41acb956462b\",\"to\":\"end\",\"catch_exception\":\"false\"}]}";
        String contentJson = CONTENT.replace("{{sourceName}}", argv.getSourceName())
                .replace("{{sql}}", URLDecoder.decode(argv.getSql()));
        editFlow(argv, contentJson);

        // 将请求的body加进去
        Map<String, Object> argvMap = new HashMap<>();

        // 创建上下文
        KFlowContext context = KFlowContext.createBaseContext("{}", "{}");

        // 调用流程
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(FLOW_ID, "", argvMap, context, true);
        return result.getData();
    }

    private void editFlow(CommonSqlExecutorArgv argv, String contentJson) {
        SysKdbFlowRet sysKdbFlowRet = sysKdbFlowService.get(FLOW_ID);
        if (sysKdbFlowRet == null) {
            BusinessException.serviceThrow("专用动态查询流程找不到");
            return;
        }

        KdbApi api = (KdbApi) (DB.getDefault());
        SysKdbFlowArgv sysKdbFlowArgv = new SysKdbFlowArgv();
        sysKdbFlowArgv.setInArgv(null);
        sysKdbFlowArgv.setOutArgv(null);
        sysKdbFlowArgv.setId(sysKdbFlowRet.getId());
        sysKdbFlowArgv.setName(sysKdbFlowRet.getName());
        sysKdbFlowArgv.setTags(sysKdbFlowRet.getTags());
        sysKdbFlowArgv.setApplicationId(sysKdbFlowRet.getApplicationId());
        sysKdbFlowArgv.setContent(contentJson);
        sysKdbFlowService.edit(sysKdbFlowArgv);
    }

}
