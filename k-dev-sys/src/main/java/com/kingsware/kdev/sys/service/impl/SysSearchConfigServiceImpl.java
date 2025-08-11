package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysSearchConfigQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import com.kingsware.kdev.sys.service.SysSearchConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SysSearchConfigServiceImpl extends BaseServiceImpl implements SysSearchConfigService {

    @Autowired private SysKdbFlowService sysKdbFlowService;

    private final String FLOW_ID = "79b218eb59f04eb485b2e58987fc3f88";

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<?> query(SysSearchConfigQueryArgv argv) {
        final String CONTENT = "{  \"name\": \"动态查询（专用，勿删）\",  \"node_definition\": [    {      \"id\": \"start\",      \"name\": \"开始\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"start\",      \"variables\": {},      \"extra\": {        \"position\": \"550,80\"      }    },    {      \"id\": \"end\",      \"name\": \"结束\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"end\",      \"variables\": {},      \"extra\": {        \"position\": \"550,1400\"      }    },    {      \"id\": \"2d469c3ff7ea4cad9110d383cf122161\",      \"name\": \"并行开始\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"fork\",      \"variables\": {},      \"extra\": {        \"position\": \"550,300\"      }    },    {      \"id\": \"c0bab83f82634b75953d96caec98de01\",      \"name\": \"查询列表数据\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"task\",      \"execute\": {        \"script\": {          \"sourceName\": \"{{sourceName}}\",          \"content\": \"<tpl>\\n\\n    select *\\n    from {{tableName}}\\n    where 1=1\\n\\n    <if test=\\\"start != null and limit != null and pageQuery != null and pageQuery == 'true' \\\">\\n        limit ${limit} offset ${start}\\n    </if>\\n\\n</tpl>\",          \"type\": \"sql\",          \"params\": [],          \"columnLabelCase\": \"normal\"        }      },      \"variables\": {},      \"listener\": {        \"before\": {          \"script\": {            \"content\": \"\\n\",            \"type\": \"js\",            \"params\": []          }        },        \"after\": {          \"script\": {            \"content\": \"setResult('list', context.get('result'));\",            \"type\": \"js\",            \"params\": []          }        }      },      \"extra\": {        \"position\": \"220,740\"      }    },    {      \"id\": \"75d04620fdc04ffa8c77a46f70a2644f\",      \"name\": \"并行结束\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"join\",      \"variables\": {},      \"extra\": {        \"position\": \"550,960\"      }    },    {      \"id\": \"e07f50ee64c7493abf93568571ebae31\",      \"name\": \"处理结果\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"task\",      \"execute\": {        \"script\": {          \"content\": \"\\nrenderPaged();\\n \",          \"type\": \"js\",          \"params\": []        }      },      \"variables\": {},      \"extra\": {        \"position\": \"550,1180\"      }    },    {      \"id\": \"8b72838304b548688fd640d4d3e67c49\",      \"name\": \"是否分页查询\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"decision\",      \"variables\": {},      \"extra\": {        \"position\": \"715,520\"      }    },    {      \"id\": \"30a7698301be48888afc2db18fc1daa0\",      \"name\": \"查询总数\",      \"auto\": true,      \"state\": \"completed\",      \"debug\": false,      \"type\": \"task\",      \"execute\": {        \"script\": {          \"sourceName\": \"{{sourceName}}\",          \"content\": \"<tpl>\\nselect count(1) as total from (\\n    select *\\n    from {{tableName}}\\n    where 1=1\\n) tmp\\n</tpl>\\n\",          \"type\": \"sql\",          \"params\": [],          \"columnLabelCase\": \"normal\"        }      },      \"variables\": {},      \"listener\": {        \"before\": {          \"script\": {            \"content\": \"\\n\",            \"type\": \"js\",            \"params\": []          }        },        \"after\": {          \"script\": {            \"content\": \"var cntList = getResult('result')\\nsetResult('total', cntList[0]['total']);\",            \"type\": \"js\",            \"params\": []          }        }      },      \"extra\": {        \"position\": \"880,740\"      }    }  ],  \"node_link\": [    {      \"id\": \"8b73102250e0430e8dcfddc7b0215f8e\",      \"name\": \"开始->并行开始\",      \"from\": \"start\",      \"to\": \"2d469c3ff7ea4cad9110d383cf122161\",      \"catch_exception\": \"false\"    },    {      \"id\": \"c419b03cbfa247b9800ba076c4dcb8d4\",      \"name\": \"并行开始->查询列表数据\",      \"from\": \"2d469c3ff7ea4cad9110d383cf122161\",      \"to\": \"c0bab83f82634b75953d96caec98de01\",      \"catch_exception\": \"false\"    },    {      \"id\": \"0ecbd98d865b4b209860982f73af9457\",      \"name\": \"查询列表数据->并行结束\",      \"from\": \"c0bab83f82634b75953d96caec98de01\",      \"to\": \"75d04620fdc04ffa8c77a46f70a2644f\",      \"catch_exception\": \"false\"    },    {      \"id\": \"cadfb568f21d484eb9cc5fc485063c96\",      \"name\": \"并行结束->处理结果\",      \"from\": \"75d04620fdc04ffa8c77a46f70a2644f\",      \"to\": \"e07f50ee64c7493abf93568571ebae31\",      \"catch_exception\": \"false\"    },    {      \"id\": \"e4ff611788db406580e11f8cd308d3a6\",      \"name\": \"并行开始->是否分页查询\",      \"from\": \"2d469c3ff7ea4cad9110d383cf122161\",      \"to\": \"8b72838304b548688fd640d4d3e67c49\",      \"catch_exception\": \"false\"    },    {      \"id\": \"b9d83b8f5eed479082de905a31c0f27c\",      \"name\": \"是否分页查询->并行结束\",      \"from\": \"8b72838304b548688fd640d4d3e67c49\",      \"to\": \"75d04620fdc04ffa8c77a46f70a2644f\",      \"conditions\": {        \"decision\": {          \"expr\": \"compare(${pageQuery}=false)\"        }      },      \"catch_exception\": \"false\"    },    {      \"id\": \"5378420880be4b8b83d1008615308d6b\",      \"name\": \"是否分页查询->查询总数\",      \"from\": \"8b72838304b548688fd640d4d3e67c49\",      \"to\": \"30a7698301be48888afc2db18fc1daa0\",      \"conditions\": {        \"decision\": {          \"expr\": \"compare(${pageQuery}=true)\"        }      },      \"catch_exception\": \"false\"    },    {      \"id\": \"be463005485f444e9f651d69f3a33a0c\",      \"name\": \"查询总数->并行结束\",      \"from\": \"30a7698301be48888afc2db18fc1daa0\",      \"to\": \"75d04620fdc04ffa8c77a46f70a2644f\",      \"catch_exception\": \"false\"    },    {      \"id\": \"855a0e59d1194ab087ae52e8bab30d16\",      \"name\": \"处理结果->结束\",      \"from\": \"e07f50ee64c7493abf93568571ebae31\",      \"to\": \"end\",      \"catch_exception\": \"false\"    }  ]}";
        String contentJson = CONTENT.replace("{{sourceName}}", argv.getSourceName())
                .replace("{{tableName}}", argv.getTableName());
        editFlow(argv, contentJson);

        // 将请求的body加进去
        Map<String, Object> argvMap = new HashMap<>();
        argvMap.put("pageSize", argv.getPageSize());
        argvMap.put("page", argv.getPage());
        argvMap.put("pageQuery", argv.isPageQuery());
        // 创建上下文
        KFlowContext context = KFlowContext.createBaseContext("{}", "{}");

        // 调用流程
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(FLOW_ID, "", argvMap, context, true, false);
        String dataJson = JsonUtil.toJson(result.getData());
        PageDataRet ret = JsonUtil.toBean(dataJson, PageDataRet.class);
        return ret;
    }

    private void editFlow(SysSearchConfigQueryArgv argv, String contentJson) {
        SysKdbFlowRet sysKdbFlowRet = sysKdbFlowService.get(FLOW_ID);
        if (sysKdbFlowRet == null) {
            BusinessException.serviceThrow(I18n.t("SysSearchConfigServiceImpl.queryFlowNotFound", "专用动态查询流程找不到"));
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
