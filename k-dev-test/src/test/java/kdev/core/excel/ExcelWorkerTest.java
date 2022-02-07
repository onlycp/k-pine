package kdev.core.excel;

import com.kingsware.kdev.LcdApplication;
import com.kingsware.kdev.biz.kw.model.KwMechanism;
import com.kingsware.kdev.core.excel.format.RegionDictReverseFormat;
import com.kingsware.kdev.core.excel.format.RegionModelIdFormat;
import com.kingsware.kdev.core.util.JsonUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Data
class KwWaterArgv {
    // 行别（机构）
    private String mechanismId;
    // 版本
    private String editionName;
    // 账户
    private String account;
    // 是否异常 abnormal
    private Integer abnormal;
}

/**
 * 单元测试
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/11 11:06 上午
 */
@SpringBootTest(classes = LcdApplication.class)
class ExcelWorkerTest {


    @Test
    void readFromFile() {
        String filePath = "/Users/mac/Documents/流水查询.xls";

        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.builder().propName("mechanismId").labelName("行别名称").format(new RegionModelIdFormat(KwMechanism.class, "bankName")).build());
        defineList.add(RegionDefine.textDefine("editionName","版本名称"));
        defineList.add(RegionDefine.textDefine("account","账户"));
        defineList.add(RegionDefine.builder().propName("abnormal").labelName("是否异常").format(new RegionDictReverseFormat("sys_yes_no")).build());

        List<KwWaterArgv> waterRets = ExcelWorker.getInstance().readFromFile(filePath, 0, defineList, KwWaterArgv.class);
        System.out.println(JsonUtil.toJson(waterRets));

    }
}
